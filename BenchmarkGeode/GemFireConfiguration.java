package com.pd.benchmark.geode;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.geode.cache.GemFireCache;
//import org.apache.geode.cache.client.ClientCache;
//import org.apache.geode.cache.client.ClientCacheFactory;
//import org.apache.geode.cache.client.ClientRegionShortcut;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
//import org.springframework.data.gemfire.CacheFactoryBean;
//import org.springframework.data.gemfire.LocalRegionFactoryBean;
//import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
//import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
//import org.springframework.data.gemfire.config.annotation.ClientCacheApplication.Locator;
//import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
//import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
//import org.springframework.data.gemfire.mapping.annotation.ClientRegion;
//import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
//import org.springframework.geode.config.annotation.EnableClusterAware;

@Configuration
//@ClientCacheApplication(locators = {
//        @ClientCacheApplication.Locator(host = "10.0.0.64", port = 10334)
//})
//@EnableEntityDefinedRegions(basePackageClasses = Person.class,
//        clientRegionShortcut = ClientRegionShortcut.PROXY)
//@EnableEntityDefinedRegions(basePackageClasses = { ObjectRegistry.class} )
//@EnableGemfireRepositories(basePackages
//		  = "com.pd.benchmark.geode")
//@EnableAutoConfiguration
public class GemFireConfiguration {
//    @Autowired
//    PersonRepository personRepository;
    
//    @Bean
//   Properties gemfireProperties() {
//        Properties gemfireProperties = new Properties();
//        gemfireProperties.setProperty("name","BenchmarkGeodeApplication");
//        gemfireProperties.setProperty("mcast-port", "0");
////        gemfireProperties.setProperty("log-level", "config");
//        
////        gemfireProperties.setProperty("spring.data.gemfire.locators", "10.0.0.64[10334]");
//        return gemfireProperties;
//    }
//
//    @Bean
//    CacheFactoryBean gemfireCache() {
//        CacheFactoryBean gemfireCache = new CacheFactoryBean();
//        gemfireCache.setClose(true);
//        gemfireCache.setProperties(gemfireProperties());
//        return gemfireCache;
//    }

    @Bean 
    HijackedAroundMethod getHijackedAroundMethod() {
    	return new HijackedAroundMethod();
    }
    
//    @Bean(name="person")
//    ClientRegionFactoryBean<String, Person> getPerson(final ClientCache cache) {
//        ClientRegionFactoryBean<String, Person> personRegion = new  ClientRegionFactoryBean<String, Person>();
//        personRegion.setCache(cache);
//        personRegion.setName("person");
//        // ...
//        return personRegion;
//    }
}