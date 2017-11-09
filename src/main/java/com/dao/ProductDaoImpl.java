package com.dao;

import com.entity.Product;
import org.springframework.stereotype.Repository;

@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDao implements ProductDao {

    @Override
    public int addProduct(Product product) {
        getSession().save(product);
        return product.getId();
    }

    @Override
    public Product getById(int id) {
        return getSession().load(Product.class, id);
    }
}
