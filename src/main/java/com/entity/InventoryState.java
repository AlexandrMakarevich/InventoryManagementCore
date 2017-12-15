package com.entity;

import com.google.common.base.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory_state")
public class InventoryState {

    @EmbeddedId
    private InventoryStatePK inventoryStatePK;

    @Column(name = "quantity")
    private Integer quantity;

    public BigDecimal calculateItemCost() {
        BigDecimal bigDecimalQuantity = BigDecimal.valueOf(quantity);
        return inventoryStatePK.getProduct().getPrice().multiply(bigDecimalQuantity);
    }

    public InventoryStatePK getInventoryStatePK() {
        return inventoryStatePK;
    }

    public void setInventoryStatePK(InventoryStatePK inventoryStatePK) {
        this.inventoryStatePK = inventoryStatePK;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryState that = (InventoryState) o;
        return Objects.equal(inventoryStatePK, that.inventoryStatePK);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inventoryStatePK);
    }
}
