package com.revature.foundation.dtos.requests;

public class StatusFilterRequest {
    private String statusName;

    public StatusFilterRequest(){
        super();
    }

    public StatusFilterRequest(String statusName){
        this.statusName = statusName.toUpperCase();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String toString() {
        return "FilterRequest{" +
                "status_id='" + statusName+ '\'' +
                '}';
    }
}
