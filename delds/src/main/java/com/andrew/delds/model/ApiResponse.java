package com.andrew.delds.model;

public class ApiResponse<T> {
    private String cid;
    private T data;

    public ApiResponse(String cid, T data) {
        this.cid = cid;
        this.data = data;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
