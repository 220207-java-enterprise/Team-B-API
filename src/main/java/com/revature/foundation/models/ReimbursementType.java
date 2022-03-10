package com.revature.foundation.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="ers_reimbursement_types")
public class ReimbursementType {

    @Id
    @Column(name = "type_id")
    private String id;

    @Column(name = "type", unique = true, nullable = false)
    private String typeName;

    @OneToMany(
            mappedBy = "type",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Reimbursement> reimbursements;

    public ReimbursementType() {
        super();
    }

    public ReimbursementType(String id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementType type = (ReimbursementType) o;
        return Objects.equals(id, type.id) && Objects.equals(typeName, type.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }

}
