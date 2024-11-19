package org.example;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Availability {
    @NotNull(message = "In-stock status cannot be null")
    private  boolean inStock;

    @Min(value = 0, message = "Quantity cannot be negative")
    private  int quantity;

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
