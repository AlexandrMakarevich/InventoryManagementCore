package com.dao;

import com.entity.Invoice;
import com.entity.InvoiceItem;
import com.entity.Product;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Repository("invoiceDaoImpl")
public class InvoiceDaoImpl extends BaseDao implements InvoiceDao{

    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

    @Override
    public void saveInvoice(Invoice invoice) {
        checkAndProcessProduct(invoice.getInvoiceItems());
        getSession().saveOrUpdate(invoice);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return getSession().get(Invoice.class, id);
    }

    @Override
    public List<Invoice> getAllPendingInvoice() {
        String sql = "select * from invoice where status = 'PENDING' order by type asc, id asc";
        NativeQuery<Invoice> nativeQuery = getSession().createNativeQuery(sql, Invoice.class);
        return nativeQuery.list();
    }

    private void checkAndProcessProduct(Set<InvoiceItem> invoiceItems) {
        if (invoiceItems.isEmpty()) {
            throw new IllegalStateException("Invoice must have at least one product");
        }
        for (InvoiceItem invoiceItem : invoiceItems) {
            Product product = invoiceItem.getProduct();
            if (productDao.getProductById(product.getId()) == null) {
                productDao.saveProduct(product);
            }
        }
    }
}
