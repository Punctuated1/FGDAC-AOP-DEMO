package com.pd.benchmark.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.ClientSessionOptions;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Bean
	public MongoClient mongoClient() {
		ConnectionString connectionString = new ConnectionString ("mongodb://root:example@127.0.0.1:27017/admin");
		MongoCredential credential = MongoCredential.createCredential("root", "admin", "example".toCharArray());
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();
//		MongoClient mongoClient = MongoClient.create(mongoClientSettings);
//		ClientSessionOptions clientSessionOptions = new ClientSessionOptions();
//		benchmark
//		mongoClient.
		return MongoClients.create(mongoClientSettings);
	}
	
//	@Bean
//	public MongoDatabaseFactory mongoDbFactory() {
//		MongoDatabaseFactory databaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
//		return databaseFactory;
//	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(),getDatabaseName());
	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return "benchmark";
	}
	
}
