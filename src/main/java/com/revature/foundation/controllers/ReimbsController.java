package com.revature.foundation.controllers;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.dtos.requests.*;
import com.revature.foundation.dtos.responses.*;
import com.revature.foundation.models.Reimbursement;
import com.revature.foundation.services.ReimbursementService;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import com.revature.foundation.util.exceptions.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/reimbs")
public class ReimbsController {
    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbsController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    @GetMapping
    public List<ReimbursementResponse> getAllReimbs() {
        return reimbursementService.getAllReimbursements();
    }

    @GetMapping(value = "/types/{type}", produces = "application/json")
    public List<ReimbursementResponse> getByType(@PathVariable String type) {
        TypeFilterRequest typeFilterRequest = new TypeFilterRequest(type);
        return reimbursementService.getByType(typeFilterRequest);
    }

    @GetMapping(value = "/statuses/{status}", produces = "application/json")
    public List<ReimbursementResponse> getByStatus(@PathVariable String status) {
        StatusFilterRequest statusFilterRequest = new StatusFilterRequest(status);
        return reimbursementService.getByStatus(statusFilterRequest);
    }

    @GetMapping(value = "/author/{authorId}", produces = "application/json")
    public List<ReimbursementResponse> getByAuthor(@PathVariable String authorId) {
        ViewReimbursementRequest viewReimbursementRequest = new ViewReimbursementRequest(authorId);
        return reimbursementService.getByAuthor(viewReimbursementRequest);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResourceCreationResponse addReimb(@RequestBody ReimbursementRequest reimbursementRequest){
        return reimbursementService.addReimbursement(reimbursementRequest);
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/status")
    public StatusUpdateResponse updateStatus(@RequestBody StatusUpdateRequest statusUpdateRequest){
        return reimbursementService.updateStatus(statusUpdateRequest);
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/type")
    public TypeUpdateResponse updateType(@RequestBody TypeUpdateRequest typeUpdateRequest){
        return reimbursementService.updateType(typeUpdateRequest);
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/employee")
    public UpdateReimbursementResponse updateReimb(@RequestBody UpdateReimbursementRequest updateReimbursementRequest){
        return reimbursementService.updateReimb(updateReimbursementRequest);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> handleInvalidRequests(InvalidRequestException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 400);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now());
        return responseBody;
    }

}
//public class ReimbursementServlet extends HttpServlet {
//
//    private final ReimbursementService reimbursementService;
//    private final ObjectMapper mapper;
//
//    public ReimbursementServlet(ReimbursementService reimbursementService, ObjectMapper mapper) {
//        this.reimbursementService = reimbursementService;
//        this.mapper = mapper;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String[] reqFrags = req.getRequestURI().split("/");
//
//        // TODO implement some security logic here to protect sensitive operations
//
//        // get users (all, by id, by w/e)
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            resp.setStatus(401);
//            return;
//        }
//
//        Principal requester = (Principal) session.getAttribute("authUser");
//
//        if (!requester.getRole().equals("FINANCE MANAGER")) {
//            resp.setStatus(403);
//            return;// FORBIDDEN
//        }
//
//        List<ReimbursementResponse> reimbursements = reimbursementService.getAllReimbursements();
//        String payload = mapper.writeValueAsString(reimbursements);
//        resp.setContentType("application/json");
//        resp.getWriter().write(payload);
//
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String[] reqFrags = req.getRequestURI().split("/");
//
//        // TODO implement some security logic here to protect sensitive operations
//
//        // get users (all, by id, by w/e)
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            resp.setStatus(401);
//            return;
//        }
//
//        Principal requester = (Principal) session.getAttribute("authUser");
//
//        if (!requester.getRole().equals("EMPLOYEE")) {
//            resp.setStatus(403);
//            return;// FORBIDDEN
//        }
//
//        PrintWriter respWriter = resp.getWriter();
//
//        try {
//
//            ReimbursementRequest reimbursementRequest = mapper.readValue(req.getInputStream(), ReimbursementRequest.class);
//            Reimbursement reimbursement = reimbursementService.register_reimbursement(reimbursementRequest);
//            resp.setStatus(201); // CREATED
//            resp.setContentType("application/json");
//            String payload = mapper.writeValueAsString(new ResourceCreationResponse(reimbursement.getId()));
//            respWriter.write(payload);
//
//        } catch (InvalidRequestException | DatabindException e) {
//            e.printStackTrace();
//            resp.setStatus(400); // BAD REQUEST
//        } catch (ResourceConflictException e) {
//            resp.setStatus(409); // CONFLICT
//        } catch (Exception e) {
//            e.printStackTrace(); // include for debugging purposes; ideally log it to a file
//            resp.setStatus(500);
//        }
//    }
//    @Override
//    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String[] reqFrags = req.getRequestURI().split("/");
//
//        // TODO implement some security logic here to protect sensitive operations
//
//        // get users (all, by id, by w/e)
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            resp.setStatus(401);
//            return;
//        }
//
//        Principal requester = (Principal) session.getAttribute("authUser");
//
//        if (!requester.getRole().equals("EMPLOYEE")) {
//            resp.setStatus(403);
//            return;// FORBIDDEN
//        }
//
//        PrintWriter respWriter = resp.getWriter();
//
//        try {
//
//            UpdateReimbursementRequest reimbursementRequest = mapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class);
//            Reimbursement reimbursement = reimbursementService.update(reimbursementRequest);
//            resp.setStatus(202); // UPDATED
//            resp.setContentType("application/json");
//            String payload = mapper.writeValueAsString(new ResourceCreationResponse(reimbursement.getId()));
//            respWriter.write(payload);
//
//        } catch (InvalidRequestException | DatabindException e) {
//            e.printStackTrace();
//            resp.setStatus(400); // BAD REQUEST
//        } catch (ResourceConflictException e) {
//            resp.setStatus(409); // CONFLICT
//        } catch (Exception e) {
//            e.printStackTrace(); // include for debugging purposes; ideally log it to a file
//            resp.setStatus(500);
//        }
//
//    }
