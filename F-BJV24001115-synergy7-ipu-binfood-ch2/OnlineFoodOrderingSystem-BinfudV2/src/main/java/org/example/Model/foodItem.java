package org.example.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class foodItem {
    private String name;
    private int price;
    private int quantity;

    public foodItem(String name, int price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public int getTotalPrice() {
        return this.quantity * this.price;
    }

    // Tambahkan jumlah pesanan makanan
    public void addQuantity(int quantityToAdd) {
        this.quantity += quantityToAdd;
    }
}
