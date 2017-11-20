package com.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Product product;

    @Column(name = "product_quantity")
    private Integer productQuantity;

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

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return Objects.equal(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", product=" + product +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
