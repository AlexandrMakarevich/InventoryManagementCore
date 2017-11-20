package com.dao;

import com.entity.InvoiceItem;

public interface InvoiceItemDao {

    int saveInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getInvoiceItemById(int id);
}
