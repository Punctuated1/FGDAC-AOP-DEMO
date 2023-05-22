package com.pd.benchmark.dataaccesslog;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.message.ElectLeadersRequestData.TopicPartitions;
import org.apache.kafka.common.message.UpdateMetadataRequestDataJsonConverter.UpdateMetadataPartitionStateJsonConverter;
import org.apache.kafka.common.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.fgacdataobjects.DataAccessContext;
import com.pd.benchmark.mongodb.dataobjects.DataAccessLog;

@Service
public class DataAccessLogService{
	@Autowired
	private DataAccessLogRepository dataAccessLogRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	private int consumerCount[] = {0,0,0,0,0,0,0,0};
	private int producerCount = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessLogService.class);
    private ObjectMapper mapper = new ObjectMapper();

	

	protected String populateDataAccessLog() {
		mapper.registerModule(new JavaTimeModule());
		// create instance for properties to access consumer configs   
		Properties props = new Properties();

	    props.put("bootstrap.servers", "10.0.0.64:9092");

	    props.put("group.id", "benchmark-kafka");
	    props.put("key.deserializer",
	     "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("value.deserializer",
	     "org.apache.kafka.common.serialization.StringDeserializer");
	    props.put("auto-offset-reset", "latest");
//	    props.put("max.poll.records", 200);
	    
	    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
	    
	    consumer.subscribe(Collections.singletonList("accessLog"));
 
	    System.out.println("Subscribed to topic: accessLog");
	    Duration pollDuration = Duration.ofMillis(100);

	    LocalDateTime sixtyMinutesFuture = LocalDateTime.now().plusMinutes(120000);
	    System.out.println(sixtyMinutesFuture.toString());
	    System.out.println("Now: "+LocalDateTime.now().toString());
	    try {
	    while(LocalDateTime.now().isBefore(sixtyMinutesFuture)) {
	    	ConsumerRecords<String, String> records = consumer. poll(pollDuration);
    		Iterator<ConsumerRecord<String,String>> iterator = records.iterator();
//    		System.out.println("before While");
    		while(iterator.hasNext()) {
//        		System.out.println("in While");
    			ConsumerRecord<String, String> record = iterator.next();
//    			String decompressedRecordValue = record.value();
    			String decompressedRecordValue = CompressionUtil.decompressB64(record.value());
    			DataAccessContext accessLog = mapper.readValue(decompressedRecordValue,DataAccessContext.class);
    			accessLog.setId(sequenceGeneratorService.generateSequence(DataAccessContext.SEQUENCE_NAME));
    			dataAccessLogRepository.save(accessLog);
    		}
//    		System.out.println("after While");
	    }
	    consumer.close();
	    } catch(Exception ex) {
	    	ex.printStackTrace();
	    }finally {
	    	consumer.close();
	    }
	    
	    return "Done populating Data Access Log";
	}

	
}
