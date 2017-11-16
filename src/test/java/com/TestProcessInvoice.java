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
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        InvoiceItem invoiceItem = invoiceItemBuilder
                .withProduct(product)
                .withProductQuantity(3)
                .build();

        invoiceItemBuilder.reset();

        InvoiceItem invoiceItem1 = invoiceItemBuilder
                .withProduct(product1)
                .withProductQuantity(4)
                .build();

        List<InvoiceItem> invoiceItems = ImmutableList.of(invoiceItem, invoiceItem1);
        Invoice invoice = invoiceBuilder.withListInvoiceItems(invoiceItems).build();
        invoiceDao.add(invoice);

        processInvoice.process(invoice);
        List<Integer> productIdList = processInvoice.getAllProductId(invoiceItems);
        List<InventoryState> actualInventoryStates = inventoryStateDao.getInventoryStates(productIdList);

        InventoryState inventoryState = inventoryStateDao.getInventoryStateByProductIdWhereMaxStateDate(invoiceItem);
        InventoryState inventoryState1 = inventoryStateDao.getInventoryStateByProductIdWhereMaxStateDate(invoiceItem1);

        Assert.assertEquals("Actual result must be expected", inventoryState.getQuantity(), invoiceItem.getProductQuantity());
        Assert.assertEquals("Actual result must be expected", inventoryState1.getQuantity(), invoiceItem1.getProductQuantity());
    }

    @Test
//    @Rollback(false)
    public void testWhenInventoryStateIsPresent() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        InventoryState inventoryState = inventoryStateBuilder
                .withDate(LocalDateTime.parse("2017-11-14T20:00:10.976"))
                .withProduct(product)
                .build();
        inventoryStateDao.add(inventoryState);

        InvoiceItem invoiceItem = invoiceItemBuilder.withProduct(product).build();
        Invoice invoice = invoiceBuilder.withListInvoiceItems(ImmutableList.of(invoiceItem)).build();

        processInvoice.process(invoice);

        List<Integer> productIdList = processInvoice.getAllProductId(invoice.getInvoiceItems());
        List<InventoryState> actualInventoryStates = inventoryStateDao.getInventoryStates(productIdList);

        Product actualProduct = actualInventoryStates.get(0).getInventoryStatePK().getProduct();
        Assert.assertEquals("Actual result must be expected", actualProduct, product);
    }

    @Test
    public void testWhenOneOfTheTwoProductIsPresentInInventoryStateTable() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        Product product1 = productPersistentBuilder.buildAndAddProduct();

        InventoryState inventoryState = inventoryStateBuilder
                .withDate(LocalDateTime.parse("2016-10-14T20:00:10.976"))
                .withProduct(product)
                .withQuantity(7)
                .build();
        inventoryStateDao.add(inventoryState);

        InvoiceItem invoiceItem = invoiceItemBuilder
                .withProduct(product)
                .withProductQuantity(35)
                .build();

        invoiceItemBuilder.reset();

        InvoiceItem invoiceItem1 = invoiceItemBuilder
                .withProduct(product1)
                .withProductQuantity(16)
                .build();

        Invoice invoice = invoiceBuilder
                .withListInvoiceItems(ImmutableList.of(invoiceItem, invoiceItem1))
                .build();

        processInvoice.process(invoice);

        InventoryState actualInventoryState = inventoryStateDao
                .getInventoryStateByProductIdWhereMaxStateDate(invoiceItem);
        InventoryState actualInventoryState1 = inventoryStateDao
                .getInventoryStateByProductIdWhereMaxStateDate(invoiceItem1);

        Integer expectedQuantity = invoiceItem.getProductQuantity() + inventoryState.getQuantity();

        Assert.assertEquals("Actual result must be expected",
                actualInventoryState.getQuantity(), expectedQuantity);
        Assert.assertEquals("Actual result must be expected",
                actualInventoryState1.getQuantity(), invoiceItem1.getProductQuantity());
    }

    @Test
    @Rollback(false)
    public void testWhenInvoiceHasTwoTheSameProduct() {
        Product product = productPersistentBuilder.buildAndAddProduct();

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
}
