package com.revature.ers.dtos.requests;

public class TypeUpdateRequest {
    private String typeName;
    private String reimb_id;

    public TypeUpdateRequest(){
        super();
    }
    public TypeUpdateRequest(String typeName, String reimb_id){
        this.typeName = typeName;
        this.reimb_id = reimb_id;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String getTypeName() {
        return typeName.toUpperCase();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return "UpdateRequest{" +
                "type_id='" + typeName + '\'' +
                ", password='" + reimb_id + '\'' +
                '}';
    }
}
