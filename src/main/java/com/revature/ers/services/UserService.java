package com.revature.ers.services;

import com.revature.ers.dtos.requests.ApproveUserRequest;
import com.revature.ers.dtos.requests.DeleteRequest;
import com.revature.ers.dtos.requests.LoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.AppUserResponse;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.AppUser;
import com.revature.ers.models.UserRole;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.util.exceptions.AuthenticationException;
import com.revature.ers.util.exceptions.InvalidRequestException;
import com.revature.ers.util.exceptions.ResourceConflictException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    public List<AppUserResponse> getAllUsers(String token, HttpServletResponse response) {
        Principal principal = tokenService.extractRequesterDetails(token);
        if (!principal.getRole().equals("ADMIN")) {
            response.setStatus(403);
            return null;
        }

        return userRepo.findAllActive()
                .stream()
                .map(AppUserResponse::new)
                .collect(Collectors.toList());
    }

    public List<AppUserResponse> getAllInactiveUsers(String token, HttpServletResponse response) {
        Principal principal = tokenService.extractRequesterDetails(token);
        if (!principal.getRole().equals("ADMIN")) {
            response.setStatus(403);
            return null;
        }

        return userRepo.findAllInactive()
                .stream()
                .map(AppUserResponse::new)
                .collect(Collectors.toList());
    }

    public void register(NewUserRequest newUserRequest) {

        AppUser newUser = newUserRequest.extractUser();

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

        newUser.setPassword(
                BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(10))
        );

        if (newUserRequest.getRole() != null)
            newUser.setRole(new UserRole(newUserRequest.getRole()));
        else
            // if no role provided in request, user is employee
            newUser.setRole(new UserRole("EMPLOYEE"));

        newUser.setId(UUID.randomUUID().toString());
        //users are automatically instantiated as false(rather than NULL)
        newUser.setActive(false);

        userRepo.save(newUser);
    }

    public Principal login(LoginRequest request, HttpServletResponse response) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (!isUsernameValid(username) || !(isPasswordValid(password)))
            throw new InvalidRequestException("Invalid credentials given.");

        AppUser authUser =  userRepo.findByUsername(request.getUsername());
        if (authUser == null)
            throw new AuthenticationException();

        // TODO: Authenticate password

        Principal principal = new Principal(authUser);
        response.setHeader("Authorization", tokenService.generateToken(principal));
        return principal;
    }

    public void approve(String token, ApproveUserRequest request, HttpServletResponse response) {
        Principal principal = tokenService.extractRequesterDetails(token);
        if (!principal.getRole().equals("ADMIN")) {
            response.setStatus(403);
            return;
        }

        userRepo.approveUser(request.getId());
    }

    public void delete(String token, DeleteRequest deleteRequest, HttpServletResponse response){
        Principal principal = tokenService.extractRequesterDetails(token);
        if (!principal.getRole().equals("ADMIN")) {
            response.setStatus(403);
            return;
        }
        userRepo.deleteUser(deleteRequest.getId());
    }
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

