package com.process_invoice;

import com.builder.InvoiceBuilder;
import com.builder.ProductPersistentBuilder;
import com.constant.InvoiceType;
import com.entity.Invoice;
import com.entity.Product;
import com.google.common.collect.ImmutableMap;
import com.process.ProcessInvoice;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import javax.annotation.Resource;

public class TestProcessInvoiceOUT extends TestProcessInvoice {

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    @Resource(name = "processInvoice")
    private ProcessInvoice processInvoice;

    private InvoiceBuilder invoiceBuilder;

    @Before
    public void init() {
        invoiceBuilder = new InvoiceBuilder(InvoiceType.OUT);
    }

    @Rule
    public ExpectedException testRuleException = ExpectedException.none();

    @Test
//    @Rollback(false)
    public void testInvoiceOUT() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int existingQuantityForProduct = 75;
        int quantityProductForInvoiceOUT = 34;
        int expectedProductQuantity = existingQuantityForProduct - quantityProductForInvoiceOUT;

        createAndSaveInventoryState(ImmutableMap.of(product, existingQuantityForProduct));

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityProductForInvoiceOUT), invoiceBuilder);

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, expectedProductQuantity), invoice);
    }

    @Test
    public void testInvoiceOUTWhenNotEnoughProduct() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int existingQuantityForProduct = 25;
        int quantityProductForInvoiceOUT = 34;

        createAndSaveInventoryState(ImmutableMap.of(product, existingQuantityForProduct));

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityProductForInvoiceOUT)
                , invoiceBuilder);

        this.testRuleException.expect(IllegalStateException.class);
        String formattedString = String.format("You want %s product with id %s, but we have only %s",
                quantityProductForInvoiceOUT, product.getId(),
                existingQuantityForProduct);
        this.testRuleException.expectMessage(formattedString);

        processInvoice.process(invoice);
    }
}
