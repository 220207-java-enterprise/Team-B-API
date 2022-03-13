package com.revature.foundation.dtos.requests;


public class StatusUpdateRequest {
    private String statusName;
    private String reimb_id;

    public StatusUpdateRequest(){
        super();
    }
    public StatusUpdateRequest(String statusName, String reimb_id){
        this.statusName = statusName;
        this.reimb_id = reimb_id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName.toUpperCase();
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String toString() {
        return "UpdateRequest{" +
                "status_id='" + statusName + '\'' +
                ", password='" + reimb_id + '\'' +
                '}';
    }
}
