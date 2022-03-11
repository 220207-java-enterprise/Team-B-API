package com.revature.foundation.dtos.requests;

import com.revature.foundation.models.Reimbursement;

public class UpdateReimbursementRequest {
    private float amount;
    private String description;
    private String id;

    public UpdateReimbursementRequest(){ super();}

    public UpdateReimbursementRequest(float amount, String description,  String id){
        this.amount = amount;
        this.description = description;
        this.id = id;
    }

    public float getAmount(){return amount;}

    public void setAmount(float amount){this.amount = amount;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getId(){return id;}

    public void setId(String id){this.id = id;}



    public String toString() {
        return "UpdateReimbursementRequest{" +
                "amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
