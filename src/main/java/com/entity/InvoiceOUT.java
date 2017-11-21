package com.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OUT")
public class InvoiceOUT extends Invoice {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceOUT.class);

    @Override
    public Integer processProductQuantity(InvoiceItem invoiceItem, InventoryState inventoryState) {
        int count = inventoryState.getQuantity() - invoiceItem.getProductQuantity();
        if (count < 0) {
            String message = String.format("You want %s product with id %s, but we have only %s",
                    invoiceItem.getProductQuantity(), invoiceItem.getProduct().getId(),
                    inventoryState.getQuantity());
            LOGGER.info(message);
            throw new IllegalStateException(message);
        }
        return count;
    }
}
