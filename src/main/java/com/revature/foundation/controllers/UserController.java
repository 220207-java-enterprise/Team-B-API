package com.revature.foundation.controllers;

import com.revature.foundation.dtos.responses.AppUserResponse;
import com.revature.foundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AppUserResponse> getAllUsers()  {
        return userService.getAllUsers();
    }
}
