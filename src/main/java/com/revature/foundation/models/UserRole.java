package com.revature.foundation.models;

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

    public UserRole(String id, String roleName) {
        this.id = id;
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
