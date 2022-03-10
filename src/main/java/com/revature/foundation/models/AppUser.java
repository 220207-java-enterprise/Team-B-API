package com.revature.foundation.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO make sure this is correct syntax + implement getters/setters and toString

@Entity
@Table(name = "ERS_Users")
public class AppUser {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "given_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column//TODO implement validation
    private String username;

    @Column//TODO implement validation
    private String password;

    @Embedded//TODO should this be embedded?
    private UserRole role;


}
