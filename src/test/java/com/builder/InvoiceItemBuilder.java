package com.builder;

import com.entity.InvoiceItem;
import com.entity.Product;

public class InvoiceItemBuilder {

    private InvoiceItem invoiceItem;

    public InvoiceItemBuilder() {
        init();
        invoiceItem.setProduct(new Product());
        invoiceItem.setProductQuantity(1);
    }

    public void init() {
        invoiceItem = new InvoiceItem();
    }

    public InvoiceItemBuilder withId(int id) {
        invoiceItem.setId(id);
        return this;
    }

    public InvoiceItemBuilder withProduct(Product product) {
        invoiceItem.setProduct(product);
        return this;
    }

    public InvoiceItemBuilder withProductQuantity(int productQuantity) {
        invoiceItem.setProductQuantity(productQuantity);
        return this;
    }

   public InvoiceItem build() {
        return invoiceItem;
   }

    public void reset() {
        init();
    }
}
