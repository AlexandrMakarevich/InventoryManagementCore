package com.dao;

import com.BaseTest;
import com.builder.InvoiceINBuilder;
import com.builder.InvoiceItemPersistentBuilder;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

public class TestInvoiceDaoImpl extends BaseTest {

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;

    @Resource(name = "invoiceItemPersistentBuilder")
    private InvoiceItemPersistentBuilder invoiceItemPersistentBuilder;

    private InvoiceINBuilder invoiceINBuilder;

    @Before
    public void init() {
        invoiceINBuilder = new InvoiceINBuilder();
    }

    @Test
//    @Rollback(false)
    public void testAddInvoice() {
        InvoiceItem invoiceItem = invoiceItemPersistentBuilder.buildAndAddInvoiceItem();
        Set<InvoiceItem> invoiceItems = new HashSet<>();
        invoiceItems.add(invoiceItem);
        Invoice invoice = invoiceINBuilder.withSetInvoiceItems(invoiceItems).build();
        invoiceDao.saveInvoice(invoice);
        Invoice actualInvoice = invoiceDao.getInvoiceById(invoice.getId());
        System.out.println(invoice);
        System.out.println(actualInvoice);
        Assert.assertEquals("Invoice must be equal actualInvoice", actualInvoice, invoice);
    }
}
