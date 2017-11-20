package com.builder;

import com.entity.InventoryState;
import com.entity.InventoryStatePK;
import com.entity.Product;

import java.time.LocalDateTime;

public class InventoryStateBuilder {

    private InventoryState inventoryState;
    private InventoryStatePK inventoryStatePK;

    public InventoryStateBuilder() {
        init();
    }

    public void init() {
        inventoryStatePK = new InventoryStatePK();
        inventoryStatePK.setProduct(new Product());
        inventoryState = new InventoryState();
        inventoryState.setInventoryStatePK(inventoryStatePK);
        inventoryState.setQuantity(1);
        inventoryStatePK.setStateDate(LocalDateTime.now().minusDays(1));
    }


    public InventoryStateBuilder withProduct(Product product) {
        inventoryState.getInventoryStatePK().setProduct(product);
        return this;
    }

    public InventoryStateBuilder withQuantity(int quantity) {
        inventoryState.setQuantity(quantity);
        return this;
    }

    public InventoryStateBuilder withDate(LocalDateTime localDateTime) {
        inventoryState.getInventoryStatePK().setStateDate(localDateTime);
        return this;
    }

    public InventoryStateBuilder withInventoryStatePK(InventoryStatePK inventoryStatePK) {
        inventoryState.setInventoryStatePK(inventoryStatePK);
        return this;
    }

    public InventoryState build() {
        return inventoryState;
    }

    public void reset() {
        init();
    }
}
