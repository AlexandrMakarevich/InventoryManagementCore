package com;

import com.builder.InventoryStateBuilder;
import com.builder.InvoiceBuilder;
import com.builder.InvoiceItemBuilder;
import com.builder.ProductPersistentBuilder;
import com.constant.InvoiceType;
import com.dao.InventoryStateDao;
import com.dao.InvoiceDao;
import com.entity.InventoryState;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.entity.Product;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.process.ProcessInvoice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestProcessInvoice extends BaseTest {

    @Resource(name = "processInvoice")
    private ProcessInvoice processInvoice;

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;

    @Resource(name = "inventoryStateDaoImpl")
    private InventoryStateDao inventoryStateDao;

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    private InvoiceBuilder invoiceBuilder;
    private InventoryStateBuilder inventoryStateBuilder;
    private InvoiceItemBuilder invoiceItemBuilder;

    @Rule
    public ExpectedException testRuleException = ExpectedException.none();

    @Before
    public void init() {
        invoiceBuilder = new InvoiceBuilder();
        inventoryStateBuilder = new InventoryStateBuilder();
        invoiceItemBuilder = new InvoiceItemBuilder();
    }

    @Test
//    @Rollback(false)
//    This test is done to check the case when the table inventory_state is empty
    public void testInventoryStateWithoutProduct() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct = 3;
        int quantityForProduct1 = 4;

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityForProduct,
                product1, quantityForProduct1));

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

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityForProduct));

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, expectedProductQuantity), invoice);
    }

    @Test
    public void testWhenOneOfTheTwoProductIsPresentInInventoryStateTable() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();

        int quantityForProduct1 = 35;
        int quantityForProduct2 = 16;
        int existingProductQuantity = 7;
        int expectedProductQuantity = quantityForProduct1 + existingProductQuantity;

        createAndSaveInventoryState(ImmutableMap.of(product, existingProductQuantity));

        Invoice invoice = createInvoice(ImmutableMap.of(product, quantityForProduct1,
                product1, quantityForProduct2));

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, expectedProductQuantity,
                product1, quantityForProduct2), invoice);
    }

    @Test
//    @Rollback(false)
    public void testInvoiceOUT() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int existingQuantityForProduct = 75;
        int quantityProductForInvoiceOUT = 34;
        int expectedProductQuantity = existingQuantityForProduct - quantityProductForInvoiceOUT;

        createAndSaveInventoryState(ImmutableMap.of(product, existingQuantityForProduct));

        InvoiceItem invoiceItem = invoiceItemBuilder
                .withProduct(product)
                .withProductQuantity(quantityProductForInvoiceOUT)
                .build();

        Set<InvoiceItem> invoiceItems = new HashSet<>();
        invoiceItems.add(invoiceItem);

        Invoice invoice = invoiceBuilder
                .withInvoiceType(InvoiceType.OUT)
                .withSetInvoiceItems(invoiceItems)
                .build();

        processInvoice.process(invoice);

        assertInventoryState(ImmutableMap.of(product, expectedProductQuantity), invoice);
    }

    @Test
    public void testInvoiceOUTFail() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        int existingQuantityForProduct = 25;
        int quantityProductForInvoiceOUT = 34;
        int expectedProductQuantity = existingQuantityForProduct - quantityProductForInvoiceOUT;

        createAndSaveInventoryState(ImmutableMap.of(product, existingQuantityForProduct));

        InvoiceItem invoiceItem = invoiceItemBuilder
                .withProduct(product)
                .withProductQuantity(quantityProductForInvoiceOUT)
                .build();

        Set<InvoiceItem> invoiceItems = new HashSet<>();
        invoiceItems.add(invoiceItem);

        Invoice invoice = invoiceBuilder
                .withInvoiceType(InvoiceType.OUT)
                .withSetInvoiceItems(invoiceItems)
                .build();

        this.testRuleException.expect(IllegalStateException.class);
        this.testRuleException.expectMessage("We don't have so much products by id " + product.getId());

        processInvoice.process(invoice);
    }

    public Invoice createInvoice(Map<Product, Integer> mapProductQuantity) {
        Set<InvoiceItem> invoiceItems = new HashSet<>();
        for (Map.Entry<Product, Integer> entry : mapProductQuantity.entrySet()) {
            InvoiceItem invoiceItem = invoiceItemBuilder
                    .withProduct(entry.getKey())
                    .withProductQuantity(entry.getValue())
                    .build();
            invoiceItemBuilder.reset();
            invoiceItems.add(invoiceItem);
        }
        Invoice invoice = invoiceBuilder
                .withSetInvoiceItems(invoiceItems)
                .build();
        return invoice;
    }

    public void assertInventoryState(Map<Product, Integer> mapProductQuantity, Invoice invoice) {
        List<Integer> productsId = processInvoice.getAllProductId(invoice.getInvoiceItems());
        List<InventoryState> inventoryStates = inventoryStateDao.getInventoryStates(productsId);
        Assert.assertEquals("Size productsId must be equal size inventorySates",
                productsId.size(), inventoryStates.size());
        for (Map.Entry<Product, Integer> entry : mapProductQuantity.entrySet()) {
            Optional<InventoryState> inventoryStateOptional =
                    Iterables.tryFind(inventoryStates, new Predicate<InventoryState>() {
                        @Override
                        public boolean apply(InventoryState input) {
                            return input.getInventoryStatePK().getProduct().equals(entry.getKey());
                        }
                    });
            Assert.assertTrue("InventoryState must be found for product" +
                    entry.getKey(), inventoryStateOptional.isPresent());
            Assert.assertEquals("Actual result must be expected",
                    entry.getValue(), inventoryStateOptional.get().getQuantity());
        }
    }

    public void createAndSaveInventoryState(Map<Product, Integer> mapProductsAndQuantity) {
        for (Map.Entry<Product, Integer> entry : mapProductsAndQuantity.entrySet()) {
            InventoryState inventoryState = inventoryStateBuilder
                    .withProduct(entry.getKey())
                    .withQuantity(entry.getValue())
                    .build();
            inventoryStateDao.saveInventoryState(inventoryState);
        }
    }
}
