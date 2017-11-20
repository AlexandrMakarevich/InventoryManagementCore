package com.dao;

import com.entity.Invoice;

public interface InvoiceDao {

    void saveInvoice(Invoice invoice);

    Invoice getInvoiceById(int id);
}
