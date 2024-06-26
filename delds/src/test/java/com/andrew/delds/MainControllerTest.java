package com.andrew.delds;

import com.andrew.delds.controller.MainController;
import com.andrew.delds.model.Account;
import com.andrew.delds.model.AdPolicy;
import com.andrew.delds.model.Restriction;
import com.andrew.delds.model.UserAgent;
import com.andrew.delds.service.AccountService;
import com.andrew.delds.service.AdPolicyService;
import com.andrew.delds.service.RestrictionService;
import com.andrew.delds.service.UserAgentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MainController.class)
public class MainControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private AdPolicyService adPolicyService;

    @MockBean
    private RestrictionService restrictionService;

    @MockBean
    private UserAgentService userAgentService;

    @Autowired
    private MockMvc mockMvc;

    private Account account;

    private AdPolicy adPolicy;

    private Restriction restriction;
    String availableDateStr;
    String expirationDateStr;


    public UserAgent userAgent;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(7);
        account.setGuid("sample GUID");
        account.setOwnerid(7L);
        account.setLocked(0);
        account.setMaxconlimit(100);

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        availableDateStr = dateFormat.format(restriction.getAvailabledate()).replace("Z", "+00:00");
        expirationDateStr = dateFormat.format(restriction.getExpirationdate()).replace("Z", "+00:00");

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
    public void testAddAccount() throws Exception {

        when(accountService.saveAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/delivery/data/AccountSettings/addAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(account.getId()))
                .andExpect(jsonPath("$.data.guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data.locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data.maxconlimit").value(account.getMaxconlimit()));

    }

    @Test
    public void testAddAccounts() throws Exception {

        when(accountService.saveAccounts(anyList())).thenReturn(Collections.singletonList(account));

        mockMvc.perform(post("/delivery/data/AccountSettings/addAccounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.singletonList(account))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(account.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data[0].locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data[0].maxconlimit").value(account.getMaxconlimit()));

    }

    @Test
    public void testFindAllAccounts() throws Exception {
        when(accountService.getAccounts()).thenReturn(Collections.singletonList(account));

        mockMvc.perform(get("/delivery/data/AccountSettings/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(account.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data[0].locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data[0].maxconlimit").value(account.getMaxconlimit()));

    }

    @Test
    public void testFindAccountById() throws Exception {
        when(accountService.getAccountById(7)).thenReturn(account);

        mockMvc.perform(get("/delivery/data/AccountSettings/accountsById/{id}", 7)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(account.getId()))
                .andExpect(jsonPath("$.data.guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data.locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data.maxconlimit").value(account.getMaxconlimit()));
    }

    @Test
    public void testFindAccountByGuid() throws Exception {
        when(accountService.getAccountByGuid("sample GUID")).thenReturn(account);

        mockMvc.perform(get("/delivery/data/AccountSettings/accountsByGuid/{guid}", "sample GUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(account.getId()))
                .andExpect(jsonPath("$.data.guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data.locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data.maxconlimit").value(account.getMaxconlimit()));
    }

    @Test
    public void testFindAccountByOwnerid() throws Exception {
        when(accountService.getAccountByOwnerid(7L)).thenReturn(account);

        mockMvc.perform(get("/delivery/data/AccountSettings/accountsByOwnerid/{ownerid}", 7L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(account.getId()))
                .andExpect(jsonPath("$.data.guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data.locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data.maxconlimit").value(account.getMaxconlimit()));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        when(accountService.deleteAccount(7)).thenReturn("Account object with ID 7 removed");

        mockMvc.perform(delete("/delivery/data/AccountSettings/deleteAccount/{id}", 7)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.data").value("Account object with ID 7 removed"));

    }

    @Test
    public void testUpdateAccount() throws Exception {

        when(accountService.updateAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(put("/delivery/data/AccountSettings/updateAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(account.getId()))
                .andExpect(jsonPath("$.data.guid").value(account.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(account.getOwnerid()))
                .andExpect(jsonPath("$.data.locked").value(account.getLocked()))
                .andExpect(jsonPath("$.data.maxconlimit").value(account.getMaxconlimit()));

    }








    @Test
    public void testAddAdPolicy() throws Exception {

        when(adPolicyService.saveAdPolicy(any(AdPolicy.class))).thenReturn(adPolicy);

        mockMvc.perform(post("/delivery/data/AdPolicy/addAdPolicy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adPolicy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data.guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data.adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data.adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data.adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data.canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data.count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data.duration").value(adPolicy.getDuration()));

    }

    @Test
    public void testAddAdPolicies() throws Exception {

        when(adPolicyService.saveAdPolicies(anyList())).thenReturn(Collections.singletonList(adPolicy));

        mockMvc.perform(post("/delivery/data/AdPolicy/addAdPolicies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.singletonList(adPolicy))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data[0].adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data[0].adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data[0].adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data[0].canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data[0].count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data[0].duration").value(adPolicy.getDuration()));

    }

    @Test
    public void testFindAllAdPolicies() throws Exception {
        when(adPolicyService.getAdPolicies()).thenReturn(Collections.singletonList(adPolicy));

        mockMvc.perform(get("/delivery/data/AdPolicy/adPolicies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data[0].adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data[0].adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data[0].adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data[0].canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data[0].count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data[0].duration").value(adPolicy.getDuration()));


    }

    @Test
    public void testFindAdPolicyById() throws Exception {
        when(adPolicyService.getAdPolicyById(7)).thenReturn(adPolicy);

        mockMvc.perform(get("/delivery/data/AdPolicy/adPoliciesById/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data.guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data.adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data.adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data.adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data.canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data.count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data.duration").value(adPolicy.getDuration()));

    }

    @Test
    public void testFindAdPolicyByGuid() throws Exception {
        when(adPolicyService.getAdPolicyByGuid("sample GUID")).thenReturn(adPolicy);

        mockMvc.perform(get("/delivery/data/AdPolicy/adPoliciesByGuid/{guid}", "sample GUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data.guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data.adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data.adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data.adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data.canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data.count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data.duration").value(adPolicy.getDuration()));

    }

    @Test
    public void testFindAdPolicyByOwnerid() throws Exception {
        when(adPolicyService.getAdPolicyByOwnerid(7L)).thenReturn(adPolicy);

        mockMvc.perform(get("/delivery/data/AdPolicy/adPoliciesByOwnerid/{ownerid}", 7L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data.guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data.adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data.adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data.adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data.canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data.count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data.duration").value(adPolicy.getDuration()));
    }

    @Test
    public void testFindAdPolicyByAdtype() throws Exception {
        when(adPolicyService.getAdPolicyByAdType("sample ad type")).thenReturn(Collections.singletonList(adPolicy));

        mockMvc.perform(get("/delivery/data/AdPolicy/adPoliciesByAdType/{adtype}", "sample ad type")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data[0].adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data[0].adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data[0].adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data[0].canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data[0].count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data[0].duration").value(adPolicy.getDuration()));
    }

    @Test
    public void testDeleteAdPolicy() throws Exception {
        when(adPolicyService.deleteAdPolicy(7)).thenReturn("AdPolicy object with ID 7 removed");

        mockMvc.perform(delete("/delivery/data/AdPolicy/deleteAdPolicy/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.data").value("AdPolicy object with ID 7 removed"));


    }

    @Test
    public void testUpdateAdPolicy() throws Exception {

        when(adPolicyService.updateAdPolicy(any(AdPolicy.class))).thenReturn(adPolicy);

        mockMvc.perform(put("/delivery/data/AdPolicy/updateAdPolicy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adPolicy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(adPolicy.getId()))
                .andExpect(jsonPath("$.data.guid").value(adPolicy.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(adPolicy.getOwnerid()))
                .andExpect(jsonPath("$.data.adtype").value(adPolicy.getAdtype()))
                .andExpect(jsonPath("$.data.adtitle").value(adPolicy.getAdtitle()))
                .andExpect(jsonPath("$.data.adformat").value(adPolicy.getAdformat()))
                .andExpect(jsonPath("$.data.canskip").value(adPolicy.getCanskip()))
                .andExpect(jsonPath("$.data.count").value(adPolicy.getCount()))
                .andExpect(jsonPath("$.data.duration").value(adPolicy.getDuration()));

    }






    @Test
    public void testAddRestriction() throws Exception {

        when(restrictionService.saveRestriction(any(Restriction.class))).thenReturn(restriction);

        mockMvc.perform(post("/delivery/data/Restriction/addRestriction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restriction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(restriction.getId()))
                .andExpect(jsonPath("$.data.guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data.availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data.expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data.domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data.geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data.ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data.useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data.requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data.maxconlimit").value(restriction.getMaxconlimit()));

    }

    @Test
    public void testAddRestrictions() throws Exception {

        when(restrictionService.saveRestrictions(anyList())).thenReturn(Collections.singletonList(restriction));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String availableDateStr = dateFormat.format(restriction.getAvailabledate()).replace("Z", "+00:00");
        String expirationDateStr = dateFormat.format(restriction.getExpirationdate()).replace("Z", "+00:00");


        mockMvc.perform(post("/delivery/data/Restriction/addRestrictions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.singletonList(restriction))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(restriction.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data[0].availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data[0].expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data[0].domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data[0].geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data[0].ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data[0].useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data[0].requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data[0].maxconlimit").value(restriction.getMaxconlimit()));

    }

    @Test
    public void testFindAllRestrictions() throws Exception {
        when(restrictionService.getRestrictions()).thenReturn(Collections.singletonList(restriction));

        mockMvc.perform(get("/delivery/data/Restriction/restrictions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(restriction.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data[0].availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data[0].expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data[0].domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data[0].geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data[0].ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data[0].useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data[0].requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data[0].maxconlimit").value(restriction.getMaxconlimit()));



    }

    @Test
    public void testFindRestrictionById() throws Exception {
        when(restrictionService.getRestrictionById(7)).thenReturn(restriction);

        mockMvc.perform(get("/delivery/data/Restriction/restrictionsById/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(restriction.getId()))
                .andExpect(jsonPath("$.data.guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data.availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data.expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data.domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data.geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data.ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data.useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data.requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data.maxconlimit").value(restriction.getMaxconlimit()));
    }

    @Test
    public void testFindRestrictionByGuid() throws Exception {
        when(restrictionService.getRestrictionByGuid("sample GUID")).thenReturn(restriction);

        mockMvc.perform(get("/delivery/data/Restriction/restrictionsByGuid/{guid}", "sample GUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(restriction.getId()))
                .andExpect(jsonPath("$.data.guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data.availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data.expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data.domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data.geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data.ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data.useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data.requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data.maxconlimit").value(restriction.getMaxconlimit()));

    }

    @Test
    public void testFindRestrictionByOwnerid() throws Exception {
        when(restrictionService.getRestrictionByOwnerid(7L)).thenReturn(restriction);

        mockMvc.perform(get("/delivery/data/Restriction/restrictionsByOwnerid/{ownerid}", 7L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(restriction.getId()))
                .andExpect(jsonPath("$.data.guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data.availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data.expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data.domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data.geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data.ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data.useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data.requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data.maxconlimit").value(restriction.getMaxconlimit()));
    }

    @Test
    public void testDeleteRestriction() throws Exception {
        when(restrictionService.deleteRestriction(7)).thenReturn("Restriction object with ID 7 removed");

        mockMvc.perform(delete("/delivery/data/Restriction/deleteRestriction/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.data").value("Restriction object with ID 7 removed"));

    }

    @Test
    public void testUpdateRestriction() throws Exception {

        when(restrictionService.updateRestriction(any(Restriction.class))).thenReturn(restriction);

        mockMvc.perform(put("/delivery/data/Restriction/updateRestriction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restriction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(restriction.getId()))
                .andExpect(jsonPath("$.data.guid").value(restriction.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(restriction.getOwnerid()))
                .andExpect(jsonPath("$.data.availabledate").value(availableDateStr))
                .andExpect(jsonPath("$.data.expirationdate").value(expirationDateStr))
                .andExpect(jsonPath("$.data.domainrestriction").value(restriction.getDomainrestriction()))
                .andExpect(jsonPath("$.data.geolocationrestriction").value(restriction.getGeolocationrestriction()))
                .andExpect(jsonPath("$.data.ipaddressrestriction").value(restriction.getIpaddressrestriction()))
                .andExpect(jsonPath("$.data.useragentrestriction").value(restriction.getUseragentrestriction()))
                .andExpect(jsonPath("$.data.requireauth").value(restriction.getRequireauth()))
                .andExpect(jsonPath("$.data.maxconlimit").value(restriction.getMaxconlimit()));


    }








    @Test
    public void testAddUserAgent() throws Exception {

        when(userAgentService.saveUserAgent(any(UserAgent.class))).thenReturn(userAgent);

        mockMvc.perform(post("/delivery/data/UserAgent/addUserAgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userAgent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data.guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data.format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data.maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data.maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data.maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data.minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data.minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data.minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data.startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testAddUserAgents() throws Exception {

        when(userAgentService.saveUserAgents(anyList())).thenReturn(Collections.singletonList(userAgent));

        mockMvc.perform(post("/delivery/data/UserAgent/addUserAgents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Collections.singletonList(userAgent))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data[0].format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data[0].maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data[0].maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data[0].maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data[0].minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data[0].minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data[0].minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data[0].startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testFindAllUserAgents() throws Exception {
        when(userAgentService.getUserAgents()).thenReturn(Collections.singletonList(userAgent));

        mockMvc.perform(get("/delivery/data/UserAgent/userAgents")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data[0].format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data[0].maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data[0].maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data[0].maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data[0].minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data[0].minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data[0].minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data[0].startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testFindUserAgentById() throws Exception {
        when(userAgentService.getUserAgentById(7)).thenReturn(userAgent);

        mockMvc.perform(get("/delivery/data/UserAgent/userAgentsById/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data.guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data.format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data.maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data.maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data.maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data.minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data.minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data.minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data.startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testFindUserAgentByGuid() throws Exception {
        when(userAgentService.getUserAgentByGuid("sample GUID")).thenReturn(userAgent);

        mockMvc.perform(get("/delivery/data/UserAgent/userAgentsByGuid/{guid}", "sample GUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data.guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data.format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data.maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data.maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data.maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data.minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data.minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data.minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data.startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testFindUserAgentByOwnerid() throws Exception {
        when(userAgentService.getUserAgentByOwnerid(7)).thenReturn(userAgent);

        mockMvc.perform(get("/delivery/data/UserAgent/userAgentsByOwnerid/{ownerid}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data.guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data.format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data.maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data.maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data.maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data.minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data.minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data.minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data.startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testFindUserAgentByStartingBitrate() throws Exception {
        when(userAgentService.getUserAgentByStartingBitrate(0)).thenReturn(Collections.singletonList(userAgent));

        mockMvc.perform(get("/delivery/data/UserAgent/userAgentsByStartingBitrate/{bitrate}", 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data[0].guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data[0].ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data[0].format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data[0].maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data[0].maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data[0].maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data[0].minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data[0].minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data[0].minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data[0].startingBitrate").value(userAgent.getStartingBitrate()));


    }

    @Test
    public void testDeleteUserAgent() throws Exception {
        when(userAgentService.deleteUserAgent(7)).thenReturn("UserAgent object with ID 7 removed");

        mockMvc.perform(delete("/delivery/data/UserAgent/deleteUserAgent/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.data").value("UserAgent object with ID 7 removed"));

    }

    @Test
    public void testUpdateUserAgent() throws Exception {

        when(userAgentService.updateUserAgent(any(UserAgent.class))).thenReturn(userAgent);

        mockMvc.perform(put("/delivery/data/UserAgent/updateUserAgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userAgent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userAgent.getId()))
                .andExpect(jsonPath("$.data.guid").value(userAgent.getGuid()))
                .andExpect(jsonPath("$.data.ownerid").value(userAgent.getOwnerid()))
                .andExpect(jsonPath("$.data.format").value(userAgent.getFormat()))
                .andExpect(jsonPath("$.data.maxBitRate").value(userAgent.getMaxBitRate()))
                .andExpect(jsonPath("$.data.maxHeight").value(userAgent.getMaxHeight()))
                .andExpect(jsonPath("$.data.maxWidth").value(userAgent.getMaxWidth()))
                .andExpect(jsonPath("$.data.minBitRate").value(userAgent.getMinBitRate()))
                .andExpect(jsonPath("$.data.minHeight").value(userAgent.getMinHeight()))
                .andExpect(jsonPath("$.data.minWidth").value(userAgent.getMinWidth()))
                .andExpect(jsonPath("$.data.startingBitrate").value(userAgent.getStartingBitrate()));


    }






















}
