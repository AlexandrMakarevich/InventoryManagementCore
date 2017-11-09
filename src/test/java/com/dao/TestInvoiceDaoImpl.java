package com.dao;

import com.BaseTest;
import com.builder.InvoiceBuilder;
import com.builder.InvoiceItemPersistentBuilder;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class TestInvoiceDaoImpl extends BaseTest {

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;

    @Resource(name = "invoiceItemPersistentBuilder")
    private InvoiceItemPersistentBuilder invoiceItemPersistentBuilder;

    private InvoiceBuilder invoiceBuilder;

    @Before
    public void init() {
        invoiceBuilder = new InvoiceBuilder();
    }

    @Test
    @Rollback(false)
    public void testAddInvoice() {
        InvoiceItem invoiceItem = invoiceItemPersistentBuilder.buildAndAddInvoiceItem();
        Invoice invoice = invoiceBuilder.withListInvoiceItems(ImmutableList.of(invoiceItem)).build();
        invoiceDao.add(invoice);
        Invoice actualInvoice = invoiceDao.getInvoiceById(invoice.getId());
        System.out.println(invoice);
        System.out.println(actualInvoice);
        Assert.assertEquals("Invoice must be equal actualInvoice", actualInvoice, invoice);
    }
}
