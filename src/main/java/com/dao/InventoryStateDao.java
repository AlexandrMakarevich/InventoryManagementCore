package com.dao;

import com.entity.InventoryState;

public interface InventoryStateDao {

    void add(InventoryState inventoryState);

    InventoryState getById(int id);
}
