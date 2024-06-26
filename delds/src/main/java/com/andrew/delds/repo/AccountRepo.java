package com.andrew.delds.repo;

import com.andrew.delds.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccountRepo extends JpaRepository<Account, Integer> {

    Account findByGuid(String guid);

    Account findByOwnerid(long ownerid);
}
