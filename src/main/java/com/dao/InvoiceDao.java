package com.dao;

import com.entity.Invoice;

public interface InvoiceDao {

    void add(Invoice invoice);

    Invoice getInvoiceById(int id);
}
