package com.revature.ers.dtos.responses;

public class DeleteResponse {

    private String id;

    public DeleteResponse(){
        super();
    }
    public DeleteResponse(String id){
        this.id = id;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    @Override
    public String toString() {
        return "DeleteResponse{" +
                "id='" + id + '\'' +
                "deleted"+
                '}';
    }
}
