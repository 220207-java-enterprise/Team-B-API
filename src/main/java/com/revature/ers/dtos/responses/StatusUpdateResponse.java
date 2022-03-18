package com.revature.ers.dtos.responses;

import com.revature.ers.models.Reimbursement;

public class StatusUpdateResponse {
    private String id;
    private String status_name;

    public StatusUpdateResponse(){super();}

    public StatusUpdateResponse(Reimbursement reimbursement){
        this.id = reimbursement.getId();
        this.status_name = reimbursement.getReimbursementStatus().getStatusName();
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public String getStatus_name(){return status_name;}
    public void setStatus_name(String status_name){this.status_name = status_name;}
}
