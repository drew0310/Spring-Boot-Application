package com.andrew.delds.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AdPolicy {

    @Id
    private int id;
    private String guid;
    private long ownerid;
    private String adtype;
    private String adtitle;
    private int canskip;
    private int duration;
    private int count;
    private String adformat;




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

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public String getAdtitle() {
        return adtitle;
    }

    public void setAdtitle(String adtitle) {
        this.adtitle = adtitle;
    }

    public int getCanskip() {
        return canskip;
    }

    public void setCanskip(int canskip) {
        this.canskip = canskip;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAdformat() {
        return adformat;
    }

    public void setAdformat(String adformat) {
        this.adformat = adformat;
    }

}
