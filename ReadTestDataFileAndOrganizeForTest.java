package com.pd.benchmark.dataobjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadTestDataFileAndOrganizeForTest {

	private List<PersonRecord> rawPersonTestData = new ArrayList<PersonRecord>();
	private int sizeActive20Percent;
	private List<Integer> multipliedPersons = new ArrayList<Integer>();
	private String[] cags = new String[145360];
	private static final String DISTINCT_CAG_FILE = "distinct_cag.csv.txt"; 
	public int numberTestDataItems = BenchmarkConstants.TARGET_GENERATE;
	

	
	public String[] getCags() {
		return cags;
	}

	public void setCags(String[] cags) {
		this.cags = cags;
	}

	public List<PersonRecord> getRawPersonTestData() {
		return rawPersonTestData;
	}

	public void setRawPersonTestData(List<PersonRecord> rawPersonTestData) {
		this.rawPersonTestData = rawPersonTestData;
	}

	public List<Integer> getMultipliedPersons() {
		return multipliedPersons;
	}

	public void setMultipliedPersons(List<Integer> multipliedPersons) {
		this.multipliedPersons = multipliedPersons;
	}

	public void doIt() {
		readCags();
		readCompletedPersonsFile();
		buildMultipledPersons();
	}
	
	private void readCags() {
      try {
          File file = new File(BenchmarkConstants.COMPLETED_PERSONS_FOLDER
 	 			 +DISTINCT_CAG_FILE);
     	     	  
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          String line = "";
          int i=0;
          while((line = br.readLine()) != null) {
        	 cags[i]=line; 
        	 i++;
             
          }
          br.close();
          } catch(IOException ioe) {
             ioe.printStackTrace();
 		}
	}
	
	private void buildMultipledPersons() {
		if(RuntimeIndicator.getRuntimeIndicator().runtimeEnvironment.equalsIgnoreCase(BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE)) {
			numberTestDataItems = BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_INSERT_COUNT;
		}
		sizeActive20Percent = (int) (.2*(double)numberTestDataItems);
		int active20PercentThreshold = (numberTestDataItems-sizeActive20Percent);
		int i = 0;
		Iterator<PersonRecord> iterator = rawPersonTestData.iterator();
		while(iterator.hasNext()) {
			PersonRecord personRecord = iterator.next();
			i++;
			if(i > active20PercentThreshold) {
				int j=0;
				while(j<BenchmarkConstants.MULTIPLIER_ACTIVE_20_PERCENT) {
					multipliedPersons.add(personRecord.getPersonId());
					j++;
				} 
			}else {
				multipliedPersons.add(personRecord.getPersonId());
			}
		}
	}
	
	private void readCompletedPersonsFile() {
	   //log
        File file;
      System.out.println(BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE);
      System.out.println();
      try {
         file = new File(BenchmarkConstants.COMPLETED_PERSONS_FOLDER
	 			 +BenchmarkConstants.COMPLETED_PERSONS_FILE);
    	     	  
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);
         String line = "";
         String[] tempArr;
         while((line = br.readLine()) != null) {
            tempArr = line.split(BenchmarkConstants.DELIMITER);
            PersonRecord personRecord = new PersonRecord(tempArr,cags);
            rawPersonTestData.add(personRecord);
            
         }
         br.close();
         } catch(IOException ioe) {
            ioe.printStackTrace();
		}
   	}	

	
}
