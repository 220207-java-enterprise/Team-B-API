package com.revature.foundation.repos;

import com.revature.foundation.models.Reimbursement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbRepository extends CrudRepository<Reimbursement, String> {

    List<Reimbursement> findAll();

    @Query("from Reimbursement r where r.type.typeName = ?1")
    List<Reimbursement> findByType(String typeName);

    @Query("from Reimbursement r where r.status.statusName = ?1")
    List<Reimbursement> findByStatus(String statusName);

    @Query("from Reimbursement r where r.author_id = ?1")
    List<Reimbursement> findByAuthor(String author_id);

}
