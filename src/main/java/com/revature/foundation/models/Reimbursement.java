package com.revature.foundation.models;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="ers_reimbursements")
public class Reimbursement {
    @Id
    @Column(name = "reimb_id")
    private String id;

    @Column(nullable = false, columnDefinition = "numeric(6,2)")
    private float amount;

    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Timestamp submitted;

    @Column
    private Timestamp resolved;

    @Column(nullable = false)
    private String description;

    // private Blob? reciept

    @Column
    private String paymentId;

    @Column(nullable = false)
    private String author_id;

    @Column
    private String resolver_id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ReimbursementStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ReimbursementType type;

    public Reimbursement() {
        super();
    }

    public Reimbursement(float amount,String description, String author_id) {
        this.amount = amount;
        this.description = description;
        this.author_id = author_id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }
    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }
    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAuthor_id() {
        return author_id;
    }
    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }
    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    public ReimbursementStatus getReimbursementStatus() {
        return status;
    }
    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    public ReimbursementType getReimbursementType() {
        return type;
    }
    public void setType(ReimbursementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", author_id='" + author_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
