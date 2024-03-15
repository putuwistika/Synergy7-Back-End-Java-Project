package org.example;

import java.util.*;
import java.io.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static List<String> foodItems = Arrays.asList("Nasi Goreng Spesial", "Mie Goreng", "Nasi + Ayam", "Es Teh Manis", "Es Jeruk");
    static List<Integer> prices = Arrays.asList(15000, 13000, 18000, 3000, 5000);
    static List<Integer> orderQuantities = new ArrayList<>(Collections.nCopies(foodItems.size(), 0));

    public static void main(String[] args) {
        while (true) {
            System.out.println("Selamat datang di BinarFud");
            System.out.println("Silahkan pilih makanan :");
            for (int i = 0; i < foodItems.size(); i++) {
                System.out.printf("%d. %s | %d\n", i + 1, foodItems.get(i), prices.get(i));
            }
            System.out.println("99. Pesan dan Bayar");
            System.out.println("0. Keluar aplikasi");

            System.out.print("=> ");
            int choice = sc.nextInt();

            if (choice == 0) {
                break;
            } else if (choice == 99) {
                confirmAndPay();
            } else {
                orderFood(choice - 1);
            }
        }
    }

    public static void orderFood(int foodIndex) {
        System.out.printf("Berapa pesanan anda untuk %s (harga: %d)\n", foodItems.get(foodIndex), prices.get(foodIndex));
        System.out.print("qty => ");
        int quantity = sc.nextInt();

        if (quantity != 0) {
            orderQuantities.set(foodIndex, orderQuantities.get(foodIndex) + quantity);
        }
    }

    public static void confirmAndPay() {
        int total = 0;
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("========================");

        for (int i = 0; i < foodItems.size(); i++) {
            if (orderQuantities.get(i) > 0) {
                int cost = orderQuantities.get(i) * prices.get(i);
                System.out.printf("%s %d %d\n", foodItems.get(i), orderQuantities.get(i), cost);
                total += cost;
            }
        }

        System.out.println("========================");
        System.out.println("Total: " + total);
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");

        System.out.print("=> ");
        int confirm = sc.nextInt();

        if (confirm == 1) {
            printReceipt(total);
        }
    }

    public static void printReceipt(int total) {
        try {
            PrintWriter writer = new PrintWriter("receipt.txt", "UTF-8");
            writer.println("BinarFud");
            writer.println("========================");
            writer.println("Terima kasih sudah memesan di BinarFud");
            writer.println("Di bawah ini adalah pesanan anda");
            writer.println("========================");

            for (int i = 0; i < foodItems.size(); i++) {
                if (orderQuantities.get(i) > 0) {
                    int cost = orderQuantities.get(i) * prices.get(i);
                    writer.printf("%s %d %d\n", foodItems.get(i), orderQuantities.get(i), cost);
                }
            }

            writer.println("========================");
            writer.println("Total: " + total);
            writer.println("Pembayaran: BinarCash");
            writer.println("========================");
            writer.println("Simpan struk ini sebagai bukti pembayaran");
            writer.println("========================");

            writer.close();
            System.out.println("Pembayaran berhasil, struk telah disimpan sebagai file .txt");
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write the receipt.");
            e.printStackTrace();
        }
    }
}
