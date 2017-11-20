package com.builder;

import com.dao.ProductDao;
import com.entity.Product;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("productPersistentBuilder")
public class ProductPersistentBuilder {

    private ProductBuilder productBuilder;

    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

    public Product buildAndAddProduct() {
        productBuilder = new ProductBuilder();
        Product product = productBuilder.build();
        productDao.saveProduct(product);
        return product;
    }
}
