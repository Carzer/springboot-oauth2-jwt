package com.carzer.model;

import java.io.Serializable;

/**
 * Created by WangHQ on 2017/7/6 0006.
 */
public class BaseUserDTO implements Serializable{

    private static final long serialVersionUID = 7815723327741280024L;
    private String id;
    private String loginName;
    private String userName;
    private String nickName;
    private String password;
    private String[] roles;
    private String depart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
