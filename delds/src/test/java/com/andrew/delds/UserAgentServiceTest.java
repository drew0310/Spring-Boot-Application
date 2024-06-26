package com.andrew.delds;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.UserAgent;
import com.andrew.delds.repo.UserAgentRepo;
import com.andrew.delds.service.UserAgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserAgentServiceTest {

    @InjectMocks
    private UserAgentService userAgentService;

    @Mock
    private UserAgentRepo userAgentRepo;

    private UserAgent userAgent;

    @BeforeEach
    public void setUp() {
        userAgent = new UserAgent();
        userAgent.setId(7);
        userAgent.setGuid("sample GUID");
        userAgent.setOwnerid(7);
        userAgent.setFormat("sample format");
        userAgent.setMinBitRate(0);
        userAgent.setMinWidth(0);
        userAgent.setMinHeight(0);
        userAgent.setMaxBitRate(0);
        userAgent.setMaxWidth(0);
        userAgent.setMaxHeight(0);
        userAgent.setStartingBitrate(0);
    }

    @Test
    public void testCreateUserAgent() {
        when(userAgentRepo.save(any(UserAgent.class))).thenReturn(userAgent);

        UserAgent createdUserAgent = userAgentService.saveUserAgent(userAgent);

        assertNotNull(createdUserAgent, "The saved user agent should not be null");
        assertEquals(userAgent, createdUserAgent, "The saved user agent should match the original input user agent");
        verify(userAgentRepo, times(1)).save(userAgent);
    }

    @Test
    public void testCreateUserAgents() {
        when(userAgentRepo.saveAll(anyList())).thenReturn(Collections.singletonList(userAgent));

        List<UserAgent> createdUserAgent = userAgentService.saveUserAgents(Collections.singletonList(userAgent));

        assertNotNull(createdUserAgent, "The saved user agents list should not be null");
        assertEquals(1, createdUserAgent.size(), "The size of the saved user agents list should be 1");
        assertEquals(userAgent, createdUserAgent.getFirst(), "The user agent in the saved user agents list should match the original input user agent");

        verify(userAgentRepo, times(1)).saveAll(Collections.singletonList(userAgent));
    }

    @Test
    public void testGetUserAgentsNotEmpty() throws ObjectNotFoundException {

        when(userAgentRepo.findAll()).thenReturn(Collections.singletonList(userAgent));

        List<UserAgent> fetchedUserAgents = userAgentService.getUserAgents();

        assertNotNull(fetchedUserAgents, "The fetched user agents list should not be null");
        assertEquals(1, fetchedUserAgents.size(), "The size of the fetched user agents list should be 1");
        assertEquals(userAgent, fetchedUserAgents.getFirst(), "The user agent in the fetched user agents list should match the original input user agent");
        verify(userAgentRepo, times(1)).findAll();

    }

    @Test
    public void testGetUserAgentsEmpty() {
        when(userAgentRepo.findAll()).thenReturn(Collections.emptyList());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.getUserAgents();
        });


    }

    @Test
    public void testGetUserAgentByIdNotEmpty() throws ObjectNotFoundException {
        when(userAgentRepo.findById(7)).thenReturn(Optional.of(userAgent));

        UserAgent fetchedUserAgent = userAgentService.getUserAgentById(7);
        assertNotNull(fetchedUserAgent, "The fetched user agent should not be null");
        assertEquals(userAgent, fetchedUserAgent, "The fetched user agent must match the original input user agent");
        verify(userAgentRepo, times(1)).findById(7);

    }

    @Test
    public void testGetUserAgentByIdEmpty() {
        when(userAgentRepo.findById(18)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.getUserAgentById(18);
        });
    }

    @Test
    public void testGetUserAgentByGuidNotEmpty() throws ObjectNotFoundException {
        when(userAgentRepo.findByGuid("sample GUID")).thenReturn(userAgent);

        UserAgent fetchedUserAgent = userAgentService.getUserAgentByGuid("sample GUID");
        assertNotNull(fetchedUserAgent, "The fetched user agent should not be null");
        assertEquals(userAgent, fetchedUserAgent, "The fetched user agent must match the original input user agent");
        verify(userAgentRepo, times(1)).findByGuid("sample GUID");

    }

    @Test
    public void testGetUserAgentByGuidEmpty() {
        when(userAgentRepo.findByGuid("sample GUID")).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.getUserAgentByGuid("sample GUID");
        });
    }

    @Test
    public void testGetUserAgentByOwneridNotEmpty() throws ObjectNotFoundException {
        when(userAgentRepo.findByOwnerid(7)).thenReturn(userAgent);

        UserAgent fetchedUserAgent = userAgentService.getUserAgentByOwnerid(7);
        assertNotNull(fetchedUserAgent, "The fetched user agent should not be null");
        assertEquals(userAgent, fetchedUserAgent, "The fetched user agent must match the original input user agent");
        verify(userAgentRepo, times(1)).findByOwnerid(7);

    }

    @Test
    public void testGetUserAgentByOwneridEmpty() {
        when(userAgentRepo.findByOwnerid(7)).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.getUserAgentByOwnerid(7);
        });
    }

    @Test
    public void testGetUserAgentByStartingBitrateNotEmpty() throws ObjectNotFoundException {

        when(userAgentRepo.findByStartingBitrate(0)).thenReturn(Collections.singletonList(userAgent));

        List<UserAgent> fetchedUserAgents = userAgentService.getUserAgentByStartingBitrate(0);

        assertNotNull(fetchedUserAgents, "The fetched user agents list should not be null");
        assertEquals(1, fetchedUserAgents.size(), "The size of the fetched user agents list should be 1");
        assertEquals(userAgent, fetchedUserAgents.getFirst(), "The user agent in the fetched user agents list should match the original input user agent");
        verify(userAgentRepo, times(1)).findByStartingBitrate(0);


    }

    @Test
    public void testGetUserAgentsByStartingBitrateEmpty() {
        when(userAgentRepo.findByStartingBitrate(0)).thenReturn(Collections.emptyList());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.getUserAgentByStartingBitrate(0);
        });
    }

    @Test
    public void testDeleteUserAgentNotEmpty() throws ObjectNotFoundException {
        when(userAgentRepo.findById(7)).thenReturn(Optional.of(userAgent));
        doNothing().when(userAgentRepo).deleteById(7);

        userAgentService.deleteUserAgent(7);
        verify(userAgentRepo, times(1)).findById(7);
        verify(userAgentRepo, times(1)).deleteById(7);
    }

    @Test
    public void testDeleteUserAgentEmpty() {
        when(userAgentRepo.findById(7)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.getUserAgentById(7);
        });
    }

    @Test
    public void testUpdateUserAgentNotEmpty() throws ObjectNotFoundException {
        UserAgent updatedUserAgent = new UserAgent();
        updatedUserAgent.setId(7);
        updatedUserAgent.setGuid("sample GUID 2");
        updatedUserAgent.setOwnerid(7);
        updatedUserAgent.setFormat("sample format 2");
        updatedUserAgent.setMinBitRate(100);
        updatedUserAgent.setMinWidth(100);
        updatedUserAgent.setMinHeight(100);
        updatedUserAgent.setMaxBitRate(100);
        updatedUserAgent.setMaxWidth(100);
        updatedUserAgent.setMaxHeight(100);
        updatedUserAgent.setStartingBitrate(100);


        when(userAgentRepo.findById(7)).thenReturn(Optional.of(userAgent));
        when(userAgentRepo.save(any(UserAgent.class))).thenReturn(updatedUserAgent);

        UserAgent newUserAgent = userAgentService.updateUserAgent(updatedUserAgent);

        assertNotNull(newUserAgent, "The updated user agent should not be null");
        assertEquals(updatedUserAgent, newUserAgent, "The updated user agent should match the original input user agent");
        verify(userAgentRepo, times(1)).findById(7);

        ArgumentCaptor<UserAgent> userAgentCaptor = ArgumentCaptor.forClass(UserAgent.class);
        verify(userAgentRepo, times(1)).save(userAgentCaptor.capture());

        UserAgent savedUserAgent = userAgentCaptor.getValue();
        assertEquals(updatedUserAgent.getId(), savedUserAgent.getId(), "The ID of the saved user agent should match the updated user agent's ID");
        assertEquals(updatedUserAgent.getGuid(), savedUserAgent.getGuid(), "The GUID of the saved user agent should match the updated user agent's GUID");
        assertEquals(updatedUserAgent.getOwnerid(), savedUserAgent.getOwnerid(), "The Owner ID of the saved user agent should match the updated user agent's Owner ID");
        assertEquals(updatedUserAgent.getFormat(), savedUserAgent.getFormat(), "The format of the saved user agent should match the updated user agent's format");
        assertEquals(updatedUserAgent.getMinBitRate(), savedUserAgent.getMinBitRate(), "The minimum bit rate of the saved user agent should match the updated user agent's minimum bit rate");
        assertEquals(updatedUserAgent.getMinHeight(), savedUserAgent.getMinHeight(), "The minimum height of the saved user agent should match the updated user agent's minimum height");
        assertEquals(updatedUserAgent.getMinWidth(), savedUserAgent.getMinWidth(), "The minimum width of the saved user agent should match the updated user agent's minimum width");
        assertEquals(updatedUserAgent.getMaxBitRate(), savedUserAgent.getMaxBitRate(), "The maximum bit rate of the saved user agent should match the updated user agent's maximum bit rate");
        assertEquals(updatedUserAgent.getMaxHeight(), savedUserAgent.getMaxHeight(), "The maximum height of the saved user agent should match the updated user agent's maximum height");
        assertEquals(updatedUserAgent.getMaxWidth(), savedUserAgent.getMaxWidth(), "The maximum width of the saved user agent should match the updated user agent's maximum width");
        assertEquals(updatedUserAgent.getStartingBitrate(), savedUserAgent.getStartingBitrate(), "The starting bit rate of the saved user agent should match the updated user agent's starting bit rate");

    }

    @Test
    public void testUpdateUserAgentEmpty() {

        UserAgent updatedUserAgent = new UserAgent();
        updatedUserAgent.setId(7);
        updatedUserAgent.setGuid("sample GUID 2");
        updatedUserAgent.setOwnerid(7);
        updatedUserAgent.setFormat("sample format 2");
        updatedUserAgent.setMinBitRate(100);
        updatedUserAgent.setMinWidth(100);
        updatedUserAgent.setMinHeight(100);
        updatedUserAgent.setMaxBitRate(100);
        updatedUserAgent.setMaxWidth(100);
        updatedUserAgent.setMaxHeight(100);
        updatedUserAgent.setStartingBitrate(100);


        when(userAgentRepo.findById(7)).thenReturn(Optional.empty());


        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            userAgentService.updateUserAgent(updatedUserAgent);
        });

        verify(userAgentRepo, times(1)).findById(7);
        verify(userAgentRepo, times(0)).save(any(UserAgent.class));
    }





}
