package com.process;

import com.entity.InventoryState;
import com.entity.InvoiceItem;

public class ProcessOutgoingInvoice implements ProcessInvoiceProductQuantity {


    @Override
    public Integer processProductQuantity(InvoiceItem invoiceItem, InventoryState inventoryState) {
        int count = inventoryState.getQuantity() - invoiceItem.getProductQuantity();
        if(count < 0) {
            throw new IllegalStateException("We don't have so much products by id "
                    + invoiceItem.getProduct().getId());
        }
        return count;
    }
}
