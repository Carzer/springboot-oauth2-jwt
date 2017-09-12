package com.carzer.controller;

import com.carzer.service.OAuth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
class UserController {
    @Autowired
    private OAuth2Client oAuth2Client;

    @Value("${config.oauth2.resourceURI}")
    private String resourceURI;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    /*@RequestMapping("/")
    public JsonNode home() {
        JsonNode node = restTemplate.getForObject(resourceURI, JsonNode.class);
        return node;
    }*/

    @RequestMapping("/")
    public ModelAndView home() {
        String token = oAuth2Client.getOauth2Token(resourceURI);
        System.out.println(token);
        if(!StringUtils.isEmpty(token)) token="redirect:"+resourceURI+"?access_token="+token;
        return new ModelAndView(token);
    }

    @RequestMapping("/hello")
    public String hello(){
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("username", "test");
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("password", "test");
        String token = restTemplate.getAccessToken().toString();
        return token;
    }

}