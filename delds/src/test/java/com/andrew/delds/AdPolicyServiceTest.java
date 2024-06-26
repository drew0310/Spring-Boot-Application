package com.andrew.delds;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.AdPolicy;
import com.andrew.delds.repo.AdPolicyRepo;
import com.andrew.delds.service.AdPolicyService;
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
public class AdPolicyServiceTest {

    @InjectMocks
    private AdPolicyService adPolicyService;

    @Mock
    private AdPolicyRepo adPolicyRepo;

    private AdPolicy adPolicy;

    @BeforeEach
    public void setUp() {
        adPolicy = new AdPolicy();
        adPolicy.setId(7);
        adPolicy.setGuid("sample GUID");
        adPolicy.setOwnerid(7);
        adPolicy.setAdformat("sample ad format");
        adPolicy.setAdtitle("sample ad title");
        adPolicy.setAdtype("sample ad type");
        adPolicy.setCanskip(0);
        adPolicy.setCount(0);
        adPolicy.setDuration(0);


    }

    @Test
    public void testCreateAdPolicy() {
        when(adPolicyRepo.save(any(AdPolicy.class))).thenReturn(adPolicy);

        AdPolicy createdAdPolicy = adPolicyService.saveAdPolicy(adPolicy);

        assertNotNull(createdAdPolicy, "The saved ad policy should not be null");
        assertEquals(adPolicy, createdAdPolicy, "The saved ad policy should match the original input account");
        verify(adPolicyRepo, times(1)).save(adPolicy);
    }

    @Test
    public void testCreateAdPolicies() {
        when(adPolicyRepo.saveAll(anyList())).thenReturn(Collections.singletonList(adPolicy));

        List<AdPolicy> createdAdPolicies = adPolicyService.saveAdPolicies(Collections.singletonList(adPolicy));

        assertNotNull(createdAdPolicies, "The saved ad policies list should not be null");
        assertEquals(1, createdAdPolicies.size(), "The size of the saved ad policies list should be 1");
        assertEquals(adPolicy, createdAdPolicies.getFirst(), "The ad policy in the saved ad policies list should match the original input ad policy");

        verify(adPolicyRepo, times(1)).saveAll(Collections.singletonList(adPolicy));
    }

    @Test
    public void testGetAdPoliciesNotEmpty() throws ObjectNotFoundException {

        when(adPolicyRepo.findAll()).thenReturn(Collections.singletonList(adPolicy));

        List<AdPolicy> fetchedAdPolicies = adPolicyService.getAdPolicies();

        assertNotNull(fetchedAdPolicies, "The fetched ad policies list should not be null");
        assertEquals(1, fetchedAdPolicies.size(), "The size of the fetched ad policies list should be 1");
        assertEquals(adPolicy, fetchedAdPolicies.getFirst(), "The ad policy in the fetched ad policies list should match the original input ad policy");
        verify(adPolicyRepo, times(1)).findAll();

    }

    @Test
    public void testGetAdPoliciesEmpty() {
        when(adPolicyRepo.findAll()).thenReturn(Collections.emptyList());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.getAdPolicies();
        });


    }

    @Test
    public void testGetAdPolicyByIdNotEmpty() throws ObjectNotFoundException {
        when(adPolicyRepo.findById(7)).thenReturn(Optional.of(adPolicy));

        AdPolicy fetchedAdPolicy = adPolicyService.getAdPolicyById(7);
        assertNotNull(fetchedAdPolicy, "The fetched ad policy should not be null");
        assertEquals(adPolicy, fetchedAdPolicy, "The fetched ad policy must match the original input ad policy");
        verify(adPolicyRepo, times(1)).findById(7);

    }

    @Test
    public void testGetAdPolicyByIdEmpty() {
        when(adPolicyRepo.findById(18)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.getAdPolicyById(18);
        });
    }

    @Test
    public void testGetAdPolicyByGuidNotEmpty() throws ObjectNotFoundException {
        when(adPolicyRepo.findByGuid("sample GUID")).thenReturn(adPolicy);

        AdPolicy fetchedAdPolicy = adPolicyService.getAdPolicyByGuid("sample GUID");
        assertNotNull(fetchedAdPolicy, "The fetched ad policy should not be null");
        assertEquals(adPolicy, fetchedAdPolicy, "The fetched ad policy must match the original input ad policy");
        verify(adPolicyRepo, times(1)).findByGuid("sample GUID");

    }

    @Test
    public void testGetAdPolicyByGuidEmpty() {
        when(adPolicyRepo.findByGuid("sample GUID")).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.getAdPolicyByGuid("sample GUID");
        });
    }

    @Test
    public void testGetAdPolicyByOwneridNotEmpty() throws ObjectNotFoundException {
        when(adPolicyRepo.findByOwnerid(7)).thenReturn(adPolicy);

        AdPolicy fetchedAdPolicy = adPolicyService.getAdPolicyByOwnerid(7);
        assertNotNull(fetchedAdPolicy, "The fetched ad policy should not be null");
        assertEquals(adPolicy, fetchedAdPolicy, "The fetched ad policy must match the original input ad policy");
        verify(adPolicyRepo, times(1)).findByOwnerid(7);

    }

    @Test
    public void testGetAdPolicyByOwneridEmpty() {
        when(adPolicyRepo.findByOwnerid(7)).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.getAdPolicyByOwnerid(7);
        });
    }

    @Test
    public void testGetAdPoliciesByAdTypeNotEmpty() throws ObjectNotFoundException {

        when(adPolicyRepo.findByAdtype("sample ad type")).thenReturn(Collections.singletonList(adPolicy));

        List<AdPolicy> fetchedAdPolicies = adPolicyService.getAdPolicyByAdType("sample ad type");

        assertNotNull(fetchedAdPolicies, "The fetched ad policies list should not be null");
        assertEquals(1, fetchedAdPolicies.size(), "The size of the fetched ad policies list should be 1");
        assertEquals(adPolicy, fetchedAdPolicies.getFirst(), "The ad policy in the fetched ad policies list should match the original input ad policy");
        verify(adPolicyRepo, times(1)).findByAdtype("sample ad type");


    }

    @Test
    public void testGetAdPoliciesByAdTypeEmpty() {
        when(adPolicyRepo.findByAdtype("sample ad type")).thenReturn(Collections.emptyList());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.getAdPolicyByAdType("sample ad type");
        });
    }

    @Test
    public void testDeleteAdPolicyNotEmpty() throws ObjectNotFoundException {
        when(adPolicyRepo.findById(7)).thenReturn(Optional.of(adPolicy));
        doNothing().when(adPolicyRepo).deleteById(7);

        adPolicyService.deleteAdPolicy(7);
        verify(adPolicyRepo, times(1)).findById(7);
        verify(adPolicyRepo, times(1)).deleteById(7);
    }

    @Test
    public void testDeleteAdPolicyEmpty() {
        when(adPolicyRepo.findById(7)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.getAdPolicyById(7);
        });
    }

    @Test
    public void testUpdateAdPolicyNotEmpty() throws ObjectNotFoundException {
        AdPolicy updatedAdPolicy = new AdPolicy();
        updatedAdPolicy.setId(7);
        updatedAdPolicy.setGuid("sample GUID 2");
        updatedAdPolicy.setOwnerid(7);
        updatedAdPolicy.setAdformat("sample format 2");
        updatedAdPolicy.setAdtitle("sample title 2");
        updatedAdPolicy.setAdtype("sample ad type 2");
        updatedAdPolicy.setCanskip(1);
        updatedAdPolicy.setCount(1);
        updatedAdPolicy.setDuration(200);


        when(adPolicyRepo.findById(7)).thenReturn(Optional.of(adPolicy));
        when(adPolicyRepo.save(any(AdPolicy.class))).thenReturn(updatedAdPolicy);

        AdPolicy newAdPolicy = adPolicyService.updateAdPolicy(updatedAdPolicy);

        assertNotNull(newAdPolicy, "The updated ad policy should not be null");
        assertEquals(updatedAdPolicy, newAdPolicy, "The updated ad policy should match the original input ad policy");
        verify(adPolicyRepo, times(1)).findById(7);

        ArgumentCaptor<AdPolicy> adPolicyCaptor = ArgumentCaptor.forClass(AdPolicy.class);
        verify(adPolicyRepo, times(1)).save(adPolicyCaptor.capture());

        AdPolicy savedAdPolicy = adPolicyCaptor.getValue();
        assertEquals(updatedAdPolicy.getId(), savedAdPolicy.getId(), "The ID of the saved ad policy should match the updated ad policy's ID");
        assertEquals(updatedAdPolicy.getGuid(), savedAdPolicy.getGuid(), "The GUID of the saved ad policy should match the updated ad policy's GUID");
        assertEquals(updatedAdPolicy.getOwnerid(), savedAdPolicy.getOwnerid(), "The Owner ID of the saved ad policy should match the updated ad policy's Owner ID");
        assertEquals(updatedAdPolicy.getAdformat(), savedAdPolicy.getAdformat(), "The ad format of the saved ad policy should match the updated ad policy's ad format");
        assertEquals(updatedAdPolicy.getAdtitle(), savedAdPolicy.getAdtitle(), "The title of the saved ad policy should match the updated ad policy's title");
        assertEquals(updatedAdPolicy.getAdtype(), savedAdPolicy.getAdtype(), "The ad type of the saved ad policy should match the updated ad policy's ad type");
        assertEquals(updatedAdPolicy.getCanskip(), savedAdPolicy.getCanskip(), "The canSkip of the saved ad policy should match the updated ad policy's canSkip");
        assertEquals(updatedAdPolicy.getCount(), savedAdPolicy.getCount(), "The ad count of the saved ad policy should match the updated ad policy's ad count");
        assertEquals(updatedAdPolicy.getDuration(), savedAdPolicy.getDuration(), "The duration of the saved ad policy should match the updated ad policy's duration");

    }

    @Test
    public void testUpdateAdPolicyEmpty() {
        AdPolicy updatedAdPolicy = new AdPolicy();
        updatedAdPolicy.setId(7);
        updatedAdPolicy.setGuid("sample GUID 2");
        updatedAdPolicy.setOwnerid(7);
        updatedAdPolicy.setAdformat("sample format 2");
        updatedAdPolicy.setAdtitle("sample title 2");
        updatedAdPolicy.setAdtype("sample ad type 2");
        updatedAdPolicy.setCanskip(1);
        updatedAdPolicy.setCount(1);
        updatedAdPolicy.setDuration(200);

        when(adPolicyRepo.findById(7)).thenReturn(Optional.empty());


        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            adPolicyService.updateAdPolicy(updatedAdPolicy);
        });

        verify(adPolicyRepo, times(1)).findById(7);
        verify(adPolicyRepo, times(0)).save(any(AdPolicy.class));
    }








}
