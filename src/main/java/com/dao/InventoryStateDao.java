package com.dao;

import com.entity.InventoryState;
import com.entity.InventoryStatePK;
import com.entity.InvoiceItem;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryStateDao {

    void saveInventoryState(InventoryState inventoryState);

    InventoryState getInventoryStateByPK(InventoryStatePK inventoryStatePK);

    InventoryState getInventoryStateByProductIdWhereMaxStateDate(InvoiceItem invoiceItem);

    List<InventoryState> getInventoryStates(List<Integer> strings);

    List<InventoryState> getActualInventoryStateByDate(LocalDateTime date);
}
