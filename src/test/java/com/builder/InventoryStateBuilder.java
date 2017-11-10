package com.builder;

import com.entity.InventoryState;
import com.entity.Product;

public class InventoryStateBuilder {

    private InventoryState inventoryState;

    public InventoryStateBuilder() {
        init();
    }

    public void init() {
        inventoryState = new InventoryState();
        inventoryState.setProduct(new Product());
        inventoryState.setQuantity(1);
    }


    public InventoryStateBuilder withProduct(Product product) {
        inventoryState.setProduct(product);
        return this;
    }

    public InventoryStateBuilder withQuantity(int quantity) {
        inventoryState.setQuantity(quantity);
        return this;
    }

    public InventoryState build() {
        return inventoryState;
    }
}
