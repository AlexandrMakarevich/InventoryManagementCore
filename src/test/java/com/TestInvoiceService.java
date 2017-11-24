package com;

import com.builder.InvoiceBuilder;
import com.builder.ProductPersistentBuilder;
import com.constant.InvoiceStatus;
import com.constant.InvoiceType;
import com.dao.InvoiceDao;
import com.entity.Invoice;
import com.entity.Product;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.process_invoice.TestProcessInvoice;
import com.service.InvoiceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.List;

public class TestInvoiceService extends TestProcessInvoice {

    @Resource(name = "invoiceService")
    private InvoiceService invoiceService;

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;

    private InvoiceBuilder invoiceBuilderIN;
    private InvoiceBuilder invoiceBuilderOUT;

    @Before
    public void init() {
        invoiceBuilderIN = new InvoiceBuilder(InvoiceType.IN);
        invoiceBuilderOUT = new InvoiceBuilder(InvoiceType.OUT);
    }

    @Test
//    @Rollback(false)
    public void testProcessPendingInvoices() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct = 45;
        int withdrawQuantityOfProduct = 42;

        Invoice invoiceIN = createInvoice(ImmutableMap.of(product, quantityForProduct), invoiceBuilderIN);
        Invoice invoiceOUT = createInvoice(ImmutableMap.of(product, withdrawQuantityOfProduct), invoiceBuilderOUT);
        saveAllInvoices(ImmutableList.of(invoiceIN, invoiceOUT));

        invoiceService.processPendingInvoices();

        List<Invoice> listPendingInvoice = invoiceDao.getAllPendingInvoice();
        Assert.assertTrue("In database remaining invoices with status PENDING", listPendingInvoice.isEmpty());

        assertInvoiceByStatus(invoiceIN, InvoiceStatus.COMPLETE);
        assertInvoiceByStatus(invoiceOUT, InvoiceStatus.COMPLETE);
    }

    @Test
//    @Rollback(false)
    public void testProcessPendingInvoiceWhenExceptionHappend() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct = 65;
        int withdrawQuantityOfProduct = 78;

        Invoice invoiceIN = createInvoice(ImmutableMap.of(product, quantityForProduct), invoiceBuilderIN);
        Invoice invoiceOUT = createInvoice(ImmutableMap.of(product, withdrawQuantityOfProduct), invoiceBuilderOUT);
        saveAllInvoices(ImmutableList.of(invoiceIN, invoiceOUT));

        invoiceService.processPendingInvoices();

        assertInvoiceByStatus(invoiceIN, InvoiceStatus.COMPLETE);
        assertInvoiceByStatus(invoiceOUT, InvoiceStatus.ERROR);
    }

    public void assertInvoiceByStatus(Invoice invoice, InvoiceStatus invoiceStatus) {
        Invoice actualInvoice = invoiceDao.getInvoiceById(invoice.getId());
        Assert.assertEquals("Actual invoice status must be the same as InvoiceStatus",
                actualInvoice.getStatus(), invoiceStatus);
    }

    public void saveAllInvoices(List<Invoice> invoiceList) {
        invoiceList.forEach(invoice -> invoiceDao.saveInvoice(invoice));
    }
}