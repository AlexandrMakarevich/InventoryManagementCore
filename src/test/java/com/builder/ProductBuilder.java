package com.builder;

import com.entity.Product;

public class ProductBuilder {

    private Product product;

    public ProductBuilder() {
        init();
    }

    public void init() {
        product = new Product();
        product.setProductName("product1");
        product.setDescription("description1");
    }

    public ProductBuilder withName(String productName) {
        product.setProductName(productName);
        return this;
    }

    public ProductBuilder withId(int id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withDescription(String description) {
        product.setDescription(description);
        return this;
    }

    public Product build() {
        return product;
    }
}
