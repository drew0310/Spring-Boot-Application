package com.andrew.delds.service;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.UserAgent;
import com.andrew.delds.repo.UserAgentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAgentService {

    private final Logger log = LoggerFactory.getLogger(UserAgentService.class);

    @Autowired
    private UserAgentRepo repo;

    public UserAgent saveUserAgent(UserAgent useragent) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New UserAgent object saved");
        return repo.save(useragent);
    }

    public List<UserAgent> saveUserAgents(List<UserAgent> useragents) {
        log.debug("Status code "+ HttpStatus.OK.value()+" - New set of UserAgent objects saved");
        return repo.saveAll(useragents);
    }

    public List<UserAgent> getUserAgents() throws ObjectNotFoundException {
        List<UserAgent> agents = repo.findAll();
        if(!agents.isEmpty()) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched all UserAgent objects");
            return agents;
        }
        else {
            throw new ObjectNotFoundException("No UserAgent objects exist");
        }
    }

    public UserAgent getUserAgentById(int id) throws ObjectNotFoundException {
        UserAgent agent = repo.findById(id).orElse(null);
        if(agent != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched UserAgent object with ID "+id);
            return agent;
        }
        else {
            throw new ObjectNotFoundException("UserAgent object with ID "+id+" not found");
        }
    }

    public UserAgent getUserAgentByGuid(String guid) throws ObjectNotFoundException {
        UserAgent agent = repo.findByGuid(guid);
        if(agent != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched UserAgent object with GUID "+guid);
            return agent;
        }
        else {
            throw new ObjectNotFoundException("UserAgent object with GUID "+guid+" not found");
        }
    }

    public UserAgent getUserAgentByOwnerid(long ownerid) throws ObjectNotFoundException {
        UserAgent agent = repo.findByOwnerid(ownerid);
        if(agent != null) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched UserAgent object with owner ID "+ownerid);
            return agent;
        }
        else {
            throw new ObjectNotFoundException("UserAgent object with owner ID "+ownerid+" not found");
        }
    }

    public List<UserAgent> getUserAgentByStartingBitrate(long bitrate) throws ObjectNotFoundException {
        List<UserAgent> agents = repo.findByStartingBitrate(bitrate);
        if(!agents.isEmpty()) {
            log.debug("Status code "+ HttpStatus.OK.value()+" - Fetched UserAgent objects with starting bitrate of "+bitrate);
            return agents;
        }
        else {
            throw new ObjectNotFoundException("UserAgent objects with starting bitrate of "+bitrate+" not found");
        }
    }

    public String deleteUserAgent(int id) throws ObjectNotFoundException {
        if(repo.findById(id).orElse(null) != null){
            repo.deleteById(id);
            log.debug("Status code "+ HttpStatus.OK.value()+" - UserAgent object with ID "+id+" removed");
            return "UserAgent object with ID "+id+" removed!";
        }
        else {
            throw new ObjectNotFoundException("UserAgent object with ID "+id+" not found");
        }
    }

    public UserAgent updateUserAgent(UserAgent useragent) throws ObjectNotFoundException {
        UserAgent existingUserAgent = repo.findById(useragent.getId()).orElse(null);
        if(existingUserAgent != null) {
            existingUserAgent.setGuid(useragent.getGuid());
            existingUserAgent.setOwnerid(useragent.getOwnerid());
            existingUserAgent.setFormat(useragent.getFormat());
            existingUserAgent.setMaxBitRate(useragent.getMaxBitRate());
            existingUserAgent.setMaxHeight(useragent.getMaxHeight());
            existingUserAgent.setMaxWidth(useragent.getMaxWidth());
            existingUserAgent.setMinBitRate(useragent.getMinBitRate());
            existingUserAgent.setMinHeight(useragent.getMinHeight());
            existingUserAgent.setMinWidth(useragent.getMinWidth());
            existingUserAgent.setStartingBitrate(useragent.getStartingBitrate());
            log.debug("Status code "+ HttpStatus.OK.value()+" - UserAgent object with ID "+useragent.getId()+" updated");
            return repo.save(existingUserAgent);
        }
        else {
            throw new ObjectNotFoundException("UserAgent object with ID "+useragent.getId()+" not found");
        }

    }

}
