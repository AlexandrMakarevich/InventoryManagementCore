package com.entity;

import com.constant.InvoiceStatus;
import com.constant.InvoiceType;
import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="status", columnDefinition="ENUM('PENDING', 'COMPLETE', 'ERROR')")
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
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
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
