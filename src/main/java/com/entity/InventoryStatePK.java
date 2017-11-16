package com.entity;

import com.google.common.base.Objects;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class InventoryStatePK implements Serializable {

    @OneToOne
    private Product product;

    @Column(name = "state_date")
    private LocalDateTime stateDate = LocalDateTime.now();

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getStateDate() {
        return stateDate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStateDate(LocalDateTime stateDate) {
        this.stateDate = stateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryStatePK that = (InventoryStatePK) o;
        return Objects.equal(product, that.product) &&
                Objects.equal(stateDate, that.stateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product, stateDate);
    }

    @Override
    public String toString() {
        return "InventoryStatePK{" +
                "product=" + product +
                ", stateDate=" + stateDate +
                '}';
    }
}
