package org.example;

import org.example.Controller.OrderController;
import org.example.View.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        OrderController controller = new OrderController(view);
        controller.startOrderProcess();
    }
}