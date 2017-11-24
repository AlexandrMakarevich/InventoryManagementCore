package com.service;

import com.constant.InvoiceStatus;
import com.dao.InvoiceDao;
import com.entity.Invoice;
import com.process.ProcessInvoice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service("invoiceService")
public class InvoiceService {

    @Resource(name = "invoiceDaoImpl")
    private InvoiceDao invoiceDao;

    @Resource(name = "processInvoice")
    private ProcessInvoice processInvoice;

    @Transactional
    public void processPendingInvoices() {
        List<Invoice> invoicePendingList = invoiceDao.getAllPendingInvoice();
        for (Invoice invoice : invoicePendingList) {
            try {
                processInvoice.process(invoice);
                invoice.setStatus(InvoiceStatus.COMPLETE);
            } catch (Exception e) {
                invoice.setStatus(InvoiceStatus.ERROR);
            }
            invoiceDao.saveInvoice(invoice);
        }
    }
}
