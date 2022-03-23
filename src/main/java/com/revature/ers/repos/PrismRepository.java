package com.revature.ers.repos;

import com.revature.ers.models.PrismInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrismRepository extends CrudRepository<PrismInfo, String> {

}
