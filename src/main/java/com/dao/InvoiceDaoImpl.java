package com.dao;

import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.entity.Product;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;

@Repository("invoiceDaoImpl")
public class InvoiceDaoImpl extends BaseDao implements InvoiceDao{

    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

    @Override
    public void add(Invoice invoice) {
        checkAndProcessProduct(invoice.getInvoiceItems());
        getSession().save(invoice);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return getSession().get(Invoice.class, id);
    }

    private void checkAndProcessProduct(List<InvoiceItem> invoiceItems) {
        if (invoiceItems.size() == 0) {
            throw new IllegalStateException("Invoice must have at least one product");
        }
        for (InvoiceItem invoiceItem : invoiceItems) {
            Product product = invoiceItem.getProduct();
            if (productDao.getById(product.getId()) == null) {
                productDao.addProduct(product);
            }
        }
    }
}
