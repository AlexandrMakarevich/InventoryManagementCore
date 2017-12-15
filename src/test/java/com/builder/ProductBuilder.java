package com.builder;

import com.entity.Product;

import java.math.BigDecimal;
import java.time.Instant;

public class ProductBuilder {

    private Product product;

    public ProductBuilder() {
        init();
    }

    private void init() {
        product = new Product();
        product.setProductName("product1");
        product.setDescription("description1");
        product.setCreationDate(Instant.now());
        product.setPrice(new BigDecimal(10.5));
    }

    public ProductBuilder withName(String productName) {
        product.setProductName(productName);
        return this;
    }

    public ProductBuilder withId(int id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withCreationDate(Instant creationDate) {
        product.setCreationDate(creationDate);
        return this;
    }

    public ProductBuilder withDescription(String description) {
        product.setDescription(description);
        return this;
    }

    public ProductBuilder withProductPrice(BigDecimal price) {
        product.setPrice(price);
        return this;
    }

    public Product build() {
        return product;
    }
}
