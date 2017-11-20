package com.process;

import com.constant.InvoiceType;
import com.dao.InventoryStateDao;
import com.dao.InvoiceDao;
import com.entity.InventoryState;
import com.entity.InventoryStatePK;
import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service("processInvoice")
public class ProcessInvoice {

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;

    @Resource(name = "inventoryStateDaoImpl")
    private InventoryStateDao inventoryStateDao;

    private ProcessInvoiceProductQuantity processInvoiceProductQuantity;

    private static final Logger LOGGER = Logger.getLogger(ProcessInvoice.class);

    public void process(Invoice invoice) {
        if (invoice.getInvoiceItems().size() == 0) {
            LOGGER.info("Invoice must have at least one product");
            throw new IllegalStateException("Invoice must have at least one product");
        }
        List<Integer> productIdsList = getAllProductId(invoice.getInvoiceItems());
        List<InventoryState> inventoryStateList = inventoryStateDao.getInventoryStates(productIdsList);
        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            InventoryState inventoryState = Iterables.find(inventoryStateList, new Predicate<InventoryState>() {
                @Override
                public boolean apply(InventoryState input) {
                    return input.getInventoryStatePK().getProduct().getId().equals(invoiceItem.getProduct().getId());
                }
            });
            InventoryState inventoryState1 = new InventoryState();
            inventoryState1.setInventoryStatePK(new InventoryStatePK());
            inventoryState1.setQuantity(inventoryState.getQuantity() + invoiceItem.getProductQuantity());
            inventoryState1.getInventoryStatePK().setStateDate(LocalDateTime.now());
            inventoryState1.getInventoryStatePK().setProduct(inventoryState.getInventoryStatePK().getProduct());
            if (invoice.getType() == InvoiceType.IN) {
                processInvoiceProductQuantity = new ProcessIncomingInvoice();
                inventoryState1.setQuantity(processInvoiceProductQuantity
                        .processProductQuantity(invoiceItem, inventoryState));
            } else {
                processInvoiceProductQuantity = new ProcessOutgoingInvoice();
                inventoryState1.setQuantity(processInvoiceProductQuantity
                        .processProductQuantity(invoiceItem, inventoryState));
            }
            inventoryStateDao.saveInventoryState(inventoryState1);
        }
    }

    public List<Integer> getAllProductId(Set<InvoiceItem> invoiceItems) {
        Iterable<Integer> listOfProductId = Iterables.transform(invoiceItems, new Function<InvoiceItem, Integer>() {
            @Override
            public Integer apply(InvoiceItem input) {
                return input.getProduct().getId();
            }
        });
        return ImmutableList.copyOf(listOfProductId);
    }
}
