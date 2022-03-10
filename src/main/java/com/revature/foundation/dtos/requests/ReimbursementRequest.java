package com.revature.foundation.dtos.requests;

import com.revature.foundation.models.Reimbursement;

public class ReimbursementRequest {
    private float amount;
    private String description;
    private String author_id;

    public ReimbursementRequest(){ super();}

    public ReimbursementRequest(float amount, String description, String author_id){
        this.amount = amount;
        this.description = description;
        this.author_id = author_id;
    }

    public double getAmount(){return amount;}

    public void setAmount(float amount){this.amount = amount;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getAuthor_id(){return author_id;}

    public void setAuthor_id(String author_id){this.author_id=author_id;}


    public Reimbursement extractReimbursement() {
        return new Reimbursement(amount, description,author_id);
    }

    public String toString() {
        return "ReimbursementRequest{" +
                "amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
