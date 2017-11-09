package com.entity;

import com.constant.InvoiceStatus;
import com.constant.InvoiceType;
import com.google.common.base.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equal(id, invoice.id) &&
                status == invoice.status &&
                type == invoice.type &&
                Objects.equal(date, invoice.date) &&
                Objects.equal(invoiceItems, invoice.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, status, type, date, invoiceItems);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", date=" + date +
                ", invoiceItems=" + invoiceItems +
                '}';
    }
}
