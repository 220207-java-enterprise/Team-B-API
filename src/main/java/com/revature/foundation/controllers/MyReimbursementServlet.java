//package com.revature.foundation.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.revature.foundation.dtos.requests.ViewReimbursementRequest;
//import com.revature.foundation.dtos.responses.Principal;
//import com.revature.foundation.dtos.responses.ReimbursementResponse;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//public class MyReimbursementServlet extends HttpServlet {
//    private final ReimbursementService reimbursementService;
//    private final ObjectMapper mapper;
//
//    public MyReimbursementServlet(ReimbursementService reimbursementService, ObjectMapper mapper) {
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
//        if (!requester.getRole().equals("EMPLOYEE")) {
//            resp.setStatus(403);
//            return;// FORBIDDEN
//        }
//
//        ViewReimbursementRequest viewReimbursementRequest = mapper.readValue(req.getInputStream(), ViewReimbursementRequest.class);
//        List<ReimbursementResponse> reimbursements = reimbursementService.getMyReimbursements(viewReimbursementRequest.getAuthor_id());
//        String payload = mapper.writeValueAsString(reimbursements);
//        resp.setContentType("application/json");
//        resp.getWriter().write(payload);
//
//
//    }
//}
