package com.andrew.delds;


import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.Restriction;
import com.andrew.delds.repo.RestrictionRepo;
import com.andrew.delds.service.RestrictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RestrictionServiceTest {

    @InjectMocks
    private RestrictionService restrictionService;

    @Mock
    private RestrictionRepo restrictionRepo;

    private Restriction restriction;

    @BeforeEach
    public void setUp() {
        restriction = new Restriction();
        restriction.setId(7);
        restriction.setGuid("sample GUID");
        restriction.setOwnerid(7);
        restriction.setAvailabledate(new Date());
        restriction.setDomainrestriction(0);
        restriction.setExpirationdate(new Date());
        restriction.setGeolocationrestriction(0);
        restriction.setIpaddressrestriction(0);
        restriction.setMaxconlimit(0);
        restriction.setRequireauth("no");
        restriction.setUseragentrestriction(0);


    }

    @Test
    public void testCreateRestriction() {
        when(restrictionRepo.save(any(Restriction.class))).thenReturn(restriction);

        Restriction createdRestriction = restrictionService.saveRestriction(restriction);

        assertNotNull(createdRestriction, "The saved restriction should not be null");
        assertEquals(restriction, createdRestriction, "The saved restriction should match the original input restriction");
        verify(restrictionRepo, times(1)).save(restriction);
    }

    @Test
    public void testCreateRestrictions() {
        when(restrictionRepo.saveAll(anyList())).thenReturn(Collections.singletonList(restriction));

        List<Restriction> createdRestrictions = restrictionService.saveRestrictions(Collections.singletonList(restriction));

        assertNotNull(createdRestrictions, "The saved restrictions list should not be null");
        assertEquals(1, createdRestrictions.size(), "The size of the saved restrictions list should be 1");
        assertEquals(restriction, createdRestrictions.getFirst(), "The restriction in the saved restrictions list should match the original input restriction");

        verify(restrictionRepo, times(1)).saveAll(Collections.singletonList(restriction));
    }

    @Test
    public void testGetRestrictionsNotEmpty() throws ObjectNotFoundException {

        when(restrictionRepo.findAll()).thenReturn(Collections.singletonList(restriction));

        List<Restriction> fetchedRestrictions = restrictionService.getRestrictions();

        assertNotNull(fetchedRestrictions, "The fetched restrictions list should not be null");
        assertEquals(1, fetchedRestrictions.size(), "The size of the fetched restrictions list should be 1");
        assertEquals(restriction, fetchedRestrictions.getFirst(), "The restriction in the fetched restrictions list should match the original input restriction");
        verify(restrictionRepo, times(1)).findAll();

    }

    @Test
    public void testGetRestrictionsEmpty() {
        when(restrictionRepo.findAll()).thenReturn(Collections.emptyList());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            restrictionService.getRestrictions();
        });


    }

    @Test
    public void testGetRestrictionByIdNotEmpty() throws ObjectNotFoundException {
        when(restrictionRepo.findById(7)).thenReturn(Optional.of(restriction));

        Restriction fetchedRestriction = restrictionService.getRestrictionById(7);
        assertNotNull(fetchedRestriction, "The fetched restriction should not be null");
        assertEquals(restriction, fetchedRestriction, "The fetched restriction must match the original input restriction");
        verify(restrictionRepo, times(1)).findById(7);

    }

    @Test
    public void testGetRestrictionByIdEmpty() {
        when(restrictionRepo.findById(18)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            restrictionService.getRestrictionById(18);
        });
    }

    @Test
    public void testGetRestrictionByGuidNotEmpty() throws ObjectNotFoundException {
        when(restrictionRepo.findByGuid("sample GUID")).thenReturn(restriction);

        Restriction fetchedRestriction = restrictionService.getRestrictionByGuid("sample GUID");
        assertNotNull(fetchedRestriction, "The fetched restriction should not be null");
        assertEquals(restriction, fetchedRestriction, "The fetched restriction must match the original input restriction");
        verify(restrictionRepo, times(1)).findByGuid("sample GUID");

    }

    @Test
    public void testGetRestrictionByGuidEmpty() {
        when(restrictionRepo.findByGuid("sample GUID")).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            restrictionService.getRestrictionByGuid("sample GUID");
        });
    }

    @Test
    public void testGetRestrictionByOwneridNotEmpty() throws ObjectNotFoundException {
        when(restrictionRepo.findByOwnerid(7)).thenReturn(restriction);

        Restriction fetchedRestriction = restrictionService.getRestrictionByOwnerid(7);
        assertNotNull(fetchedRestriction, "The fetched restriction should not be null");
        assertEquals(restriction, fetchedRestriction, "The fetched restriction must match the original input restriction");
        verify(restrictionRepo, times(1)).findByOwnerid(7);

    }

    @Test
    public void testGetRestrictionByOwneridEmpty() {
        when(restrictionRepo.findByOwnerid(7)).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            restrictionService.getRestrictionByOwnerid(7);
        });
    }

    @Test
    public void testDeleteRestrictionNotEmpty() throws ObjectNotFoundException {
        when(restrictionRepo.findById(7)).thenReturn(Optional.of(restriction));
        doNothing().when(restrictionRepo).deleteById(7);

        restrictionService.deleteRestriction(7);
        verify(restrictionRepo, times(1)).findById(7);
        verify(restrictionRepo, times(1)).deleteById(7);
    }

    @Test
    public void testDeleteRestrictionEmpty() {
        when(restrictionRepo.findById(7)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            restrictionService.getRestrictionById(7);
        });
    }

    @Test
    public void testUpdateRestrictionNotEmpty() throws ObjectNotFoundException {
        Restriction updatedRestriction = new Restriction();
        updatedRestriction.setId(7);
        updatedRestriction.setGuid("sample GUID 2");
        updatedRestriction.setOwnerid(7);
        updatedRestriction.setAvailabledate(new Date());
        updatedRestriction.setDomainrestriction(0);
        updatedRestriction.setExpirationdate(new Date());
        updatedRestriction.setGeolocationrestriction(0);
        updatedRestriction.setIpaddressrestriction(0);
        updatedRestriction.setMaxconlimit(0);
        updatedRestriction.setRequireauth("yes");
        updatedRestriction.setUseragentrestriction(0);

        when(restrictionRepo.findById(7)).thenReturn(Optional.of(restriction));
        when(restrictionRepo.save(any(Restriction.class))).thenReturn(updatedRestriction);

        Restriction newRestriction = restrictionService.updateRestriction(updatedRestriction);

        assertNotNull(newRestriction, "The updated restriction should not be null");
        assertEquals(updatedRestriction, newRestriction, "The updated restriction should match the original input restriction");
        verify(restrictionRepo, times(1)).findById(7);

        ArgumentCaptor<Restriction> restrictionCaptor = ArgumentCaptor.forClass(Restriction.class);
        verify(restrictionRepo, times(1)).save(restrictionCaptor.capture());

        Restriction savedRestriction = restrictionCaptor.getValue();
        assertEquals(updatedRestriction.getId(), savedRestriction.getId(), "The ID of the saved restriction should match the updated restriction's ID");
        assertEquals(updatedRestriction.getGuid(), savedRestriction.getGuid(), "The GUID of the saved restriction should match the updated restriction's GUID");
        assertEquals(updatedRestriction.getOwnerid(), savedRestriction.getOwnerid(), "The Owner ID of the saved restriction should match the updated restriction's Owner ID");
        assertEquals(updatedRestriction.getMaxconlimit(), savedRestriction.getMaxconlimit(), "The Maxconlimit of the saved restriction should match the updated restriction's Maxconlimit");
        assertEquals(updatedRestriction.getAvailabledate(), savedRestriction.getAvailabledate(), "The available date of the saved restriction should match the updated restriction's available date");
        assertEquals(updatedRestriction.getExpirationdate(), savedRestriction.getExpirationdate(), "The expiration date of the saved restriction should match the updated restriction's expiration date");
        assertEquals(updatedRestriction.getDomainrestriction(), savedRestriction.getDomainrestriction(), "The domain restriction of the saved restriction should match the updated restriction's domain restriction");
        assertEquals(updatedRestriction.getGeolocationrestriction(), savedRestriction.getGeolocationrestriction(), "The geolocation restriction of the saved restriction should match the updated restriction's geolocation restriction");
        assertEquals(updatedRestriction.getIpaddressrestriction(), savedRestriction.getIpaddressrestriction(), "The IP address of the saved restriction should match the updated restriction's IP address");
        assertEquals(updatedRestriction.getRequireauth(), savedRestriction.getRequireauth(), "The requireauth of the saved restriction should match the updated restriction's requireauth");
        assertEquals(updatedRestriction.getUseragentrestriction(), savedRestriction.getUseragentrestriction(), "The user agent restriction of the saved restriction should match the updated restriction's user agent restriction");
    }

    @Test
    public void testUpdateRestrictionEmpty() {
        Restriction updatedRestriction = new Restriction();
        updatedRestriction.setId(7);
        updatedRestriction.setGuid("sample GUID 2");
        updatedRestriction.setOwnerid(7);
        updatedRestriction.setAvailabledate(new Date());
        updatedRestriction.setDomainrestriction(0);
        updatedRestriction.setExpirationdate(new Date());
        updatedRestriction.setGeolocationrestriction(0);
        updatedRestriction.setIpaddressrestriction(0);
        updatedRestriction.setMaxconlimit(0);
        updatedRestriction.setRequireauth("yes");
        updatedRestriction.setUseragentrestriction(0);

        when(restrictionRepo.findById(7)).thenReturn(Optional.empty());


        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            restrictionService.updateRestriction(updatedRestriction);
        });

        verify(restrictionRepo, times(1)).findById(7);
        verify(restrictionRepo, times(0)).save(any(Restriction.class));
    }


}
