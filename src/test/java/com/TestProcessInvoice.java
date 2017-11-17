package com;

import com.builder.InventoryStateBuilder;
import com.builder.InvoiceBuilder;
import com.builder.InvoiceItemBuilder;
import com.builder.ProductPersistentBuilder;
import com.dao.InventoryStateDao;
import com.dao.InvoiceDao;
import com.entity.InventoryState;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.entity.Product;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
//    This test is done to check the case when the table inventory_state is empty
    public void testInventoryStateWithoutProduct() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();
        Map<Product, Integer> productsQuantity = ImmutableMap.of(product, 3, product1, 4);
        Invoice invoice = createTestInvoice(productsQuantity);

        processInvoice.process(invoice);
        assertInventoryState(productsQuantity, invoice);
    }

    @Test
    public void testWhenInventoryStateIsPresent() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        int alreadyExistProductQuantity = 5;
        Map<Product, Integer> existingProductQuantities = ImmutableMap.of(product, alreadyExistProductQuantity);
        addInventoryState(existingProductQuantities);
        int invoiceProductQuantity = 7;
        Map<Product, Integer> productsQuantity = ImmutableMap.of(product, invoiceProductQuantity);
        Invoice invoice = createTestInvoice(productsQuantity);

        processInvoice.process(invoice);

        Map<Product, Integer> expectedProductQuantities = ImmutableMap.of(
                product, alreadyExistProductQuantity + invoiceProductQuantity);
        assertInventoryState(expectedProductQuantities, invoice);
    }

    @Test
    public void testWhenOneOfTheTwoProductIsPresentInInventoryStateTable() {
        int invoiceProduct1Quantity = 3;
        Product product1 = productPersistentBuilder.buildAndAddProduct();
        int invoiceProduct2Quantity = 8;
        Product product2 = productPersistentBuilder.buildAndAddProduct();
        Map<Product, Integer> productsQuantity = ImmutableMap.of(
                product1, invoiceProduct1Quantity,
                product2, invoiceProduct2Quantity);
        Invoice invoice = createTestInvoice(productsQuantity);

        int existingProduct1Quantity = 11;
        Map<Product, Integer> inventoryStateProductQuantity = ImmutableMap.of(
                product1, existingProduct1Quantity
        );
        addInventoryState(inventoryStateProductQuantity);

        processInvoice.process(invoice);
        assertInventoryState(ImmutableMap.of(
                product1, invoiceProduct1Quantity + existingProduct1Quantity,
                product2, invoiceProduct2Quantity),
                invoice);
    }

    private void addInventoryState(Map<Product, Integer> inventoryStateProductQuantities) {
        for (Map.Entry<Product, Integer> entry : inventoryStateProductQuantities.entrySet()) {
            InventoryState inventoryState = inventoryStateBuilder
                    .withProduct(entry.getKey())
                    .withQuantity(entry.getValue())
                    .build();
            inventoryStateDao.add(inventoryState);
        }
    }

    @Test
    public void testWhenInvoiceHasTwoTheSameProduct() {
        this.testRuleException.expect(IllegalArgumentException.class);
//        this.testRuleException.expectMessage("You have duplicate product in invoice.Check your data.");

        Product product1 = productPersistentBuilder.buildAndAddProduct();
        Invoice invoice = createTestInvoice(ImmutableMap.of(
                product1, 12,
                product1, 22));

        processInvoice.process(invoice);

    }

    @Test
    public void testWhenInvoiceHasTwoTheSameProductAndInventoryStateAlreadyHasThisProduct() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        InventoryState inventoryState = inventoryStateBuilder
                .withProduct(product)
                .withQuantity(22)
                .build();
        inventoryStateDao.add(inventoryState);

        InvoiceItem invoiceItem = invoiceItemBuilder
                .withProduct(product)
                .withProductQuantity(34)
                .build();

        invoiceItemBuilder.reset();

        InvoiceItem invoiceItem1 = invoiceItemBuilder
                .withProduct(product)
                .withProductQuantity(65)
                .build();

        Invoice invoice = invoiceBuilder
                .withListInvoiceItems(ImmutableList.of(invoiceItem, invoiceItem1))
                .build();

        this.testRuleException.expect(IllegalStateException.class);
        this.testRuleException.expectMessage("You have duplicate product in invoice.Check your data.");

        processInvoice.process(invoice);
    }

    @Test
    public void testQuery() {
        List<Integer> listOfProductId = new ArrayList<>();
        listOfProductId.add(59);
        listOfProductId.add(61);
        List<InventoryState> inventoryStates = inventoryStateDao.getInventoryStates(listOfProductId);
        System.out.println(inventoryStates);
    }

    @Test
    public void testIterable() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();
        InvoiceItem invoiceItem = invoiceItemBuilder.withProduct(product).build();
        InvoiceItem invoiceItem1 = invoiceItemBuilder.withProduct(product1).build();
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        invoiceItems.add(invoiceItem);
        invoiceItems.add(invoiceItem1);
        List<Integer> listOfProductId = processInvoice.getAllProductId(invoiceItems);
        System.out.println(listOfProductId);
    }

    private void assertInventoryState(Map<Product, Integer> productsQuantity, Invoice invoice) {
        List<Integer> productIdList = processInvoice.getAllProductId(invoice.getInvoiceItems());
        List<InventoryState> actualInventoryStates = inventoryStateDao.getInventoryStates(productIdList);
        assertEquals("actualInventoryStates must be the same size as productsQuantity", productsQuantity.size(), actualInventoryStates.size());
        for (Map.Entry<Product, Integer> productQuantity: productsQuantity.entrySet()) {
            Optional<InventoryState> actualInventoryState = Iterables.tryFind(actualInventoryStates, new Predicate<InventoryState>() {
                @Override
                public boolean apply(InventoryState inventoryState) {
                    return inventoryState.getInventoryStatePK().getProduct().equals(productQuantity.getKey());
                }
            });
            assertTrue("actualInventoryState must be found for product " + productQuantity.getKey(), actualInventoryState.isPresent());
            assertEquals("Product quantity must be as expected", productQuantity.getValue(), actualInventoryState.get().getQuantity());

        }
    }

    private Invoice createTestInvoice(Map<Product, Integer> products) {
        List<InvoiceItem> invoiceItems = new ArrayList<>(products.size());
        for(Map.Entry<Product, Integer> entry : products.entrySet()) {
            InvoiceItem invoiceItem = invoiceItemBuilder
                    .withProduct(entry.getKey())
                    .withProductQuantity(entry.getValue())
                    .build();
            invoiceItemBuilder.reset();
            invoiceItems.add(invoiceItem);
        }
        Invoice invoice = invoiceBuilder.withListInvoiceItems(invoiceItems).build();
        invoiceDao.add(invoice);
        return invoice;
    }

}
