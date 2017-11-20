package com.dao;

import com.entity.Product;

public interface ProductDao {

    int saveProduct(Product product);

    Product getProductById(int id);
}
