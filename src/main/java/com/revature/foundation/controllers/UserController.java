package com.revature.foundation.controllers;

import com.revature.foundation.dtos.requests.ApproveUserRequest;
import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.requests.NewUserRequest;
import com.revature.foundation.dtos.responses.AppUserResponse;
import com.revature.foundation.models.AppUser;
import com.revature.foundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
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

    @ResponseStatus(HttpStatus.ACCEPTED)//accepted because admin needs to approve request
    @PostMapping
    public void registerNewUser(@RequestBody NewUserRequest request) {
        userService.register(request);
    }

    @PostMapping("login")
    public void login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = userService.login(request);
        response.setHeader("Authorization", token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "approve")
    public void approveUser(@RequestBody ApproveUserRequest request) {
        userService.approve(request);
    }
}
