package com.process;

import com.entity.InventoryState;
import com.entity.InvoiceItem;

public class ProcessIncomingInvoice implements ProcessInvoiceProductQuantity {

    @Override
    public Integer processProductQuantity(InvoiceItem invoiceItem, InventoryState inventoryState) {
        return inventoryState.getQuantity() + invoiceItem.getProductQuantity();
    }
}
