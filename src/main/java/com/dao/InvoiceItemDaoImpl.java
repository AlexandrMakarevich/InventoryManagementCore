package com.dao;

import com.entity.InvoiceItem;
import org.springframework.stereotype.Repository;

@Repository("invoiceItemDaoImpl")
public class InvoiceItemDaoImpl extends BaseDao implements InvoiceItemDao {

    @Override
    public int addInvoiceItem(InvoiceItem invoiceItem) {
        getSession().save(invoiceItem);
        return invoiceItem.getId();
    }

    @Override
    public InvoiceItem getById(int id) {
        return getSession().get(InvoiceItem.class, id);
    }
}
