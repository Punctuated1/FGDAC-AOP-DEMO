package com.pd.benchmark.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.dataobjects.PersonRecord;
import com.pd.benchmark.dataobjects.StatisticRecord;


@Service
public class BenchmarkLogicTarget extends BenchMarkLogic{
	@Autowired
	private PersonRepository personRepository;
	
	protected void runInserts(Integer setKeyInteger) {
		List<PersonRecord> personRecords = keyedSetsOfPersonsRaw.get(setKeyInteger);
		List<StatisticRecord> statisticRecords = new ArrayList<StatisticRecord>();
		keyedInsertStatistics.put(setKeyInteger, statisticRecords);
		Iterator<PersonRecord> iterator = personRecords.iterator();
		while(iterator.hasNext()) {
			PersonRecord personRecord = iterator.next();
			PersonPg person = new PersonPg(personRecord);
			StatisticRecord statisticRecord = new StatisticRecord();
			statisticRecord.setOperationType(BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_INSERTS);
			statisticRecord.setSetKeyInteger(setKeyInteger);

			statisticRecord.setStartTime(LocalDateTime.now());
			savePerson(person);
			statisticRecord.setEndTime(LocalDateTime.now());
			statisticRecords.add(statisticRecord);
		}
		
	}
	

	private void savePerson(PersonPg person) {
		person.setIsInsert(true);
		personRepository.save(person);
	}

	private PersonPg findPerson(Integer personId) {
		return personRepository.findById(personId).get();
	}

	private void updatePerson(Integer personId) {
		PersonPg person = personRepository.findById(personId).get();
		person.setTsup(LocalDateTime.now());
		person.setNameUp(LocalDateTime.now().toString());
		person.setIsInsert(false);
		personRepository.save(person);
	}

	protected void runSelects(Integer setKeyInteger) {
		int sizeRandomSearchIndex = setSize*4;
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
			StatisticRecord statisticRecord = new StatisticRecord();
			statisticRecord.setOperationType(BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_SELECTS);
			statisticRecord.setSetKeyInteger(setKeyInteger);
			statisticRecord.setStartTime(LocalDateTime.now());
			PersonPg person = findPerson(personId);
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
			
			StatisticRecord statisticRecord = new StatisticRecord();
			statisticRecord.setOperationType(BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_UPDATES);
			statisticRecord.setSetKeyInteger(setKeyInteger);
			statisticRecord.setStartTime(LocalDateTime.now());
			updatePerson(personId);
			statisticRecord.setEndTime(LocalDateTime.now());
			statisticRecords.add(statisticRecord);
		}
		
	}	
	public List<PersonPg> findByLastName(String lastName){
		long start = System.nanoTime();
		List<PersonPg> persons = personRepository.findByLastName(lastName);
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		return persons;
	}
	

	
}
