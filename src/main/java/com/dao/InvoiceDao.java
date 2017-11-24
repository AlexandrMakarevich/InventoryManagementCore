package com.dao;

import com.entity.Invoice;

import java.util.List;

public interface InvoiceDao {

    void saveInvoice(Invoice invoice);

    Invoice getInvoiceById(int id);

    List<Invoice> getAllPendingInvoice();
}
