package com.dao;

import com.entity.Product;
import org.springframework.stereotype.Repository;

@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDao implements ProductDao {

    @Override
    public int saveProduct(Product product) {
        getSession().save(product);
        return product.getId();
    }

    @Override
    public Product getProductById(int id) {
        return getSession().get(Product.class, id);
    }
}
