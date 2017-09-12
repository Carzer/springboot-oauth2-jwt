package com.carzer.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class MongoProperty {
	/** 主机地址IP+端口 **/
	@Value("${mongodb.host}")
	private String host;
	/** 数据库名称 ***/
	@Value("${mongodb.name}")
	private String name;
	/** 数据库用户（非必须） ***/
	@Value("${mongodb.user}")
	private String user;
	/** 数据库密码 **/
	@Value("${mongodb.pwd}")
	private String pwd;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
