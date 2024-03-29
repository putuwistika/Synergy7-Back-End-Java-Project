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
    }

    public int getTotalPrice() {
        return this.quantity * this.price;
    }

    public void addQuantity(int quantityToAdd) {
        this.quantity += quantityToAdd;
    }


}
