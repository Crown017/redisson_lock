package com.sqhtech.redisson.Entity;

public class GlobalResponse {
    private int responseCode;
    private Object responseData;

    public GlobalResponse(int responseCode, Object responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public static GlobalResponse fail(int responseCode, Object responseData){
        return  new GlobalResponse(responseCode,responseData);
    }

    public static GlobalResponse success(Object responseData){
        return  new GlobalResponse(200,responseData);
    }
}
