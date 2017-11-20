package com.dao;

import com.BaseTest;
import com.builder.InvoiceItemBuilder;
import com.builder.ProductPersistentBuilder;
import com.entity.InvoiceItem;
import com.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import javax.annotation.Resource;

public class TestInvoiceItemDaoImpl extends BaseTest {

    @Resource(name = "invoiceItemDaoImpl")
    private InvoiceItemDao invoiceItemDao;

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    private InvoiceItemBuilder invoiceItemBuilder;

    @Before
    public void init() {
        invoiceItemBuilder = new InvoiceItemBuilder();
    }

    @Test
    @Rollback(false)
    public void testAddInvoiceItem() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        InvoiceItem invoiceItem = invoiceItemBuilder.withProduct(product).build();
        int actualId = invoiceItemDao.saveInvoiceItem(invoiceItem);
        InvoiceItem actualInvoiceItem = invoiceItemDao.getInvoiceItemById(actualId);
        System.out.println(invoiceItem);
        System.out.println(actualInvoiceItem);
        Assert.assertEquals("ActualInvoiceItem must be equal invoiceItem", actualInvoiceItem, invoiceItem);
    }
}
