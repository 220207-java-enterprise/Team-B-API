package com.revature.foundation.services;

import com.revature.foundation.dtos.requests.*;
import com.revature.foundation.dtos.responses.*;
import com.revature.foundation.models.AppUser;
import com.revature.foundation.models.Reimbursement;
import com.revature.foundation.models.ReimbursementStatus;
import com.revature.foundation.models.ReimbursementType;
import com.revature.foundation.repos.ReimbRepository;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.dnd.InvalidDnDOperationException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReimbursementService {

    private ReimbRepository reimbRepository;
    private TokenService tokenService;

    public ReimbursementService(ReimbRepository reimbRepository, TokenService tokenService) {
        this.reimbRepository = reimbRepository;
        this.tokenService = tokenService;
    }

    public List<ReimbursementResponse> getAllReimbursements(String token, HttpServletResponse response){

        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("FINANCE MANAGER"))){
            response.setStatus(403);
            return null;
        }

        return ((List<Reimbursement>) reimbRepository.findAll())
                              .stream()
                              .map(ReimbursementResponse::new)
                              .collect(Collectors.toList());
    }

    public List<ReimbursementResponse> getByType(TypeFilterRequest typeFilterRequest, String token, HttpServletResponse response){

        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("FINANCE MANAGER"))){
            response.setStatus(403);
            return null;
        }


        String typeName = typeFilterRequest.getTypeName();

        return reimbRepository.findByType(typeName)
                .stream()
                .map(ReimbursementResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReimbursementResponse> getByStatus(StatusFilterRequest statusFilterRequest, String token, HttpServletResponse response){

        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("FINANCE MANAGER"))){
            response.setStatus(403);
            return null;
        }

        String statusName = statusFilterRequest.getStatusName();

        return reimbRepository.findByStatus(statusName)
                .stream()
                .map(ReimbursementResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReimbursementResponse> getByAuthor(String token, HttpServletResponse response){

        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("EMPLOYEE"))){
            response.setStatus(403);
            return null;
        }
        String authorId = principal.getId();

        return reimbRepository.findByAuthor(authorId)
                .stream()
                .map(ReimbursementResponse::new)
                .collect(Collectors.toList());
    }

    public ResourceCreationResponse addReimbursement(ReimbursementRequest reimbursementRequest, String token, HttpServletResponse response){
        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("EMPLOYEE"))){
            response.setStatus(403);
            return null;
        }
        Reimbursement reimbursement = reimbursementRequest.extractReimbursement();
        reimbursement.setId(UUID.randomUUID().toString());
        reimbursement.setAuthor(new AppUser(principal.getId()));
        reimbursement.setResolver(new AppUser("8d5f0507-9804-450a-bfda-46209f16ba7c"));
        reimbursement.setStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9e","PENDING"));
        if (reimbursementRequest.getType() != null){
            reimbursement.setType(new ReimbursementType(reimbursementRequest.getType()));
        }
        else{
            reimbursement.setType(new ReimbursementType("OTHER"));
        }

        reimbursement.setSubmitted(new Timestamp(System.currentTimeMillis()));

        reimbRepository.save(reimbursement);

        return new ResourceCreationResponse(reimbursement.getId());
    }

    public StatusUpdateResponse updateStatus(StatusUpdateRequest statusUpdateRequest, String token, HttpServletResponse response){
        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("FINANCE MANAGER"))){
            response.setStatus(403);
            return null;
        }
        Reimbursement reimbursement = reimbRepository.findByReimbId(statusUpdateRequest.getReimb_id());
        if (statusUpdateRequest.getStatusName().equals(reimbursement.getReimbursementStatus().getStatusName())){
            throw new InvalidRequestException("The reimbursement is already "+reimbursement.getReimbursementStatus().getStatusName().toLowerCase());
        }
        if (statusUpdateRequest.getStatusName().equals("PENDING")){
            reimbursement.setStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9e","PENDING"));
        }
        else if(statusUpdateRequest.getStatusName().equals("APPROVED")){
            reimbursement.setStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9f","APPROVED"));
        }
        else if(statusUpdateRequest.getStatusName().equals("DENIED")){
            reimbursement.setStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9g","DENIED"));
        }
        reimbursement.setResolver(new AppUser(principal.getId()));
        reimbursement.setResolved(new Timestamp(System.currentTimeMillis()));
        reimbRepository.update_status(reimbursement.getReimbursementStatus().getId(),reimbursement.getResolved(),reimbursement.getId());
        return new StatusUpdateResponse(reimbursement);
    }


    public UpdateReimbursementResponse updateReimb(UpdateReimbursementRequest updateReimbursementRequest, String token, HttpServletResponse response){

        Principal principal = tokenService.extractRequesterDetails(token);

        if (!(principal.getRole().equals("EMPLOYEE"))){
            response.setStatus(403);
            return null;
        }

        Reimbursement reimbursement = reimbRepository.findByReimbId(updateReimbursementRequest.getId());

        if(reimbursement.getReimbursementStatus().getStatusName().equals("PENDING")) {
            if (updateReimbursementRequest.getAmount() > 0.0) {
                reimbursement.setAmount(updateReimbursementRequest.getAmount());
            }
            else{
                reimbursement.setAmount(reimbursement.getAmount());
            }
            if (updateReimbursementRequest.getDescription() != null) {
                reimbursement.setDescription(updateReimbursementRequest.getDescription());
            }
            else {
                reimbursement.setDescription(reimbursement.getDescription());
            }
        }
        else{
            throw new InvalidRequestException("The reimbursement is already "+reimbursement.getReimbursementStatus().getStatusName().toLowerCase());
        }
        reimbRepository.update(reimbursement.getDescription(), reimbursement.getAmount(), reimbursement.getId());
        return new UpdateReimbursementResponse(reimbursement);
    }



//    public List<ReimbursementResponse> getTypeReimbursements(String id){
//        return reimbursementDAO.getByType(id).stream().map(ReimbursementResponse::new).collect(Collectors.toList());
//    }
//    public List<ReimbursementResponse> getStatusReimbursements(String id){
//        return reimbursementDAO.getByStatus(id).stream().map(ReimbursementResponse::new).collect(Collectors.toList());
//    }
//    public List<ReimbursementResponse> getMyReimbursements(String id){
//        return reimbursementDAO.getByAuthor(id).stream().map(ReimbursementResponse::new).collect(Collectors.toList());
//    }
//
//    public Reimbursement register_reimbursement(ReimbursementRequest reimbursementRequest) throws IOException{
//        Reimbursement reimbursement = reimbursementRequest.extractReimbursement();
//
//        reimbursement.setId(UUID.randomUUID().toString());
//        reimbursement.setReimbursementStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9e","PENDING"));
//        reimbursement.setResolver_id("5ff00a27-3361-4615-97b9-9dd1a81f9171");
//        //reimbursement.setAuthor_id("b9616166-c655-47d0-b247-b51b25c6879f");
//        reimbursement.setReimbursementType(new ReimbursementType("7c3521f5-ff75-4e8a-9913-01d15ee4dc9d","OTHER"));
//
//        reimbursementDAO.save(reimbursement);
//        return reimbursement;
//    }
//
//    public Reimbursement update_type(TypeUpdateRequest typeUpdateRequest){
//        Reimbursement reimbursement = reimbursementDAO.getById(typeUpdateRequest.getReimb_id());
//        if (typeUpdateRequest.getType_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9d")){
//            reimbursement.setReimbursementType(new ReimbursementType("7c3521f5-ff75-4e8a-9913-01d15ee4dc9d","OTHER"));
//        }
//        else if(typeUpdateRequest.getType_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9c")){
//            reimbursement.setReimbursementType(new ReimbursementType("7c3521f5-ff75-4e8a-9913-01d15ee4dc9c","FOOD"));
//        }
//        else if(typeUpdateRequest.getType_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9b")){
//            reimbursement.setReimbursementType(new ReimbursementType("7c3521f5-ff75-4e8a-9913-01d15ee4dc9b","TRAVEL"));
//        }
//        else if(typeUpdateRequest.getType_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9a")){
//            reimbursement.setReimbursementType(new ReimbursementType("7c3521f5-ff75-4e8a-9913-01d15ee4dc9a","LODGING"));
//        }
//        reimbursementDAO.update_type(reimbursement);
//        return reimbursement;
//    }
//    public Reimbursement update_status(StatusUpdateRequest statusUpdateRequest){
//        Reimbursement reimbursement = reimbursementDAO.getById(statusUpdateRequest.getReimb_id());
//        if (statusUpdateRequest.getStatus_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9e")){
//            reimbursement.setReimbursementStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9e","PENDING"));
//        }
//        else if(statusUpdateRequest.getStatus_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9f")){
//            reimbursement.setReimbursementStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9f","APPROVED"));
//        }
//        else if(statusUpdateRequest.getStatus_id().equals("7c3521f5-ff75-4e8a-9913-01d15ee4dc9g")){
//            reimbursement.setReimbursementStatus(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9g","DENIED"));
//        }
//        reimbursementDAO.update_status(reimbursement);
//        return reimbursement;
//    }
//
//    public Reimbursement update(UpdateReimbursementRequest updateReimbursementRequest){
//        String id = updateReimbursementRequest.getId();
//        Reimbursement reimbursement = reimbursementDAO.getById(id);
//        if(reimbursement.getReimbursementStatus().getStatusName().equals("PENDING")) {
//            if (updateReimbursementRequest.getAmount() >= 0.0) {
//                reimbursement.setAmount(updateReimbursementRequest.getAmount());
//            }
//            if (updateReimbursementRequest.getDescription() != null) {
//                reimbursement.setDescription(updateReimbursementRequest.getDescription());
//            }
//        }
//            reimbursementDAO.update(reimbursement);
//            return reimbursement;
//
//    }
}
