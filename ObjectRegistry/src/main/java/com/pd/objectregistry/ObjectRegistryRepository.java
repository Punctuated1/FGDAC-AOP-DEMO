package com.pd.objectregistry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRegistryRepository extends JpaRepository<ObjectRegistry, String> {

}
