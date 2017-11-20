package com.process;

import com.entity.InventoryState;
import com.entity.InvoiceItem;

public interface ProcessInvoiceProductQuantity {

    Integer processProductQuantity(InvoiceItem invoiceItem, InventoryState inventoryState);
}
