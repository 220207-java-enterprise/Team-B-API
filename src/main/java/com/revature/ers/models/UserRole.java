package com.revature.ers.models;

import com.revature.ers.util.exceptions.InvalidRequestException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ers_user_roles")
public class UserRole {

    @Id
    @Column(name = "role_id")
    private String id;

    @Column(name = "role", nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppUser> users;

    public UserRole() {
        super();
    }

    public UserRole(String roleName) {
        switch (roleName) {
            case "EMPLOYEE":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc98";
                break;
            case "FINANCE MANAGER":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc97";
                break;
            case "ADMIN":
                this.id = "7c3521f5-ff75-4e8a-9913-01d15ee4dc96";
                break;
            default:
                throw new InvalidRequestException("Role \"" + roleName + "\" is not valid.");
        }

        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(roleName, userRole.roleName) && Objects.equals(users, userRole.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, users);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                '}';
    }
}
