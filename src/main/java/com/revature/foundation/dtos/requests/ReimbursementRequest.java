package com.revature.foundation.dtos.requests;

import com.revature.foundation.models.Reimbursement;

public class ReimbursementRequest {
    private float amount;
    private String description;

    public ReimbursementRequest(){ super();}

    public ReimbursementRequest(float amount, String description){
        this.amount = amount;
        this.description = description;
    }

    public double getAmount(){return amount;}

    public void setAmount(float amount){this.amount = amount;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}


    public Reimbursement extractReimbursement() {
        return new Reimbursement(amount, description);
    }

    public String toString() {
        return "ReimbursementRequest{" +
                "amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
