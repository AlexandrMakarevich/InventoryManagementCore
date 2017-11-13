package com.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_state")
public class InventoryState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "state_date")
    private LocalDateTime stateDate = LocalDateTime.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDateTime lastUpDate) {
        this.stateDate = lastUpDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryState that = (InventoryState) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, product);
    }

    @Override
    public String toString() {
        return "InventoryState{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", lastUpDate=" + stateDate +
                '}';
    }
}
