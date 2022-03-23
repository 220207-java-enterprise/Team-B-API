package com.revature.ers.controllers;

import com.revature.ers.dtos.requests.PrismRegisterRequest;
import com.revature.ers.services.PrismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/prism")
public class PrismController {

   private final PrismService prismService;

    @Autowired
    public PrismController(PrismService prismService) {
        this.prismService = prismService;
    }

    @PostMapping
    public void register(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return;
        }

        prismService.register(token, response);
    }
}
