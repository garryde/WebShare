package com.share.service;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class JsonMessageService {
    public String handle (String parameter) {
        return "{\"act\":\"openResult\",\"code\":\"" + parameter + "\"}";
    }
}
