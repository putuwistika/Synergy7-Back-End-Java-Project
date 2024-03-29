package org.example.Controller;

import org.example.Model.foodItem;
import org.example.View.ConsoleView;


import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private final List<foodItem> foodItems;
    private final ConsoleView view;


    public OrderController(ConsoleView view) {
        this.view = view;
        this.foodItems = new ArrayList<>();
        populateFoodItems();
    }

    private void populateFoodItems() {
        foodItems.add(new foodItem("Nasi Goreng Ayam", 15000));
        foodItems.add(new foodItem("Mie Goreng", 13000));
        foodItems.add(new foodItem("Nasi + Ayam", 18000));
        foodItems.add(new foodItem("Es Teh Manis", 3000));
        foodItems.add(new foodItem("Es Jeruk", 5000));
    }

    public void startOrderProcess() {
        view.displayWelcomeMessage();

        while (true) {
            view.displaySectionBreak();
            view.displayFoodItems(foodItems);
            int choice = view.getUserChoice();

            if (choice == -1) continue;

            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 99:
                    view.displaySectionBreak();
                    if (!anyItemSelected()) {
                        view.displayError("Mohon masukkan input pilihan anda\nMinimal 1 jumlah pesanan!");
                        break;
                    }
                    finalizeOrder();
                    break;
                default:
                    if (choice > 0 && choice <= foodItems.size()) {
                        orderFood(choice);
                    } else {
                        view.displayError("Pilihan tidak valid. Silakan coba lagi.");
                    }
                    break;
            }
        }
    }

    private boolean anyItemSelected() {
        return foodItems.stream().anyMatch(item -> item.getQuantity() > 0);
    }

    private void orderFood(int foodIndex) {
        foodItem item = foodItems.get(foodIndex - 1);
        view.displayOrderPrompt(item);
        int quantity = view.getQuantity();

        if (quantity == -1) return;

        if (quantity > 0) {
            item.addQuantity(quantity);
        } else {
            view.displayError("Minimal 1 jumlah pesanan!");
        }
    }

    private void finalizeOrder() {
        int total = calculateTotal();
        view.displayPaymentConfirmation(foodItems, total);
        handlePaymentAction();
    }

    private int calculateTotal() {
        return foodItems.stream().mapToInt(foodItem::getTotalPrice).sum();
    }

    private void handlePaymentAction() {
        handlePayment paymentHandler = new handlePayment(view, foodItems);
        paymentHandler.handlePaymentAction();
    }

}
