package com.entity;

import com.constant.InvoiceStatus;
import com.constant.InvoiceType;
import com.google.common.base.Objects;
import javax.persistence.*;
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

    @Column(name = "type", columnDefinition = "ENUM('IN', 'OUT')")
    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "invoice_item_map",
            inverseJoinColumns =  @JoinColumn(name = "invoice_item_id") ,
            joinColumns = @JoinColumn(name = "invoice_id"))
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
        return Objects.equal(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", invoiceItems=" + invoiceItems +
                '}';
    }
}
