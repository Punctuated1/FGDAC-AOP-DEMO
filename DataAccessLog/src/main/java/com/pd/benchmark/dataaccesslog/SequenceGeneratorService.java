package com.pd.benchmark.dataaccesslog;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

    private MongoOperations mongoOperations;
    private FindAndModifyOptions options = new FindAndModifyOptions(); 
    
    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
    	Query query  = new Query(Criteria.where("_id").is(seqName)); 
    	DatabaseSequence counter = mongoOperations.findAndModify(new Query(Criteria.where("_id").is(seqName)),
          new Update().inc("seq",1), options.returnNew(true).upsert(true),
          DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}