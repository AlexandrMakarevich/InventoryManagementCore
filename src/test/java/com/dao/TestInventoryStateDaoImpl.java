package com.dao;

import com.BaseTest;
import com.builder.*;
import com.entity.InventoryState;
import com.entity.InvoiceItem;
import com.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Resource;

public class TestInventoryStateDaoImpl extends BaseTest {

    @Resource(name = "inventoryStateDaoImpl")
    private InventoryStateDao inventoryStateDao;

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    private InventoryStateBuilder inventoryStateBuilder;
    private InvoiceItemBuilder invoiceItemBuilder;

    @Before
    public void init() {
        inventoryStateBuilder = new InventoryStateBuilder();
        invoiceItemBuilder = new InvoiceItemBuilder();
    }

    @Test
//    @Rollback(false)
    public void testAddAndGetInventoryState() {
        Product product = productPersistentBuilder.buildAndAddProduct();

        InventoryState inventoryState = inventoryStateBuilder.withProduct(product).build();
        inventoryStateDao.add(inventoryState);

        InvoiceItem invoiceItem = invoiceItemBuilder.withProduct(product).build();

        InventoryState actualInventoryState = inventoryStateDao.getInventoryStateByProductIdWhereMaxStateDate(invoiceItem);
        System.out.println(inventoryState);
        System.out.println(actualInventoryState);
        Assert.assertEquals("ActualInventoryState must be equal inventoryState", actualInventoryState, inventoryState);
    }
}
