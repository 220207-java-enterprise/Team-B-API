package com.revature.ers.dtos.requests;


public class UpdateUserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String role_id;


    public UpdateUserRequest(){super();}

    public UpdateUserRequest(String id, String firstName, String lastName, String email, String username, String password, String role_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
    }

    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
    public String getRole_id(){return role_id;}
    public void setRole_id(String role_id){this.role_id = role_id;}

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
