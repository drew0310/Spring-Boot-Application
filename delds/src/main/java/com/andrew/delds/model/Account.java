package com.andrew.delds.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private int id;
    private String guid;
    private long ownerid;
    private int locked;
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

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getMaxconlimit() {
        return maxconlimit;
    }

    public void setMaxconlimit(int maxconlimit) {
        this.maxconlimit = maxconlimit;
    }



}
