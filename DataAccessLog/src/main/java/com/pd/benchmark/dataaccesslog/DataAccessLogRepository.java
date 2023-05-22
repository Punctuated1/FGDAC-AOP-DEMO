package com.pd.benchmark.dataaccesslog;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pd.benchmark.fgacdataobjects.DataAccessContext;
import com.pd.benchmark.mongodb.dataobjects.DataAccessLog;

public interface DataAccessLogRepository extends MongoRepository<DataAccessContext, Integer> {

}
