package com.andrew.delds.repo;

import com.andrew.delds.model.AdPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AdPolicyRepo extends JpaRepository<AdPolicy, Integer> {
    AdPolicy findByGuid(String guid);

    AdPolicy findByOwnerid(long ownerid);

    List<AdPolicy> findByAdtype(String adtype);

}
