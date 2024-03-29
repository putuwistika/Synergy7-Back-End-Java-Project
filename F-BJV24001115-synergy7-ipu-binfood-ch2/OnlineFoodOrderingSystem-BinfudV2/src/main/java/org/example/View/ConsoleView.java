package org.example.View;

import org.example.Model.foodItem;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);

    public void displayWelcomeMessage() {
        System.out.println("\n===================================================");
        System.out.println("Selamat datang di BinarFud");
        System.out.println("=====================================================");
    }

    public void displayFoodItems(List<foodItem> foodItems) {
        System.out.println("Silahkan pilih makanan:");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-3s %-25s %s\n", "#", "Makanan", "Harga");
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < foodItems.size(); i++) {
            foodItem item = foodItems.get(i);
            System.out.printf("%-3d %-25s Rp%,d\n", i + 1, item.getName(), item.getPrice());
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
    }

    public int getUserChoice() {
        System.out.print("=> ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            displayError("Masukkan pilihan berupa angka.");
            return -1; // Indicator of invalid choice
        }
    }

    public void displayOrderPrompt(foodItem item) {
        System.out.printf("Berapa pesanan anda untuk %s (harga: Rp%,d)?\n", item.getName(), item.getPrice());
        System.out.print("qty => ");
    }

    public int getQuantity() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            displayError("Masukkan jumlah berupa angka.");
            return -1; // Indicator of invalid quantity

        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayReceipt(String receipt) {
        System.out.println(receipt);
    }

    public void displayError(String errorMessage) {
        System.out.println("===================================================");
        System.out.println(errorMessage);
        System.out.println("===================================================");
    }

    // Membuat baris kosong sebagai pemisah sesi
    public void displaySectionBreak() {
        System.out.println("\n\n");
    }
}
