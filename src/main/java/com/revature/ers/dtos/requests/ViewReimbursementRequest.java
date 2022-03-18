package com.revature.ers.dtos.requests;

public class ViewReimbursementRequest {
    private String author_id;

    public ViewReimbursementRequest(){
        super();
    }

    public ViewReimbursementRequest(String author_id){
        this.author_id = author_id;
    }

    public String getAuthor_id(){return author_id;}
    public void setAuthor_id(String author_id){this.author_id=author_id;}


    public String toString() {
        return "FilterRequest{" +
                "author_id='" + author_id + '\'' +
                '}';
    }
}
