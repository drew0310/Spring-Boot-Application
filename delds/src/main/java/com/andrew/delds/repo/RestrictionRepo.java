package com.andrew.delds.repo;

import com.andrew.delds.model.AdPolicy;
import com.andrew.delds.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestrictionRepo extends JpaRepository<Restriction, Integer> {

    Restriction findByGuid(String guid);

    Restriction findByOwnerid(long ownerid);
}
