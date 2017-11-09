package com.dao;

import com.entity.InvoiceItem;

public interface InvoiceItemDao {

    int addInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getById(int id);
}
