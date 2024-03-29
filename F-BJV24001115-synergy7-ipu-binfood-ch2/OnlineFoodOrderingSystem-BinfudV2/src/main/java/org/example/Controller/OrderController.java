package org.example.Controller;

import org.example.Model.foodItem;
import org.example.View.ConsoleView;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private List<foodItem> foodItems;
    private ConsoleView view;
    private int receiptNumber = 0; // Variabel untuk nomor struk

    public OrderController(ConsoleView view) {
        this.view = view;
        this.foodItems = new ArrayList<>();
        populateFoodItems();
    }

    // Masukkan menu makanan kita yang enak-enak
    private void populateFoodItems() {
        foodItems.add(new foodItem("Nasi Goreng Ayam", 15000));
        foodItems.add(new foodItem("Mie Goreng", 13000));
        foodItems.add(new foodItem("Nasi + Ayam", 18000));
        foodItems.add(new foodItem("Es Teh Manis", 3000));
        foodItems.add(new foodItem("Es Jeruk", 5000));
    }

    // Proses pesanan, asyiknya mulai dari sini!
    public void startOrderProcess() {
        view.displaySectionBreak();
        view.displayWelcomeMessage();


        while (true) {
            view.displaySectionBreak();
            view.displayFoodItems(foodItems);
            int choice = view.getUserChoice();

            if (choice == -1) continue; // Kalau inputnya bukan angka, kita suruh coba lagi

            switch (choice) {
                case 0:
                    System.exit(0); //  keluar aplikasi
                    break;
                case 99:
                    if (!anyItemSelected()) {
                        view.displaySectionBreak();
                        view.displayError("Mohon masukkan input pilihan anda\nMinimal 1 jumlah pesanan!");
                        view.displaySectionBreak();
                        break;
                    }
                    if (finalizeOrder()) {
                        return; // tutup aplikasi setelah cetak struk
                    }
                    break;
                default:
                    if (choice > 0 && choice <= foodItems.size()) {
                        orderFood(choice);
                    } else {
                        view.displaySectionBreak();
                        view.displayError("Pilihan tidak valid. Silakan coba lagi."); // Lho kok pilihannya ngawur?
                    }
                    view.displaySectionBreak();
                    break;
            }
        }
    }

    // Method untuk memeriksa apakah ada item yang dipilih
    private boolean anyItemSelected() {
        return foodItems.stream().anyMatch(item -> item.getQuantity() > 0);
    }

    // Mau pesan berapa? Ini metode buat nambahin jumlah pesanan.
    private void orderFood(int foodIndex) {
        foodItem item = foodItems.get(foodIndex - 1);
        view.displayOrderPrompt(item);
        int quantity = view.getQuantity();

        if (quantity == -1) return; // Oops, bukan angka. Coba lagi ya.

        if (quantity > 0) {
            item.addQuantity(quantity); // Tambahkan jumlah pesanannya
        } else {
            view.displayError("Minimal 1 jumlah pesanan!"); // Jangan lupa pesan minimal satu ya.
        }
    }

    // Saatnya menghitung dan menyajikan struk pembelian yang telah kita lakukan
    private boolean finalizeOrder() {
        StringBuilder receipt = new StringBuilder();
        int total = 0;

        LocalDateTime now = LocalDateTime.now(); // Mendapatkan waktu sekarang
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); // Format tanggal dan waktu
        String formattedDate = now.format(dateFormat); // Format tanggal dan waktu sekarang ke dalam string

        // Menambahkan tanggal dan waktu sekarang ke dalam struk
        receipt.append("===================================================\n");
        receipt.append("BinarFud\n");
        receipt.append(String.format("Tanggal: %s\n", formattedDate));
        receipt.append("===================================================\n");
        receipt.append("Rincian Pesanan:\n");
        receipt.append("---------------------------------------------------\n");

        // Hitung total harga per item
        for (foodItem item : foodItems) {
            if (item.getQuantity() > 0) {
                int cost = item.getTotalPrice();
                receipt.append(String.format("%-25s %-10d Rp%,d\n", item.getName(), item.getQuantity(), cost));
                total += cost;
            }
        }

        receipt.append("---------------------------------------------------\n");
        receipt.append(String.format("%-35s Rp%,d\n", "Total Pembayaran", total));
        receipt.append("===================================================\n");

        view.displayReceipt(receipt.toString());
        saveReceiptToFile(receipt.toString()); // Simpan struknya ke file

        return true; // Semua sudah selesai, kembali ke main untuk tutup aplikasi
    }

    // Metode untuk menyimpan struk ke dalam file txt dengan nama yang unik
    private void saveReceiptToFile(String receipt) {
        String filename = generateReceiptFilename();
        try (PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8)) {
            writer.print(receipt);
            view.displayMessage("Pembayaran berhasil, struk telah disimpan sebagai '" + filename + "'.");
        } catch (Exception e) {
            view.displayError("Error saat menyimpan struk. Kasihan deh lo."); // Eits, ada yang salah nih.
        }
    }

    // Buat nama file struk yang unik dengan tanggal dan nomor struk
    private String generateReceiptFilename() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return String.format("BinarFud_Receipt_%s_%d.txt", now.format(formatter), ++receiptNumber);
    }
}
