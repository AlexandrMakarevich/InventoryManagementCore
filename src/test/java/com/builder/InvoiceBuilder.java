package com.builder;

import com.constant.InvoiceType;
import com.entity.Invoice;
import com.entity.InvoiceIN;
import com.entity.InvoiceItem;
import com.entity.InvoiceOUT;
import java.util.Set;

public class InvoiceBuilder {

    private Invoice invoice;

    public InvoiceBuilder(InvoiceType invoiceType) {
        init(invoiceType);
    }

    public void init(InvoiceType invoiceType) {
      invoice = invoiceType == InvoiceType.IN ? new InvoiceIN() : new InvoiceOUT();
    }

    public InvoiceBuilder withSetInvoiceItems(Set<InvoiceItem> invoiceItemsSet) {
        invoice.getInvoiceItems().addAll(invoiceItemsSet);
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
