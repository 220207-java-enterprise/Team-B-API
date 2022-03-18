package com.revature.ers.dtos.responses;

import com.revature.ers.models.Reimbursement;

public class UpdateReimbursementResponse {
    private double amount;
    private String description;
    private String id;

    public UpdateReimbursementResponse(){ super();}

    public UpdateReimbursementResponse(Reimbursement reimbursement){
        this.amount = reimbursement.getAmount();
        this.description = reimbursement.getDescription();
        this.id = reimbursement.getId();
    }

    public double getAmount(){return amount;}

    public void setAmount(double amount){this.amount = amount;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getId(){return id;}

    public void setId(String id){this.id = id;}
}
