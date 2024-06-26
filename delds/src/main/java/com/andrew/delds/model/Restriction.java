package com.andrew.delds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restriction {

    @Id
    private int id;
    private String guid;
    private long ownerid;
    private Date availabledate;
    private Date expirationdate;
    private int domainrestriction;
    private int geolocationrestriction;
    private int ipaddressrestriction;
    private int useragentrestriction;

    private String requireauth;

    private int maxconlimit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public Date getAvailabledate() {
        return availabledate;
    }

    public void setAvailabledate(Date availabledate) {
        this.availabledate = availabledate;
    }

    public Date getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }

    public int getDomainrestriction() {
        return domainrestriction;
    }

    public void setDomainrestriction(int domainrestriction) {
        this.domainrestriction = domainrestriction;
    }

    public int getGeolocationrestriction() {
        return geolocationrestriction;
    }

    public void setGeolocationrestriction(int geolocationrestriction) {
        this.geolocationrestriction = geolocationrestriction;
    }

    public int getIpaddressrestriction() {
        return ipaddressrestriction;
    }

    public void setIpaddressrestriction(int ipaddressrestriction) {
        this.ipaddressrestriction = ipaddressrestriction;
    }

    public int getUseragentrestriction() {
        return useragentrestriction;
    }

    public void setUseragentrestriction(int useragentrestriction) {
        this.useragentrestriction = useragentrestriction;
    }

    public String getRequireauth() {
        return requireauth;
    }

    public void setRequireauth(String requireauth) {
        this.requireauth = requireauth;
    }

    public int getMaxconlimit() {
        return maxconlimit;
    }

    public void setMaxconlimit(int maxconlimit) {
        this.maxconlimit = maxconlimit;
    }
}
