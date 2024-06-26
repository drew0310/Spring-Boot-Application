package com.andrew.delds.service;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.Account;
import com.andrew.delds.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);


    @Autowired
    private AccountRepo repo;

    public Account saveAccount(Account account) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New Account object saved");
        return repo.save(account);

    }

    public List<Account> saveAccounts(List<Account> accounts) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New set of Account objects saved");
        return repo.saveAll(accounts);
    }
    public List<Account> getAccounts() throws ObjectNotFoundException {
        List<Account> accounts = repo.findAll();
        if(!accounts.isEmpty()) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched all Account objects");
            return accounts;

        }
        else {
            throw new ObjectNotFoundException("No Account objects exist");
        }
    }

    public Account getAccountById(int id) throws ObjectNotFoundException {
        Account acc = repo.findById(id).orElse(null);
        if(acc != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched Account object with ID "+id);
            return acc;
        }
        else {
            throw new ObjectNotFoundException("Account object with ID "+id+" not found");
        }
    }

    public Account getAccountByGuid(String guid) throws ObjectNotFoundException {
        Account acc = repo.findByGuid(guid);
        if(acc != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched Account object with GUID "+guid);
            return acc;
        }
        else {
            throw new ObjectNotFoundException("Account object with GUID "+guid+" not found");
        }
    }

    public Account getAccountByOwnerid(long ownerid) throws ObjectNotFoundException {
        Account acc = repo.findByOwnerid(ownerid);
        if(acc != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched Account object with owner ID "+ownerid);
            return acc;
        }
        else {
            throw new ObjectNotFoundException("Account object with owner ID "+ownerid+" not found");
        }
    }

    public String deleteAccount(int id) throws ObjectNotFoundException {
        if(repo.findById(id).orElse(null) != null){
            repo.deleteById(id);
            log.debug("Status code "+ HttpStatus.OK.value()+" - Account object with ID "+id+" removed");
            return "Account object with ID "+id+" removed";
        }
        else {
            throw new ObjectNotFoundException("Account object with ID "+id+" not found");
        }

    }

    public Account updateAccount(Account account) throws ObjectNotFoundException {
        Account existingAccount = repo.findById(account.getId()).orElse(null);
        if(existingAccount != null) {
            existingAccount.setGuid(account.getGuid());
            existingAccount.setOwnerid(account.getOwnerid());
            existingAccount.setLocked(account.getLocked());
            existingAccount.setMaxconlimit(account.getMaxconlimit());
            log.debug("Status code "+ HttpStatus.OK.value()+" - Account object with ID "+account.getId()+" updated");
            return repo.save(existingAccount);
        }
        else {
            throw new ObjectNotFoundException("Account object with ID "+account.getId()+" not found");
        }

    }



}
