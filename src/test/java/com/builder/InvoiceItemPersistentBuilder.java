package com.builder;

import com.dao.InvoiceItemDao;
import com.entity.InvoiceItem;
import com.entity.Product;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("invoiceItemPersistentBuilder")
public class InvoiceItemPersistentBuilder {

    private InvoiceItemBuilder invoiceItemBuilder = new InvoiceItemBuilder();

    @Resource(name = "productPersistentBuilder")
    private ProductPersistentBuilder productPersistentBuilder;

    @Resource(name = "invoiceItemDaoImpl")
    private InvoiceItemDao invoiceItemDao;

    public InvoiceItem buildAndAddInvoiceItem() {
        Product product = productPersistentBuilder.buildAndAddProduct();
        InvoiceItem invoiceItem = invoiceItemBuilder.withProduct(product).build();
        invoiceItemDao.addInvoiceItem(invoiceItem);
        return invoiceItem;
    }
}
