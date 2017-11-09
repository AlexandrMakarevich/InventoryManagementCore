package com.dao;

import com.entity.Product;

public interface ProductDao {

    int addProduct(Product product);

    Product getById(int id);
}
