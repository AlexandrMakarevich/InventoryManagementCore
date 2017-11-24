package com.process_invoice;

import com.BaseTest;
import com.builder.InventoryStateBuilder;
import com.builder.InvoiceBuilder;
import com.builder.InvoiceItemBuilder;
import com.dao.InventoryStateDao;
import com.entity.InventoryState;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.entity.Product;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.process.ProcessInvoice;
import org.junit.Assert;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class TestProcessInvoice extends BaseTest {

    @Resource(name = "processInvoice")
    private ProcessInvoice processInvoice;

    @Resource(name = "inventoryStateDaoImpl")
    private InventoryStateDao inventoryStateDao;

    private InvoiceItemBuilder invoiceItemBuilder = new InvoiceItemBuilder();
    private InventoryStateBuilder inventoryStateBuilder = new InventoryStateBuilder();

    public Invoice createInvoice(Map<Product, Integer> mapProductQuantity, InvoiceBuilder invoiceBuilder) {
        Set<InvoiceItem> invoiceItems = new HashSet<>();
        for (Map.Entry<Product, Integer> entry : mapProductQuantity.entrySet()) {
            InvoiceItem invoiceItem = invoiceItemBuilder
                    .withProduct(entry.getKey())
                    .withProductQuantity(entry.getValue())
                    .build();
            invoiceItemBuilder.reset();
            invoiceItems.add(invoiceItem);
        }
        Invoice invoice1 = invoiceBuilder
                .withSetInvoiceItems(invoiceItems)
                .build();
        return invoice1;
    }

    public void assertInventoryState(Map<Product, Integer> mapProductQuantity, Invoice invoice) {
        List<Integer> productsId = processInvoice.getAllProductId(invoice.getInvoiceItems());
        List<InventoryState> inventoryStates = inventoryStateDao.getInventoryStates(productsId);
        Assert.assertEquals("Size productsId must be equal size inventorySates",
                productsId.size(), inventoryStates.size());
        for (Map.Entry<Product, Integer> entry : mapProductQuantity.entrySet()) {
            Optional<InventoryState> inventoryStateOptional =
                    Iterables.tryFind(inventoryStates, new Predicate<InventoryState>() {
                        @Override
                        public boolean apply(InventoryState input) {
                            return input.getInventoryStatePK().getProduct().equals(entry.getKey());
                        }
                    });
            Assert.assertTrue("InventoryState must be found for product" +
                    entry.getKey(), inventoryStateOptional.isPresent());
            Assert.assertEquals("Actual result must be expected",
                    entry.getValue(), inventoryStateOptional.get().getQuantity());
        }
    }

    public void createAndSaveInventoryState(Map<Product, Integer> mapProductsAndQuantity) {
        for (Map.Entry<Product, Integer> entry : mapProductsAndQuantity.entrySet()) {
            InventoryState inventoryState = inventoryStateBuilder
                    .withProduct(entry.getKey())
                    .withQuantity(entry.getValue())
                    .build();
            inventoryStateDao.saveInventoryState(inventoryState);
        }
    }
}
