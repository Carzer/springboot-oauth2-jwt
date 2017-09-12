package com.carzer.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.StringUtils;

@Configuration
public class MongoDBConfig {
	@Autowired
	MongoProperty mongoProperties;

	/**
	 * 注入mongodb的工厂类
	 * 
	 * @return MongoDbFactory
	 */
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		// uri格式mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
		String mongoURI = "mongodb://" + mongoProperties.getHost();
		if (!StringUtils.isEmpty(mongoProperties.getUser())) {
			mongoURI = "mongodb://" + mongoProperties.getUser() + ":" + mongoProperties.getPwd() + "@"
					+ mongoProperties.getHost();
		}
		// 为了方便实现mongodb多数据库和数据库的负债均衡这里使用url方式创建工厂
		MongoClientURI mongoClientURI = new MongoClientURI(mongoURI);
		MongoClient mongoClient = new MongoClient(mongoClientURI);
		return new SimpleMongoDbFactory(mongoClient, mongoProperties.getName());
	}

	/**
	 * 获取操作实例
	 * 
	 * @param  mongoDbFactory db
	 * @return MongoTemplate
	 */
	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
		return new MongoTemplate(mongoDbFactory);
	}

}
