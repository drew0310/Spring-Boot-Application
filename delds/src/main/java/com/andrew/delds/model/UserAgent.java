package com.andrew.delds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserAgent {

    @Id
    private int id;
    private String guid;
    private long ownerid;
    private String format;
    private long maxBitRate;
    private int maxHeight;
    private int maxWidth;
    private long minBitRate;
    private int minHeight;
    private int minWidth;
    private long startingBitrate;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getMaxBitRate() {
        return maxBitRate;
    }

    public void setMaxBitRate(long maxBitRate) {
        this.maxBitRate = maxBitRate;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public long getMinBitRate() {
        return minBitRate;
    }

    public void setMinBitRate(long minBitRate) {
        this.minBitRate = minBitRate;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public long getStartingBitrate() {
        return startingBitrate;
    }

    public void setStartingBitrate(long startingBitrate) {
        this.startingBitrate = startingBitrate;
    }



}
