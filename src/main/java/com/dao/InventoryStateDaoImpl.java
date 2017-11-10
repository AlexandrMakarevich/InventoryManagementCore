package com.dao;

import com.entity.InventoryState;
import org.springframework.stereotype.Repository;

@Repository("inventoryStateDaoImpl")
public class InventoryStateDaoImpl extends BaseDao implements InventoryStateDao {

    @Override
    public void add(InventoryState inventoryState) {
        getSession().save(inventoryState);
    }

    @Override
    public InventoryState getById(int id) {
        return getSession().get(InventoryState.class, id);
    }
}
