package com.andrew.delds.service;


import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.AdPolicy;
import com.andrew.delds.repo.AdPolicyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdPolicyService {

    private final Logger log = LoggerFactory.getLogger(AdPolicyService.class);

    @Autowired
    private AdPolicyRepo repo;

    public AdPolicy saveAdPolicy(AdPolicy adpolicy) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New AdPolicy object saved");
        return repo.save(adpolicy);
    }

    public List<AdPolicy> saveAdPolicies(List<AdPolicy> adpolicies) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New set of AdPolicy objects saved");
        return repo.saveAll(adpolicies);
    }

    public List<AdPolicy> getAdPolicies() throws ObjectNotFoundException {
        List<AdPolicy> policies = repo.findAll();
        if(!policies.isEmpty()) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched all AdPolicy objects");
            return policies;
        }
        else {
            throw new ObjectNotFoundException("No AdPolicy objects exist");
        }
    }

    public AdPolicy getAdPolicyById(int id) throws ObjectNotFoundException {
        AdPolicy policy = repo.findById(id).orElse(null);
        if(policy != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched AdPolicy object with ID "+id);
            return policy;
        }
        else {
            throw new ObjectNotFoundException("AdPolicy object with ID "+id+" not found");
        }
    }

    public AdPolicy getAdPolicyByGuid(String guid) throws ObjectNotFoundException {
        AdPolicy policy = repo.findByGuid(guid);
        if(policy != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched AdPolicy object with GUID "+guid);
            return policy;
        }
        else {
            throw new ObjectNotFoundException("AdPolicy object with GUID "+guid+" not found");
        }
    }

    public AdPolicy getAdPolicyByOwnerid(long ownerid) throws ObjectNotFoundException {
        AdPolicy policy = repo.findByOwnerid(ownerid);
        if(policy != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched AdPolicy object with owner ID "+ownerid);
            return policy;
        }
        else {
            throw new ObjectNotFoundException("AdPolicy object with owner ID "+ownerid+" not found");
        }
    }

    public List<AdPolicy> getAdPolicyByAdType(String adtype) throws ObjectNotFoundException {
        List<AdPolicy> policies = repo.findByAdtype(adtype);
        if(!policies.isEmpty()) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched AdPolicy objects with "+adtype+" ad type");
            return policies;
        }
        else {
            throw new ObjectNotFoundException("AdPolicy objects with "+adtype+" ad type not found");
        }
    }

    public String deleteAdPolicy(int id) throws ObjectNotFoundException {
        if(repo.findById(id).orElse(null) != null){
            repo.deleteById(id);
            log.debug("Status code "+ HttpStatus.OK.value()+" - AdPolicy object with ID "+id+" removed");
            return "AdPolicy object with ID "+id+" removed";
        }
        else {
            throw new ObjectNotFoundException("AdPolicy object with ID "+id+" not found");
        }
    }

    public AdPolicy updateAdPolicy(AdPolicy adpolicy) throws ObjectNotFoundException {
        AdPolicy existingAdPolicy = repo.findById(adpolicy.getId()).orElse(null);
        if(existingAdPolicy != null) {
            existingAdPolicy.setGuid(adpolicy.getGuid());
            existingAdPolicy.setOwnerid(adpolicy.getOwnerid());
            existingAdPolicy.setAdformat(adpolicy.getAdformat());
            existingAdPolicy.setAdtitle(adpolicy.getAdtitle());
            existingAdPolicy.setAdtype(adpolicy.getAdtype());
            existingAdPolicy.setCanskip(adpolicy.getCanskip());
            existingAdPolicy.setCount(adpolicy.getCount());
            existingAdPolicy.setDuration(adpolicy.getDuration());
            log.debug("Status code "+ HttpStatus.OK.value()+" - AdPolicy object with ID "+adpolicy.getId()+" updated");
            return repo.save(existingAdPolicy);
        }
        else {
            throw new ObjectNotFoundException("AdPolicy object with ID "+adpolicy.getId()+" not found");
        }

    }
}
