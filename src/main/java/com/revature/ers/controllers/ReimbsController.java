package com.revature.ers.controllers;

import com.revature.ers.dtos.requests.*;
import com.revature.ers.dtos.responses.ReimbursementResponse;
import com.revature.ers.dtos.responses.ResourceCreationResponse;
import com.revature.ers.dtos.responses.StatusUpdateResponse;
import com.revature.ers.dtos.responses.UpdateReimbursementResponse;
import com.revature.ers.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reimbs")
public class ReimbsController {
    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbsController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    @GetMapping
    public List<ReimbursementResponse> getAllReimbs(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }
        return reimbursementService.getAllReimbursements(token,response);
    }

    @GetMapping(value = "/types/{type}", produces = "application/json")
    public List<ReimbursementResponse> getByType(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }
        TypeFilterRequest typeFilterRequest = new TypeFilterRequest(type);
        return reimbursementService.getByType(typeFilterRequest,token,response);
    }

    @GetMapping(value = "/statuses/{status}", produces = "application/json")
    public List<ReimbursementResponse> getByStatus(@PathVariable String status, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }
        StatusFilterRequest statusFilterRequest = new StatusFilterRequest(status);
        return reimbursementService.getByStatus(statusFilterRequest, token, response);
    }

    @GetMapping(value = "/author", produces = "application/json")
    public List<ReimbursementResponse> getByAuthor(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }

        return reimbursementService.getByAuthor(token, response);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResourceCreationResponse addReimb(@RequestBody ReimbursementRequest reimbursementRequest, HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }
        return reimbursementService.addReimbursement(reimbursementRequest, token, response);
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/status")
    public StatusUpdateResponse updateStatus(@RequestBody StatusUpdateRequest statusUpdateRequest, HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }
        return reimbursementService.updateStatus(statusUpdateRequest, token, response);
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/employee")
    public UpdateReimbursementResponse updateReimb(@RequestBody UpdateReimbursementRequest updateReimbursementRequest, HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return null;
        }
        return reimbursementService.updateReimb(updateReimbursementRequest, token, response);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> handleInvalidRequests(RuntimeException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        //responseBody.put("status", 400);
        responseBody.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());
        return responseBody;
    }

}
