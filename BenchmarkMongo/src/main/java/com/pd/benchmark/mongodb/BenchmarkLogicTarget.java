package com.pd.benchmark.mongodb;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.dataobjects.PersonRecord;
import com.pd.benchmark.dataobjects.StatisticRecord;

@Service
public class BenchmarkLogicTarget extends BenchMarkLogic{
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	protected void runInserts(Integer setKeyInteger) {
		List<PersonRecord> personRecords = keyedSetsOfPersonsRaw.get(setKeyInteger);
		List<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();
		keyedInsertStatistics.put(setKeyInteger, statisticRecords);
		Iterator<PersonRecord> iterator = personRecords.iterator();
		int setKeyInt=setKeyInteger.intValue()-1;


		long sizeSet=0;
		while(iterator.hasNext()) {
			PersonRecord personRecord = iterator.next();
//			StatisticRecord statisticRecord = new StatisticRecord();
//			statisticRecord.setOperationType(BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_INSERTS);
//			statisticRecord.setSetKeyInteger(setKeyInteger);
//			statisticRecord.setStartTime(LocalDateTime.now());
			long x = 0;
			while(x < BenchmarkConstants.BENCHMARK_GENERATE_MULTIPLIER) {
				Person person = new Person(personRecord);
				long personId = personRecord.getPersonId().longValue()+x*vlim;
				person.setId(personId);
				LocalDateTime startTime = LocalDateTime.now();
				personRepository.save(person);
				LocalDateTime endTime = LocalDateTime.now();
				long durationOfOperation = Duration.between(startTime, endTime).toNanos();
				totalDurationOpInsert[setKeyInt] = totalDurationOpInsert[setKeyInt]+durationOfOperation;
				x++;
				int sizeRow = 8+person.getFirstName().length()+person.lastName.length()+person.getGender().length()+10+10+person.getTsCr().toString().length();
				sizeSet=sizeSet+sizeRow;
			}
//			statisticRecord.setEndTime(LocalDateTime.now());
//			statisticRecords.add(statisticRecord);
		}
		totalSize=totalSize+sizeSet;
		System.out.println("Set: "+setKeyInteger.intValue()+" -- SizeSet: "+sizeSet+" -- Average: "+sizeSet/setSize);
		
	}
	
	protected void runSelects(Integer setKeyInteger) {
		int sizeRandomSearchIndex = setSize;
//		int sizeRandomSearchIndex = setSize*4;
		System.out.println("Select Set Size: "+sizeRandomSearchIndex);
		List<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();
		keyedSelectStatistics.put(setKeyInteger, statisticRecords);
		int i = 0;
		int j=(setKeyInteger.intValue()-1)*sizeRandomSearchIndex;
		i=j;
		System.out.println("Set: "+setKeyInteger+" -- select i: "+i+" -- j: "+j+" -- j+set size: "+ (j+sizeRandomSearchIndex));
		while(i<(j+sizeRandomSearchIndex)) {
			i++;
			int targetIndex = (int)(Math.random()*(double)sizeRandomSearchIndex);
			Integer personId = multipliedPersons.get(targetIndex);
			long x=(long)(Math.random()*(double)BenchmarkConstants.BENCHMARK_GENERATE_MULTIPLIER);
			long intPersonId = personId.longValue()+x*(int)vlim;
//			System.out.println("x: "+x+"  -- intPersonId: "+intPersonId+"  -- personId: "+personId.intValue());
			StatisticRecord statisticRecord = new StatisticRecord();
			statisticRecord.setOperationType(BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_SELECTS);
			statisticRecord.setSetKeyInteger(setKeyInteger);
			statisticRecord.setStartTime(LocalDateTime.now());
			Person person = personRepository.findById(intPersonId).get();
			System.out.println("RunSelects id: "+person.getId()
					+ " -- Name: "+person.getFirstName()+" "+person.getLastName()
					+ "  dob: "+person.getDateOfBirth()
					+ "  gender: "+person.getGender()
					+ "  name Updt: "+person.getNameUp()
					+ "  ts_cr: "+person.getTsCr()
					+ "  ts_up: "+person.getTsup()
					+ "  cag: "+person.getCag()
					);
			statisticRecord.setEndTime(LocalDateTime.now());
			statisticRecords.add(statisticRecord);
		}

	}
	
	protected void runUpdates(Integer setKeyInteger) {
		int sizeRandomSearchIndex = setSize;
		System.out.println("Update Set Size: "+sizeRandomSearchIndex);
		List<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();
		keyedUpdatetStatistics.put(setKeyInteger, statisticRecords);
		int i = 0;
		int j=(setKeyInteger.intValue()-1)*sizeRandomSearchIndex;
		i=j;
		System.out.println("Set: "+setKeyInteger+" -- update i: "+i+" -- j: "+j+" -- j+set size: "+ (j+sizeRandomSearchIndex));
		while(i<(j+sizeRandomSearchIndex)) {
			i++;
			int targetIndex = (int)(Math.random()*(double)sizeRandomSearchIndex);
			Integer personId = multipliedPersons.get(targetIndex);
			long x=(long)(Math.random()*(double)BenchmarkConstants.BENCHMARK_GENERATE_MULTIPLIER);
			long intPersonId = personId.longValue()+x*(int)vlim;
			
			StatisticRecord statisticRecord = new StatisticRecord();
			statisticRecord.setOperationType(BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_UPDATES);
			statisticRecord.setSetKeyInteger(setKeyInteger);
			statisticRecord.setStartTime(LocalDateTime.now());
			Person person = personRepository.findById(intPersonId).get();
//			System.out.println("RunUpdates id: "+person.getId()
//			+ " -- Name: "+person.getFirstName()+" "+person.getLastName()
//			+ "  dob: "+person.getDateOfBirth()
//			+ "  name Updt: "+person.getNameUp()
//			+ "  ts_cr: "+person.getTsCr()
//			+ "  ts_up: "+person.getTsup()
//			+ "  clientName: "+person.getClientName()
//			);
			person.setTsup(LocalDateTime.now());
			person.setNameUp(LocalDateTime.now().toString());
//			personRepository.save(person);
			statisticRecord.setEndTime(LocalDateTime.now());
			statisticRecords.add(statisticRecord);
		}
		
	}	
	
	public List<Person> findByLastName(String lastName){
		long start = System.nanoTime();
		List<Person> persons = personRepository.findByLastName(lastName);
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		if(persons != null ) {
//			Iterator<Person> iterator = persons.iterator();
//			while(iterator.hasNext()) {
//				Person person = iterator.next();
//				System.out.println(" id: "+person.getId()
//				+" first name: "+person.getFirstName()
//				+"  last name: "+person.getLastName()
//				+"     gender: "+person.getGender()
//				+"        dob: "+person.getDateOfBirth()
//				+" clientName: "+person.getClientName()				
//				);
//			}

		}
		return persons;
	}
	
	
	public List<Person> findByLastNameTemplate(String lastName){
		long start = System.nanoTime();
		mongoTemplate.dropCollection(Person.class);
		Query query = new Query();
		query.addCriteria(Criteria.where("lastName").is(lastName));
		List<Person> persons = mongoTemplate.find(query,Person.class,"person");
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
//		if(persons != null ) {
//			Iterator<Person> iterator = persons.iterator();
//			while(iterator.hasNext()) {
//				Person person = iterator.next();
//				System.out.println(" id: "+person.getId()
//				+" first name: "+person.getFirstName()
//				+"  last name: "+person.getLastName()
//				+"     gender: "+person.getGender()
//				+"        dob: "+person.getDateOfBirth()
//				+" clientName: "+person.getClientName()				
//				);
//			}

//		}
		return persons;
	}
	
}
