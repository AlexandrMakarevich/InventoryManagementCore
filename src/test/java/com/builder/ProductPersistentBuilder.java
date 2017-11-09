package com.builder;

import com.dao.ProductDao;
import com.entity.Product;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("productPersistentBuilder")
public class ProductPersistentBuilder {

    private ProductBuilder productBuilder = new ProductBuilder();

    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

    public Product buildAndAddProduct() {
        Product product = productBuilder.build();
        productDao.addProduct(product);
        return product;
    }
}
