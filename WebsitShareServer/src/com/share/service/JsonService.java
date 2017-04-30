package com.share.service;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class JsonService {
    public String handle (String parameter) {
        return "{\"act\":\"openResult\",\"code\":\"" + parameter + "\"}";
    }
}
