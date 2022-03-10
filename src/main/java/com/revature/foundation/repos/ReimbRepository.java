package com.revature.foundation.repos;

import com.revature.foundation.models.Reimbursement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbRepository extends CrudRepository<Reimbursement, String> {

    List<Reimbursement> findAll();
}
