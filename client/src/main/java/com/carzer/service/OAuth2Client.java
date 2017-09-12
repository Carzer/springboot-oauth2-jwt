package com.carzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by WangHQ on 2017/7/5 0005.
 */
@Component
public class OAuth2Client {
    @Autowired
    private OAuth2RestOperations restTemplate;

    public String getOauth2Token(String resourceURI)
    {
        String result="";
        restTemplate.getForObject(resourceURI, Map.class);
        OAuth2AccessToken testToken = restTemplate.getAccessToken();
        if(testToken != null){
            result = testToken.getValue();
        }
        return result;
    }

}
