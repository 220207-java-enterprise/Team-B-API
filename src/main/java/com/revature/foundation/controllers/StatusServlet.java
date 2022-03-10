//package com.revature.foundation.controllers;
//
//import com.fasterxml.jackson.databind.DatabindException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.revature.foundation.dtos.requests.StatusUpdateRequest;
//import com.revature.foundation.dtos.requests.StatusFilterRequest;
//import com.revature.foundation.dtos.responses.Principal;
//import com.revature.foundation.dtos.responses.ReimbursementResponse;
//import com.revature.foundation.dtos.responses.StatusUpdateResponse;
//import com.revature.foundation.models.Reimbursement;
//import com.revature.foundation.services.ReimbursementService;
//import com.revature.foundation.util.exceptions.InvalidRequestException;
//import com.revature.foundation.util.exceptions.ResourceConflictException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//public class StatusServlet extends HttpServlet {
//    private final ReimbursementService reimbursementService;
//    private final ObjectMapper mapper;
//
//    public StatusServlet(ReimbursementService reimbursementService, ObjectMapper mapper) {
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
//        StatusFilterRequest statusFilterRequest = mapper.readValue(req.getInputStream(), StatusFilterRequest.class);
//        List<ReimbursementResponse> reimbursements = reimbursementService.getStatusReimbursements(statusFilterRequest.getStatus_id());
//        String payload = mapper.writeValueAsString(reimbursements);
//        resp.setContentType("application/json");
//        resp.getWriter().write(payload);
//
//
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
//
//        PrintWriter writer = resp.getWriter();
//
//        try {
//
//            StatusUpdateRequest statusUpdateRequest = mapper.readValue(req.getInputStream(), StatusUpdateRequest.class);
//            Reimbursement reimbursement = reimbursementService.update_status(statusUpdateRequest);
//            resp.setStatus(201); // CREATED
//            resp.setContentType("application/json");
//            String payload = mapper.writeValueAsString(new StatusUpdateResponse(reimbursement));
//            writer.write(payload);
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
//
//}
