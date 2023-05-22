package com.pd.objectregistry;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableJpaRepositories("com.pd.objectregistry")
@EntityScan("com.pd.objectregistry")

public class ObjectRegistryService {
	@Autowired
	private ObjectRegistryRepository objectRegistryRepository;

	public List<ObjectRegistry> getAllObjectRegistryEntries(){
		List<ObjectRegistry> objectRegistries = objectRegistryRepository.findAll();
		return objectRegistries;
	}
}
