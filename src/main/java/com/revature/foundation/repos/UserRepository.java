package com.revature.foundation.repos;

import com.revature.foundation.models.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<AppUser, String> {

    @Query("from AppUser a where a.isActive = true")
    List<AppUser> findAllActive();



//    //Using native SQL query
//    @Query(
//        value = "SELECT * from ERS_Users where User_name = ?1",
//        nativeQuery = true
//    )
//    List<AppUser> findByAppUserByUsername(String username);
//
//    //Using JPQL annotation
//    @Query("from AppUser a where a.Email = ?1")
//    List<AppUser> findByUserByEmail(AppUser email);
//
//    //Using JPQL annotation
//    @Query("from AppUser a where a.username = ?1 AND a.password= ?2")
//    List<AppUser> findByAppUserByUsernameAndPassword(AppUser username, AppUser password);

}