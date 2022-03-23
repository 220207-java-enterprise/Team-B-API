package com.revature.ers.repos;

import com.revature.ers.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {

    @Query("from AppUser a where a.isActive = true")
    List<AppUser> findAllActive();

    @Query("from AppUser a where a.isActive = false")
    List<AppUser> findAllInactive();

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update AppUser a set a.isActive=true where a.id=?1")
    void approveUser(String id);

    @Transactional
    @Modifying
    @Query("update AppUser a set a.isActive=false where a.id=?1")
    void deleteUser(String id);

}
