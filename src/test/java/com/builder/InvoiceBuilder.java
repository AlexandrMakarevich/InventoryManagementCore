package com.builder;

import com.constant.InvoiceType;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import java.util.ArrayList;
import java.util.List;

public class InvoiceBuilder {

    private Invoice invoice;
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    public InvoiceBuilder() {
        init();
    }

    public void init() {
        invoice = new Invoice();
        invoice.setType(InvoiceType.IN);
        invoice.setInvoiceItems(invoiceItems);
    }

    public InvoiceBuilder withListInvoiceItems(List<InvoiceItem> invoiceItems) {
        invoice.getInvoiceItems().addAll(invoiceItems);
        return this;
    }

    public InvoiceBuilder withInvoiceType(InvoiceType invoiceType) {
        invoice.setType(invoiceType);
        return this;
    }

    public Invoice build() {
        return invoice;
    }
}
