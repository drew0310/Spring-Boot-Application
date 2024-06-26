package com.andrew.delds.service;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.Restriction;
import com.andrew.delds.repo.RestrictionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionService {

    private final Logger log = LoggerFactory.getLogger(RestrictionService.class);

    @Autowired
    private RestrictionRepo repo;

    public Restriction saveRestriction(Restriction restriction) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New Restriction object saved");
        return repo.save(restriction);
    }

    public List<Restriction> saveRestrictions(List<Restriction> restrictions) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New set of Restriction objects saved");
        return repo.saveAll(restrictions);
    }

    public List<Restriction> getRestrictions() throws ObjectNotFoundException {
        List<Restriction> restrictions = repo.findAll();
        if(!restrictions.isEmpty()) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched all Restriction objects");
            return restrictions;
        }
        else {
            throw new ObjectNotFoundException("No Restriction objects exist");
        }
    }

    public Restriction getRestrictionById(int id) throws ObjectNotFoundException {
        Restriction res = repo.findById(id).orElse(null);
        if(res != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched Restriction object with ID "+id);
            return res;
        }
        else {
            throw new ObjectNotFoundException("Restriction object with ID "+id+" not found");
        }
    }

    public Restriction getRestrictionByGuid(String guid) throws ObjectNotFoundException {
        Restriction res = repo.findByGuid(guid);
        if(res != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched Restriction object with GUID "+guid);
            return res;
        }
        else {
            throw new ObjectNotFoundException("Restriction object with GUID "+guid+" not found");
        }
    }

    public Restriction getRestrictionByOwnerid(long ownerid) throws ObjectNotFoundException {
        Restriction res = repo.findByOwnerid(ownerid);
        if(res != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched Restriction object with owner ID "+ownerid);
            return res;
        }
        else {
            throw new ObjectNotFoundException("Restriction object with owner ID "+ownerid+" not found");
        }
    }

    public String deleteRestriction(int id) throws ObjectNotFoundException {
        if(repo.findById(id).orElse(null) != null){
            repo.deleteById(id);
            log.debug("Status code "+ HttpStatus.OK.value()+" - Restriction object with ID "+id+" removed");
            return "Restriction object with ID "+id+" removed";
        }
        else {
            throw new ObjectNotFoundException("Restriction object with ID "+id+" not found");
        }
    }

    public Restriction updateRestriction(Restriction res) throws ObjectNotFoundException {
        Restriction existingRestriction = repo.findById(res.getId()).orElse(null);
        if(existingRestriction != null) {
            existingRestriction.setGuid(res.getGuid());
            existingRestriction.setOwnerid(res.getOwnerid());
            existingRestriction.setAvailabledate(res.getAvailabledate());
            existingRestriction.setDomainrestriction(res.getDomainrestriction());
            existingRestriction.setExpirationdate(res.getExpirationdate());
            existingRestriction.setGeolocationrestriction(res.getGeolocationrestriction());
            existingRestriction.setIpaddressrestriction(res.getIpaddressrestriction());
            existingRestriction.setMaxconlimit(res.getMaxconlimit());
            existingRestriction.setRequireauth(res.getRequireauth());
            existingRestriction.setUseragentrestriction(res.getUseragentrestriction());
            log.debug("Status code "+ HttpStatus.OK.value()+" - Restriction object with ID "+res.getId()+" updated");
            return repo.save(existingRestriction);
        }
        else {
            throw new ObjectNotFoundException("Restriction object with ID "+res.getId()+" not found");
        }

    }
}
