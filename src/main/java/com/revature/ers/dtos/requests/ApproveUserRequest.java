package com.revature.ers.dtos.requests;

public class ApproveUserRequest {
    private String id;

    public ApproveUserRequest() {
        super();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ApproveUserRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
