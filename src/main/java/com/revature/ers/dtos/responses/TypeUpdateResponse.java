package com.revature.ers.dtos.responses;

import com.revature.ers.models.Reimbursement;

public class TypeUpdateResponse {
    private String id;
    private String type_name;

    public TypeUpdateResponse(){super();}

    public TypeUpdateResponse(Reimbursement reimbursement){
        this.id = reimbursement.getId();
        this.type_name = reimbursement.getReimbursementType().getTypeName();
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public String getType_name(){return type_name;}
    public void setType_name(String status_name){this.type_name = type_name;}
}
