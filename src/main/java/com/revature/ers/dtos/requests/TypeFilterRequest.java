package com.revature.ers.dtos.requests;

public class TypeFilterRequest {
    private String typeName;

    public TypeFilterRequest(){
        super();
    }

    public TypeFilterRequest(String typeName){
        this.typeName = typeName.toUpperCase();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return "FilterRequest{" +
                "type_id='" + typeName + '\'' +
                '}';
    }
}
