package com.pd.benchmark.mongodb;

import org.springframework.aop.framework.DefaultAdvisorChainFactory;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.config.DefaultRepositoryConfiguration;

import com.pd.benchmark.dataobjects.RuntimeIndicator;
import com.pd.benchmark.geode.GemFireConfiguration;



@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.pd.benchmark.mongodb")
@Import(GemFireConfiguration.class)

public class BenchmarkMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenchmarkMongoApplication.class, args);
		RuntimeIndicator runtimeIndicator = RuntimeIndicator.getRuntimeIndicator();
			runtimeIndicator.runtimeEnvironment = args[0];

		System.out.println(runtimeIndicator.runtimeEnvironment);
		
	}

}
