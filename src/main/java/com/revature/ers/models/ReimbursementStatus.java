package com.revature.ers.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="ers_reimbursement_statuses")
public class ReimbursementStatus {

    @Id
    @Column(name = "status_id")
    private String id;

    @Column(name = "status", unique = true, nullable = false)
    private String statusName;

    @OneToMany(
        mappedBy = "status",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private List<Reimbursement> reimbursements;

    public ReimbursementStatus() {
        super();
    }

    public ReimbursementStatus(String id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setTypeName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementStatus status = (ReimbursementStatus) o;
        return Objects.equals(id, status.id) && Objects.equals(statusName, status.statusName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusName);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", typeName='" + statusName + '\'' +
                '}';
    }

}