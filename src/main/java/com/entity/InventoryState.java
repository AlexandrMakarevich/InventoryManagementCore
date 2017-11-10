package com.entity;

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

    @Column(name = "last_up_date")
    private LocalDateTime lastUpDate = LocalDateTime.now();

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

    public LocalDateTime getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(LocalDateTime lastUpDate) {
        this.lastUpDate = lastUpDate;
    }
}
