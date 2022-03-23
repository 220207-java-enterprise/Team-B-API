package com.revature.ers.dtos.responses;

import com.revature.ers.models.Reimbursement;

public class ReimbursementResponse {
    private String id;
    private double amount;
    private String description;
    private String author_id;
    private String resolver_id;
    private String status;
    private String type;

    public ReimbursementResponse(){super();}

    public ReimbursementResponse(Reimbursement newReimbursement){
        this.id = newReimbursement.getId();
        this.amount = newReimbursement.getAmount();
        this.description = newReimbursement.getDescription();
        this.author_id = newReimbursement.getAuthor().getFirstName() + " " + newReimbursement.getAuthor().getLastName();
        this.status = newReimbursement.getReimbursementStatus().getStatusName();
        this.type = newReimbursement.getReimbursementType().getTypeName();

        if (newReimbursement.getResolver() != null)
            this.resolver_id = newReimbursement.getResolver().getFirstName() + " " + newReimbursement.getResolver().getLastName();
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public double getAmount(){return amount;}

    public void setAmount(double amount){this.amount = amount;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}

    public String getAuthor_id(){return author_id;}

    public void setAuthor_id(String author_id){this.author_id = author_id;}

    public String getResolver_id(){return resolver_id;}

    public void setResolver_id(String resolver_id){this.resolver_id = resolver_id;}

    public String getStatus(){return status;}

    public void setStatus(String status){this.status = status;}

    public String getType(){return type;}

    public void setType(String type){this.type = type;}
}
