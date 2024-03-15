package org.example;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static List<String> foodItems = Arrays.asList("Nasi Goreng Ayam", "Mie Goreng", "Nasi + Ayam", "Es Teh Manis", "Es Jeruk");
    static List<Integer> prices = Arrays.asList(15000, 13000, 18000, 3000, 5000);
    static List<Integer> orderQuantities = new ArrayList<>(Collections.nCopies(foodItems.size(), 0));

    public static void main(String[] args) {
        while (true) {
            System.out.println("Selamat datang di BinarFud");
            System.out.println("Silahkan pilih makanan :");
            for (int i = 0; i < foodItems.size(); i++) {
                System.out.printf("%d. %s | %s\n", i + 1, foodItems.get(i), formatRupiah(prices.get(i)));
            }
            System.out.println("99. Pesan dan Bayar");
            System.out.println("0. Keluar aplikasi");

            System.out.print("=> ");
            int choice = sc.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Terima kasih telah menggunakan BinarFud!");
                    return; // Exit the application
                case 99:
                    confirmAndPay();
                    break;
                default:
                    if (choice > 0 && choice <= foodItems.size()) {
                        orderFood(choice - 1);
                    } else {
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    }
                    break;
            }
        }
    }

    public static void orderFood(int foodIndex) {
        System.out.printf("Berapa pesanan anda untuk %s (harga: %s)\n", foodItems.get(foodIndex), formatRupiah(prices.get(foodIndex)));
        System.out.print("qty => ");
        int quantity = sc.nextInt();

        if (quantity > 0) {
            orderQuantities.set(foodIndex, orderQuantities.get(foodIndex) + quantity);
        }
    }

    public static void confirmAndPay() {
        int total = 0;
        int totalQuantity = 0;
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("========================");

        for (int i = 0; i < foodItems.size(); i++) {
            if (orderQuantities.get(i) > 0) {
                int cost = orderQuantities.get(i) * prices.get(i);
                total += cost;
                totalQuantity += orderQuantities.get(i);
                System.out.printf("%-12s %2d %12s\n", foodItems.get(i), orderQuantities.get(i), formatRupiah(cost));
            }
        }

        System.out.println("--------------------------------+");
        System.out.printf("Total %11d %12s\n", totalQuantity, formatRupiah(total));
        System.out.println("--------------------------------+");
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");

        System.out.print("=> ");
        int confirm = sc.nextInt();

        if (confirm == 1) {
            saveReceipt(total);
        }
    }


    public static void saveReceipt(int total) {
        String receipt = generateReceiptString(total);
        System.out.println(receipt); // Display the receipt to the console

        // Save the receipt to a file
        try (PrintWriter writer = new PrintWriter("receipt.txt", StandardCharsets.UTF_8)) {
            writer.print(receipt);
            System.out.println("Pembayaran berhasil, struk telah disimpan sebagai file .txt");
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write the receipt.");
        }
    }

    private static String generateReceiptString(int total) {
        StringBuilder sb = new StringBuilder();
        sb.append("BinarFud\n");
        sb.append("========================\n");
        sb.append("Terima kasih sudah memesan di BinarFud\n");
        sb.append("Di bawah ini adalah pesanan anda\n");
        sb.append("========================\n");

        int totalQuantity = 0;
        for (int i = 0; i < foodItems.size(); i++) {
            if (orderQuantities.get(i) > 0) {
                int cost = orderQuantities.get(i) * prices.get(i);
                sb.append(String.format("%-12s %2d %12s\n", foodItems.get(i), orderQuantities.get(i), formatRupiah(cost)));
                totalQuantity += orderQuantities.get(i);
            }
        }

        sb.append("--------------------------------+\n");
        sb.append(String.format("Total %11d %12s\n", totalQuantity, formatRupiah(total)));
        sb.append("Pembayaran : BinarCash\n");
        sb.append("========================\n");
        sb.append("Simpan struk ini sebagai bukti pembayaran\n");
        sb.append("========================\n");

        return sb.toString();
    }


    public static String formatRupiah(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        return "Rp" + formatter.format(amount);
    }


}
