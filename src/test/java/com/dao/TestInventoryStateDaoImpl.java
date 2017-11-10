package com.dao;

import com.BaseTest;
import com.builder.InventoryStateBuilder;
import com.builder.ProductPersistentBuilder;
import com.entity.InventoryState;
import com.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import javax.annotation.Resource;

public class TestInventoryStateDaoImpl extends BaseTest {

    @Resource(name = "inventoryStateDaoImpl")
    private InventoryStateDao inventoryStateDao;

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    private InventoryStateBuilder inventoryStateBuilder;

    @Before
    public void init() {
        inventoryStateBuilder = new InventoryStateBuilder();
    }

    @Test
    @Rollback(false)
    public void testAddAndGetInventoryState() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        InventoryState inventoryState = inventoryStateBuilder.withProduct(product).build();
        inventoryStateDao.add(inventoryState);
        InventoryState actualInventoryState = inventoryStateDao.getById(inventoryState.getId());
        System.out.println(inventoryState);
        System.out.println(actualInventoryState);
        Assert.assertEquals("ActualInventoryState must be equal inventoryState", actualInventoryState, inventoryState);
    }
}
