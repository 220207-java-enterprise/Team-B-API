//package com.revature.foundation.services;
//
//import com.revature.foundation.dtos.requests.ReimbursementRequest;
//import com.revature.foundation.dtos.requests.StatusUpdateRequest;
//import com.revature.foundation.dtos.requests.TypeUpdateRequest;
//import com.revature.foundation.dtos.requests.UpdateReimbursementRequest;
//import com.revature.foundation.dtos.responses.ReimbursementResponse;
//import com.revature.foundation.models.Reimbursement;
//import com.revature.foundation.models.ReimbursementStatus;
//import com.revature.foundation.models.ReimbursementType;
//import com.revature.foundation.daos.ReimbursementDAO;
//import com.revature.foundation.util.exceptions.AuthenticationException;
//import com.revature.foundation.util.exceptions.InvalidRequestException;
//import com.revature.foundation.util.exceptions.ResourceConflictException;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//
//public class ReimbursementService {
//
//    private ReimbursementDAO reimbursementDAO;
//
//    public ReimbursementService(ReimbursementDAO reimbursementDAO){
//        this.reimbursementDAO = reimbursementDAO;
//    }
//
//    public List<ReimbursementResponse> getAllReimbursements(){
//        return reimbursementDAO.getAll().stream().map(ReimbursementResponse::new).collect(Collectors.toList());
//    }
//
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
//
//
//
//}
