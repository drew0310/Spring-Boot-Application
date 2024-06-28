package com.andrew.delds.controller;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.*;
import com.andrew.delds.service.AccountService;
import com.andrew.delds.service.AdPolicyService;
import com.andrew.delds.service.RestrictionService;
import com.andrew.delds.service.UserAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;


@RestController
@RestControllerAdvice
@RequestMapping("/delivery/data")
public class MainController {


    @Autowired
    private AccountService accservice;

    @Autowired
    private AdPolicyService adservice;

    @Autowired
    private RestrictionService restriction;

    @Autowired
    private UserAgentService userservice;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    @GetMapping("/AccountSettings/accounts")
    public List<Account> findAllAccounts() throws ObjectNotFoundException {
        logger.debug("Received request to fetch all Account objects");
        return accservice.getAccounts();
    }

    @GetMapping("/AccountSettings/accountsById/{id}")
    public Account findAccountById(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to fetch Account object with ID "+id);
        return accservice.getAccountById(id);
    }

    @GetMapping("/AccountSettings/accountsByGuid/{guid}")
    public Account findAccountByGuid(@PathVariable String guid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch Account object with GUID "+guid);
        return accservice.getAccountByGuid(guid);
    }

    @GetMapping("/AccountSettings/accountsByOwnerid/{ownerid}")
    public Account findAccountByOwnerid(@PathVariable long ownerid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch Account object with owner ID "+ownerid);
        return accservice.getAccountByOwnerid(ownerid);
    }

    @PostMapping("/AccountSettings/addAccount")
    public Account addAccount(@RequestBody Account account) {
        logger.debug("Received request to add new Account object");
        return accservice.saveAccount(account);
    }

    @PostMapping("/AccountSettings/addAccounts")
    public List<Account> addAccounts(@RequestBody List<Account> accounts) {
        logger.debug("Received request to add new Account objects");
        return accservice.saveAccounts(accounts);
    }

    @DeleteMapping("/AccountSettings/deleteAccount/{id}")
    public ApiResponse<String> deleteAccount(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to delete Account object with ID "+id);
        String message = accservice.deleteAccount(id);
        String cid = MDC.get("CID");
        return new ApiResponse<>(cid, message);
    }

    @PutMapping("/AccountSettings/updateAccount")
    public Account updateAccount(@RequestBody Account account) throws ObjectNotFoundException {
        logger.debug("Received request to update Account object with ID "+account.getId());
        return accservice.updateAccount(account);
    }


    @GetMapping("/AdPolicy/adPolicies")
    public List<AdPolicy> findAllAdPolicies() throws ObjectNotFoundException {
        logger.debug("Received request to fetch all AdPolicy objects");
        return adservice.getAdPolicies();
    }

    @GetMapping("/AdPolicy/adPoliciesById/{id}")
    public AdPolicy findAdPolicyById(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to fetch AdPolicy object with ID "+id);
        return adservice.getAdPolicyById(id);
    }

    @GetMapping("/AdPolicy/adPoliciesByGuid/{guid}")
    public AdPolicy findAdPolicyByGuid(@PathVariable String guid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch AdPolicy object with GUID "+guid);
        return adservice.getAdPolicyByGuid(guid);
    }

    @GetMapping("/AdPolicy/adPoliciesByOwnerid/{ownerid}")
    public AdPolicy findAdPolicyByOwnerid(@PathVariable long ownerid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch AdPolicy object with owner ID "+ownerid);
        return adservice.getAdPolicyByOwnerid(ownerid);
    }

    @GetMapping("/AdPolicy/adPoliciesByAdType/{adtype}")
    public List<AdPolicy> findAdPolicyByAdType(@PathVariable String adtype) throws ObjectNotFoundException {
        logger.debug("Received request to fetch AdPolicy object with ad type "+adtype);
        return adservice.getAdPolicyByAdType(adtype);
    }

    @PostMapping("/AdPolicy/addAdPolicy")
    public AdPolicy addAdPolicy(@RequestBody AdPolicy adpolicy) {
        logger.debug("Received request to add new AdPolicy object");
        return adservice.saveAdPolicy(adpolicy);
    }

    @PostMapping("/AdPolicy/addAdPolicies")
    public List<AdPolicy> addAdPolicies(@RequestBody List<AdPolicy> adpolicies) {
        logger.debug("Received request to add new AdPolicy objects");
        return adservice.saveAdPolicies(adpolicies);
    }

    @DeleteMapping("/AdPolicy/deleteAdPolicy/{id}")
    public ApiResponse<String> deleteAdPolicy(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to delete AdPolicy object with ID "+id);
        String message = adservice.deleteAdPolicy(id);
        String cid = MDC.get("CID");
        return new ApiResponse<>(cid, message);
    }

    @PutMapping("/AdPolicy/updateAdPolicy")
    public AdPolicy updateAdPolicy(@RequestBody AdPolicy adpolicy) throws ObjectNotFoundException {
        logger.debug("Received request to update AdPolicy object with ID "+adpolicy.getId());
        return adservice.updateAdPolicy(adpolicy);
    }


    @GetMapping("/Restriction/restrictions")
    public List<Restriction> findAllRestrictions() throws ObjectNotFoundException {
        logger.debug("Received request to fetch all Restriction objects");
        return restriction.getRestrictions();
    }

    @GetMapping("/Restriction/restrictionsById/{id}")
    public Restriction findRestrictionById(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to fetch Restriction object with ID "+id);
        return restriction.getRestrictionById(id);
    }

    @GetMapping("/Restriction/restrictionsByGuid/{guid}")
    public Restriction findRestrictionByGuid(@PathVariable String guid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch Restriction object with GUID "+guid);
        return restriction.getRestrictionByGuid(guid);
    }

    @GetMapping("/Restriction/restrictionsByOwnerid/{ownerid}")
    public Restriction findRestrictionByOwnerid(@PathVariable long ownerid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch Restriction object with owner ID "+ownerid);
        return restriction.getRestrictionByOwnerid(ownerid);
    }

    @PostMapping("/Restriction/addRestriction")
    public Restriction addRestriction(@RequestBody Restriction res) {
        logger.debug("Received request to add new Restriction object");
        return restriction.saveRestriction(res);
    }

    @PostMapping("/Restriction/addRestrictions")
    public List<Restriction> addRestrictions(@RequestBody List<Restriction> res) {
        logger.debug("Received request to add new Restriction objects");
        return restriction.saveRestrictions(res);
    }

    @DeleteMapping("/Restriction/deleteRestriction/{id}")
    public ApiResponse<String> deleteRestriction(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to delete Restriction object with ID "+id);
        String message = restriction.deleteRestriction(id);
        String cid = MDC.get("CID");
        return new ApiResponse<>(cid, message);
    }

    @PutMapping("/Restriction/updateRestriction")
    public Restriction updateRestriction(@RequestBody Restriction res) throws ObjectNotFoundException {
        logger.debug("Received request to update Restriction object with ID "+res.getId());
        return restriction.updateRestriction(res);
    }


    @GetMapping("/UserAgent/userAgents")
    public List<UserAgent> findAllUserAgents() throws ObjectNotFoundException {
        logger.debug("Received request to fetch all UserAgent objects");
        return userservice.getUserAgents();
    }

    @GetMapping("/UserAgent/userAgentsById/{id}")
    public UserAgent findUserAgentById(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to fetch UserAgent object with ID "+id);
        return userservice.getUserAgentById(id);
    }


    @GetMapping("/UserAgent/userAgentsByGuid/{guid}")
    public UserAgent findUserAgentByGuid(@PathVariable String guid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch UserAgent object with GUID "+guid);
        return userservice.getUserAgentByGuid(guid);
    }

    @GetMapping("/UserAgent/userAgentsByOwnerid/{ownerid}")
    public UserAgent findUserAgentByOwnerid(@PathVariable long ownerid) throws ObjectNotFoundException {
        logger.debug("Received request to fetch UserAgent object with owner ID "+ownerid);
        return userservice.getUserAgentByOwnerid(ownerid);
    }

    @GetMapping("/UserAgent/userAgentsByStartingBitrate/{bitrate}")
    public List<UserAgent> findUserAgentByStartingBitrate(@PathVariable long bitrate) throws ObjectNotFoundException {
        logger.debug("Received request to fetch UserAgent object with starting bitrate "+bitrate);
        return userservice.getUserAgentByStartingBitrate(bitrate);
    }

    @PostMapping("/UserAgent/addUserAgent")
    public UserAgent addUserAgent(@RequestBody UserAgent useragent) {
        logger.debug("Received request to add new UserAgent object");
        return userservice.saveUserAgent(useragent);
    }

    @PostMapping("/UserAgent/addUserAgents")
    public List<UserAgent> addUserAgents(@RequestBody List<UserAgent> useragents) {
        logger.debug("Received request to add new UserAgent objects");
        return userservice.saveUserAgents(useragents);
    }

    @DeleteMapping("/UserAgent/deleteUserAgent/{id}")
    public ApiResponse<String> deleteUserAgent(@PathVariable int id) throws ObjectNotFoundException {
        logger.debug("Received request to delete UserAgent object with ID "+id);
        String message = userservice.deleteUserAgent(id);
        String cid = MDC.get("CID");
        return new ApiResponse<>(cid, message);
    }

    @PutMapping("/UserAgent/updateUserAgent")
    public UserAgent updateUserAgent(@RequestBody UserAgent useragent) throws ObjectNotFoundException {
        logger.debug("Received request to update UserAgent object with ID "+useragent
                .getId());
        return userservice.updateUserAgent(useragent);
    }


}

