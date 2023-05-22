package com.pd.benchmark.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.pd.benchmark.dataobjects.RuntimeIndicator;
import com.pd.benchmark.geode.GemFireConfiguration;

@SpringBootApplication
@ComponentScan("com.pd.benchmark.jpa")
@Import(GemFireConfiguration.class)
public class BenchmarkPostgresApplication {

    
	public static void main(String[] args) {
		SpringApplication.run(BenchmarkPostgresApplication.class, args);
		RuntimeIndicator runtimeIndicator = RuntimeIndicator.getRuntimeIndicator();
		if(args != null && args.length > 0) {
			for(String s: args) {
				System.out.println(s);
			}
			runtimeIndicator.runtimeEnvironment = args[0];
		
		}
		System.out.println(runtimeIndicator.runtimeEnvironment);
	}
}
