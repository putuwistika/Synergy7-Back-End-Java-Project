package org.example.Controller;

import org.example.Model.foodItem;
import org.example.View.ConsoleView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class handlePaymentTest {

    private handlePayment paymentHandler;
    private List<foodItem> foodItems;
    private TestConsoleView view;

    private class TestConsoleView extends ConsoleView {
        int action;
        int cancelItemChoice;
        String displayedMessage;

        @Override
        public int getPaymentConfirmationAction() {
            return action;
        }

        @Override
        public int getCancelItemChoice() {
            return cancelItemChoice;
        }

        @Override
        public void displayMessage(String message) {
            displayedMessage = message;
        }

        @Override
        public void displayError(String error) {
            displayedMessage = error;
        }


    }

    @BeforeEach
    void setUp() {
        view = new TestConsoleView();
        foodItems = new ArrayList<>();
        paymentHandler = new handlePayment(view, foodItems);
    }

    @Test
    void testHandlePaymentAction() {
        view.action = 1;
        paymentHandler.handlePaymentAction();

    }

    @Test
    void testCancelSpecificOrder() {
        foodItems.add(new foodItem("Pizza", 100));
        view.action = 2;
        view.cancelItemChoice = 1;
        paymentHandler.handlePaymentAction();
        assertEquals(0, foodItems.get(0).getQuantity());
        assertFalse(view.displayedMessage.contains("canceled"));
    }

    @Test
    void testCompleteOrder() {
        view.action = 4;
        paymentHandler.handlePaymentAction();
        assertNotNull(view.displayedMessage);
    }
}
