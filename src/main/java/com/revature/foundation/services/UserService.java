package com.revature.foundation.services;

import com.revature.foundation.dtos.requests.ApproveUserRequest;
import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.requests.NewUserRequest;
import com.revature.foundation.dtos.responses.AppUserResponse;
import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.models.AppUser;
import com.revature.foundation.models.UserRole;
import com.revature.foundation.repos.UserRepository;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import com.revature.foundation.util.exceptions.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepo;
    private TokenService tokenService;

    @Autowired
    public UserService(UserRepository userRepo, TokenService tokenService) {
        this.userRepo = userRepo;
        this.tokenService = tokenService;
    }

    public List<AppUserResponse> getAllUsers() {
        return userRepo.findAllActive()
                .stream()
                .map(AppUserResponse::new)
                .collect(Collectors.toList());
    }

    public void register(NewUserRequest newUserRequest) {

        AppUser newUser = newUserRequest.extractUser();

        System.out.println(newUser.getEmail());

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Bad registration details given.");
        }

        boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
        boolean emailAvailable = isEmailAvailable(newUser.getEmail());

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users: ";
            if (!usernameAvailable) msg += "user_name";
            if (!emailAvailable) msg += "email";
            throw new ResourceConflictException(msg);
        }

        // TODO encrypt provided password before storing in the database

        newUser.setId(UUID.randomUUID().toString());
        newUser.setRole(new UserRole("7c3521f5-ff75-4e8a-9913-01d15ee4dc98", "EMPLOYEE")); // All newly registered users start as EMPLOYEE
        System.out.println(newUser);
        userRepo.save(newUser);
    }

    public String login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (!isUsernameValid(username) || !(isPasswordValid(password)))
            throw new InvalidRequestException("Invalid credentials given.");

        AppUser authUser =  userRepo.findByUsername(request.getUsername());
        if (authUser.equals(null))
            throw new AuthenticationException();

        // TODO: Authenticate password

        Principal principal = new Principal(authUser);
        return tokenService.generateToken(principal);
    }

    public void approve(ApproveUserRequest request) {
        userRepo.approveUser(request.getId());
    }
//
//    public AppUser login(LoginRequest loginRequest) {
//
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//
//        //if (!isUsernameValid(username) || !isPasswordValid(password)) {
//            //throw new InvalidRequestException("Invalid credentials provided!");
//        //}
//
//        // TODO encrypt provided password (assumes password encryption is in place) to see if it matches what is in the DB
//
//        AppUser authUser = userDAO.findUserByUsernameAndPassword(username, password);
//
//        if (authUser == null) {
//            throw new AuthenticationException();
//        }
//
//        return authUser;
//
//    }
//
//    public AppUser delete(DeleteRequest deleteRequest){
//        String id = deleteRequest.getId();
//
//        AppUser appUser = userDAO.getById(id);
//
//        userDAO.deleteById(id);
//
//        return appUser;
//    }
//
//    public AppUser update(UpdateUserRequest updateUserRequest){
//        String id = updateUserRequest.getId();
//
//        AppUser appUser = userDAO.getById(id);
//
//        if (updateUserRequest.getEmail() != null){
//            appUser.setEmail(updateUserRequest.getEmail());
//        }
//
//        if (updateUserRequest.getFirstName() != null){
//            appUser.setFirstName(updateUserRequest.getFirstName());
//        }
//
//        if (updateUserRequest.getLastName() != null){
//            appUser.setLastName(updateUserRequest.getLastName());
//        }
//
//        if (updateUserRequest.getPassword() != null){
//            appUser.setPassword(updateUserRequest.getPassword());
//        }
//
//        if (updateUserRequest.getUsername() != null){
//            appUser.setUsername(updateUserRequest.getUsername());
//        }
//
//        if (updateUserRequest.getRole_id() != null){
//            if (updateUserRequest.getRole_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc96")){
//                appUser.setRole(new UserRole("7c3521f5-ff75-4e8a-9913-01d15ee4dc96","ADMIN"));
//            }
//            else if (updateUserRequest.getRole_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc97")){
//                appUser.setRole(new UserRole("7c3521f5-ff75-4e8a-9913-01d15ee4dc97","FINANCE MANAGER"));
//            }
//            else if (updateUserRequest.getRole_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc98")){
//                appUser.setRole(new UserRole("7c3521f5-ff75-4e8a-9913-01d15ee4dc98","EMPLOYEE"));
//            }
//        }
//
//        userDAO.update(appUser);
//
//        return appUser;
//    }
//
    private boolean isUserValid(AppUser appUser) {

        // First name and last name are not just empty strings or filled with whitespace
        if (appUser.getFirstName().trim().equals("") || appUser.getLastName().trim().equals("")) {
            return false;
        }

        // Usernames must be a minimum of 8 and a max of 25 characters in length, and only contain alphanumeric characters.
        if (!isUsernameValid(appUser.getUsername())) {
            return false;
        }

        // Password is between 8 and 25 characters
        if (!isPasswordValid(appUser.getPassword())) {
            return false;
        }

        // Basic email validation
        return isEmailValid(appUser.getEmail());

    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;

        System.out.println("isEmailValid: " + email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$"));

        return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
    }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9]{8,25}");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^[^\\s]{8,25}");
    }

    public boolean isUsernameAvailable(String username) {
        return userRepo.findByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        return userRepo.findByEmail(email) == null;
    }

}

