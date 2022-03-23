package com.revature.ers.services;

import com.revature.ers.dtos.requests.PrismRegisterRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.PrismInfo;
import com.revature.ers.repos.PrismRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class PrismService {

    private final PrismRepository prismRepo;
    private final TokenService tokenService;

    private final String prismRoot = "http://localhost:5000/prism";
    private final RestTemplate prismClient = new RestTemplate();
    private final PrismRegisterRequest prismRequest;

    @Autowired
    public PrismService(PrismRepository prismRepo, TokenService tokenService, PrismRegisterRequest prismRequest) {
        this.prismRepo = prismRepo;
        this.tokenService = tokenService;
        this.prismRequest = prismRequest;
    }

    public void register(String token, HttpServletResponse response) {
        Principal principal = tokenService.extractRequesterDetails(token);
        if (!principal.getRole().equals("ADMIN")) {
            response.setStatus(403);
            return;
        }

        List<PrismInfo> currentInfo = this.getInfo();
        if (currentInfo.size() > 0) {
            response.setStatus(400);
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PrismRegisterRequest> request = new HttpEntity<>(prismRequest, headers);

        PrismInfo prismInfo = prismClient.postForObject(prismRoot + "/organizations", request, PrismInfo.class);
        if (prismInfo == null) {
            response.setStatus(500);
            return;
        }

        prismRepo.save(prismInfo);
    }

    private List<PrismInfo> getInfo() {
        return (List<PrismInfo>) prismRepo.findAll();
    }
}