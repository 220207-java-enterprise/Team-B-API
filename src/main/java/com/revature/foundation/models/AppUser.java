package com.revature.foundation.models;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Entity
@Table(name = "ERS_Users")
public class AppUser {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "given_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String lastName;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToMany(
        mappedBy = "author",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    private List<Reimbursement> authorReimbursements;

    @OneToMany(
            mappedBy = "resolver",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Reimbursement> resolverReimbursements;

    public AppUser() {
        super();
    }

    public AppUser(String id) {
        this.id = id;
    }

    public AppUser(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Reimbursement> getAuthorReimbursements() {
        return authorReimbursements;
    }

    public void setAuthorReimbursements(List<Reimbursement> authorReimbursements) {
        this.authorReimbursements = authorReimbursements;
    }

    public List<Reimbursement> getResolverReimbursements() {
        return resolverReimbursements;
    }

    public void setResolverReimbursements(List<Reimbursement> resolverReimbursements) {
        this.resolverReimbursements = resolverReimbursements;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                ", authorReimbursements=" + authorReimbursements +
                ", resolverReimbursements=" + resolverReimbursements +
                '}';
    }
}
