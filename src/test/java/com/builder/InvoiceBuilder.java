package com.builder;

import com.constant.InvoiceType;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import java.util.HashSet;
import java.util.Set;

public class InvoiceBuilder {

    private Invoice invoice;
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    public InvoiceBuilder() {
        init();
    }

    public void init() {
        invoice = new Invoice();
        invoice.setType(InvoiceType.IN);
        invoice.setInvoiceItems(invoiceItems);
    }

    public InvoiceBuilder withSetInvoiceItems(Set<InvoiceItem> invoiceItems) {
        invoice.getInvoiceItems().addAll(invoiceItems);
        return this;
    }

    public InvoiceBuilder withInvoiceType(InvoiceType invoiceType) {
        invoice.setType(invoiceType);
        return this;
    }

    public InvoiceBuilder withId(int id) {
        invoice.setId(id);
        return this;
    }

    public Invoice build() {
        return invoice;
    }
}
