package com.dao;

import com.BaseTest;
import com.builder.ProductBuilder;
import com.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class TestProductDao extends BaseTest {

    private ProductBuilder productBuilder;

    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

    @Before
    public void init() {
        productBuilder = new ProductBuilder();
    }

    @Test
//    @Rollback(false)
    public void testAddProduct() {
        Product product = productBuilder.build();
        int expectedId = productDao.addProduct(product);
        Product actualProduct = productDao.getById(expectedId);
        Assert.assertEquals("Actual product must be equal expected", actualProduct, product);
    }

}
