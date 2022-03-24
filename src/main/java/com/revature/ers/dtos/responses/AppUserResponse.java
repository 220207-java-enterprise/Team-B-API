package com.revature.ers.dtos.responses;

import com.revature.ers.models.AppUser;

public class AppUserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String status;

    public AppUserResponse() {
        super();
    }

    public AppUserResponse(AppUser user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.role = user.getRole().getRoleName();

        if (user.getActive())
            this.status = "Active";
        else
            this.status = "Inactive";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
