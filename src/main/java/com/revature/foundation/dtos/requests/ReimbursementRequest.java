package com.revature.foundation.dtos.requests;

import com.revature.foundation.models.Reimbursement;

public class ReimbursementRequest {
    private float amount;
    private String description;
    private String type;

    public ReimbursementRequest(){ super();}

    public ReimbursementRequest(float amount, String description, String type){
        this.amount = amount;
        this.description = description;
        this.type = type.toUpperCase();
    }

    public double getAmount(){return amount;}

    public void setAmount(float amount){this.amount = amount;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public Reimbursement extractReimbursement() {
        return new Reimbursement(amount, description);
    }

    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}