package com.revature.foundation.repos;

import com.revature.foundation.models.Reimbursement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    @Transactional
    @Modifying
    @Query(value = "UPDATE ers_reimbursements " + "SET status_id = ?1, "+ "resolved = ?2 "+ "WHERE reimb_id = ?3", nativeQuery = true)
    void update_status(String status_id, Timestamp resolved, String reimb_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ers_reimbursements " + "SET type_id = ?1, "+ "resolved = ?2 "+ "WHERE reimb_id = ?3", nativeQuery = true)
    void update_type(String type_id, Timestamp resolved, String reimb_id);

    @Query("from Reimbursement r where r.id = ?1")
    Reimbursement findByReimbId(String id);
}
