package com.revature.ers.dtos.requests;

public class DeleteRequest {
    private String id;

    public DeleteRequest(){
        super();
    }
    public DeleteRequest(String id){
        this.id = id;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    @Override
    public String toString() {
        return "DeleteRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
