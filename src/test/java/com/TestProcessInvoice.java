package com;

import com.builder.InvoiceBuilder;
import com.builder.InvoiceItemBuilder;
import com.builder.ProductBuilder;
import com.dao.ProductDao;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.entity.Product;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import javax.annotation.Resource;

public class TestProcessInvoice extends BaseTest {

    @Resource(name = "processInvoice")
    private ProcessInvoice processInvoice;

    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

    private InvoiceBuilder invoiceBuilder;
    private InvoiceItemBuilder invoiceItemBuilder;
    private ProductBuilder productBuilder;

    @Before
    public void init() {
        invoiceBuilder = new InvoiceBuilder();
        invoiceItemBuilder = new InvoiceItemBuilder();
        productBuilder = new ProductBuilder();
    }

    @Test
    @Rollback(false)
    public void testWhenDbWithOutProduct() {
        Product product = productBuilder.withId(2).build();
        InvoiceItem invoiceItem = invoiceItemBuilder.withProduct(product).build();
        Invoice invoice = invoiceBuilder.withListInvoiceItems(ImmutableList.of(invoiceItem)).build();
//        processInvoice.process(invoice);
        Product actualProduct = productDao.getById(product.getId());
        Assert.assertEquals("ActualProduct must be equal product", actualProduct, product);
    }
}
