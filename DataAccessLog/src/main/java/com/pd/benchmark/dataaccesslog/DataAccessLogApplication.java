package com.pd.benchmark.dataaccesslog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan("com.pd.benchmark.dataaccesslog")
@EnableMongoRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DataAccessLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAccessLogApplication.class, args);
	}

}
