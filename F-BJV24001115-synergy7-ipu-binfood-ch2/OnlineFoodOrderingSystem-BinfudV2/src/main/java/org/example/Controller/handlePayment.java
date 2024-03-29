package org.example.Controller;

import org.example.Model.foodItem;
import org.example.View.ConsoleView;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class handlePayment {

    private final List<foodItem> foodItems;
    private final ConsoleView view;
    private static int receiptNumber = 0;

    public handlePayment(ConsoleView view, List<foodItem> foodItems) {
        this.view = view;
        this.foodItems = foodItems;
    }

    public void handlePaymentAction() {
        int action = view.getPaymentConfirmationAction();
        switch (action) {
            case 1:
                // Logika tambah pesanan
                break;
            case 2:
                cancelSpecificOrder();
                break;
            case 3:
                cancelOrder();
                break;
            case 4:
                completeOrder();
                break;
            default:
                view.displayError("Aksi tidak valid");
                // finalizeOrder(); akan dipanggil dari OrderController jika diperlukan
                break;
        }
    }

    private void cancelSpecificOrder() {
        view.displayCancelableItems(foodItems);
        int itemToCancel = view.getCancelItemChoice();
        if (itemToCancel > 0 && itemToCancel <= foodItems.size()) {
            foodItem item = foodItems.get(itemToCancel - 1);
            if (item.getQuantity() > 0) {
                item.setQuantity(0);
                view.displayMessage("Pesanan " + item.getName() + " telah dibatalkan.");
            } else {
                view.displayError("Tidak ada pesanan aktif untuk " + item.getName() + ".");
            }
        } else {
            view.displayError("Pilihan tidak valid.");
        }
        // finalizeOrder(); akan dipanggil dari OrderController jika diperlukan
    }

    private void cancelOrder() {
        foodItems.forEach(item -> item.setQuantity(0));
        view.displayMessage("Semua pesanan telah dibatalkan.");
        // startOrderProcess(); akan dipanggil dari OrderController jika diperlukan
    }

    private void completeOrder() {
        int total = calculateTotal();
        if (total > 0) {
            printReceipt(total);
            System.exit(0);
        } else {
            view.displayError("Tidak ada pesanan untuk dibayar.");
            // startOrderProcess(); akan dipanggil dari OrderController jika diperlukan
        }
    }

    private int calculateTotal() {
        return foodItems.stream().mapToInt(foodItem::getTotalPrice).sum();
    }

    private void printReceipt(int total) {
        StringBuilder receipt = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(dateFormat);

        receipt.append("===================================================\n");
        receipt.append("BinarFud\n");
        receipt.append(String.format("Tanggal: %s\n", formattedDate));
        receipt.append("===================================================\n");
        receipt.append("Rincian Pesanan:\n");
        receipt.append("---------------------------------------------------\n");

        for (foodItem item : foodItems) {
            if (item.getQuantity() > 0) {
                int cost = item.getTotalPrice();
                receipt.append(String.format("%-25s %-10d Rp%,d\n", item.getName(), item.getQuantity(), cost));
            }
        }

        receipt.append("---------------------------------------------------\n");
        receipt.append(String.format("%-35s Rp%,d\n", "Total Pembayaran", total));
        receipt.append("===================================================\n");

        view.displayReceipt(receipt.toString());
        saveReceiptToFile(receipt.toString());
    }

    private void saveReceiptToFile(String receipt) {
        String filename = generateReceiptFilename();
        try (PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8)) {
            writer.print(receipt);
            view.displayMessage("Pembayaran berhasil, struk telah disimpan sebagai '" + filename + "'.");
        } catch (Exception e) {
            view.displayError("Error saat menyimpan struk.");
        }
    }

    private String generateReceiptFilename() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        return String.format("BinarFud_Receipt_%s_%d.txt", now.format(formatter), ++receiptNumber);
    }

}
