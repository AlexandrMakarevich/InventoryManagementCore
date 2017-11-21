package com;

import com.builder.ProductPersistentBuilder;
import com.constant.InvoiceType;
import com.entity.Invoice;
import com.entity.Product;
import com.google.common.collect.ImmutableMap;
import com.process.ProcessInvoice;
import org.junit.Test;
import javax.annotation.Resource;

public class TestProcessInvoiceIN extends TestProcessInvoice {

    @Resource(name = "processInvoice")
    private ProcessInvoice processInvoice;

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    @Test
//    @Rollback(false)
//    This test is done to check the case when the table inventory_state is empty
    public void testInventoryStateWithoutProduct() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct = 3;
        int quantityForProduct1 = 4;

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityForProduct,
                product1, quantityForProduct1), InvoiceType.IN);

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, quantityForProduct, product1, quantityForProduct1),
                invoice);
    }

    @Test
//    @Rollback(false)
    public void testWhenInventoryStateIsPresent() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct = 23;
        int existingQuantity = 15;
        int expectedProductQuantity = quantityForProduct + existingQuantity;
        createAndSaveInventoryState(ImmutableMap.of(product, existingQuantity));

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityForProduct), InvoiceType.IN);

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, expectedProductQuantity), invoice);
    }

    @Test
//    @Rollback(false)
    public void testWhenOneOfTheTwoProductIsPresentInInventoryStateTable() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct1 = 35;
        int quantityForProduct2 = 16;
        int existingProductQuantity = 7;
        int expectedProductQuantity = quantityForProduct1 + existingProductQuantity;

        createAndSaveInventoryState(ImmutableMap.of(product, existingProductQuantity));

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityForProduct1,
                product1, quantityForProduct2), InvoiceType.IN);

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, expectedProductQuantity,
                product1, quantityForProduct2), invoice);
    }
}
