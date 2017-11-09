package com.entity;

import com.constant.InvoiceStatus;
import com.constant.InvoiceType;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @Column(name = "date")
    private Instant date = Instant.now();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "invoice_item_map",
            joinColumns =  @JoinColumn(name = "product_id") ,
            inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<InvoiceItem> invoiceItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public InvoiceType getType() {
        return type;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
