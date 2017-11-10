package com.dao;

import com.entity.Invoice;
import org.springframework.stereotype.Repository;

@Repository("invoiceDaoImpl")
public class InvoiceDaoImpl extends BaseDao implements InvoiceDao{


    @Override
    public void add(Invoice invoice) {
        getSession().save(invoice);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return getSession().get(Invoice.class, id);
    }
}
