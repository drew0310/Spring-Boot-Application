package com.andrew.delds.repo;

import com.andrew.delds.model.UserAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserAgentRepo extends JpaRepository<UserAgent, Integer> {
    UserAgent findByGuid(String guid);

    UserAgent findByOwnerid(long ownerid);

    List<UserAgent> findByStartingBitrate(long bitrate);
}
