package com.revature.foundation.models;

import com.revature.foundation.util.exceptions.InvalidRequestException;

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

    public ReimbursementType( String typeName) {
        switch (typeName) {
            case "LODGING":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc9a";
                break;
            case "TRAVEL":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc9b";
                break;
            case "FOOD":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc9c";
                break;
            case "OTHER":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc9d";
                break;
            default:
                throw new InvalidRequestException("Type \"" + typeName + "\" is not valid.");
        }

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