package co.pd.datagen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.dataobjects.FirstNameInfo;
import com.pd.benchmark.dataobjects.PersonRaw;
import com.pd.benchmark.dataobjects.PersonRecord;

public class LogicGenDataToFileFromFiles {
	   private List<PersonRaw> rawPersons = new ArrayList<PersonRaw>();
	   private List<PersonRecord> expandedPersons = new ArrayList<PersonRecord>();
	   private List<PersonRecord> completedPersons = new ArrayList<PersonRecord>();
	   private List<FirstNameInfo> rawFirstNameMale;
	   private List<FirstNameInfo> rawFirstNameFemale;
	   private double dateRandomizeSeed = 365*99;
	   private int totalGenerated=0;
	   
	   public void doit() {
		      rawPersons = readLastNameFile(BenchmarkConstants.LAST_NAME_FILE);
		      expandedPersons = expandLastNameList();
		      rawFirstNameMale = readFirstNameFile(BenchmarkConstants.FIRST_NAME_FILE_MALE,BenchmarkConstants.GENDER_MALE);
		      rawFirstNameFemale = readFirstNameFile(BenchmarkConstants.FIRST_NAME_FILE_FEMALE , BenchmarkConstants.GENDER_FEMALE);
		      completedPersons = applyFirstNameGenderDobToExpandedPerson();
		      completedPersons = applyFirstNameGenderDobToExpandedPerson();
		      writeToFile();
	   }
	   
	   private void writeToFile(){
		   System.out.println("remaining Expanded Persons: "+expandedPersons.size());
		   System.out.println("Total Generated: "+totalGenerated);
		   try {
			   BufferedWriter completedPersonWriter = new BufferedWriter(new FileWriter(BenchmarkConstants.COMPLETED_PERSONS_FILE));
		    	Iterator<PersonRecord> iterator = completedPersons.iterator();
		    	while(iterator.hasNext()) {
		    		  PersonRecord personRecord = iterator.next();
		    		  completedPersonWriter.write(convertPersonRecordToString(personRecord)); 
		    		  
		    	}
		    	  completedPersonWriter.close();
		      } catch(IOException ioe) {
		            ioe.printStackTrace();
			}
	   }

	   private String convertPersonRecordToString(PersonRecord personRecord) {
		   return personRecord.getPersonId().toString()+","
			   + "\""+personRecord.getFirstName()+"\","
			   + "\""+personRecord.getLastName()+"\","
			   + "\""+personRecord.getGender()+"\","
			   + "\""+personRecord.getDateOfBirth()+"\"\n";
	   }
	   
	   private List<PersonRecord> applyFirstNameGenderDobToExpandedPerson(){
		   int i=0;
		   while(i<BenchmarkConstants.NUMBER_FIRST_NAMES && totalGenerated < BenchmarkConstants.TARGET_GENERATE) {
			   FirstNameInfo firstNameInfoMale = rawFirstNameMale.get(i);
			   FirstNameInfo firstNameInfoFemale = rawFirstNameFemale.get(i);
			   processFirstNameInfo(firstNameInfoMale);
			   processFirstNameInfo(firstNameInfoFemale);
			   i++;
		   }
		   return completedPersons;
	   }

	   private void processFirstNameInfo(FirstNameInfo firstNameInfo) {
		   int i=0;
		   while(i<firstNameInfo.getNumberToGen() && totalGenerated<BenchmarkConstants.TARGET_GENERATE) {
			   int numberExpandedPersons = expandedPersons.size();
			   int targetIndex = (int)(Math.random()*(double)numberExpandedPersons);
			   PersonRecord personRecord = expandedPersons.get(targetIndex);
			   personRecord.setFirstName(firstNameInfo.getFirstName());
			   personRecord.setGender(firstNameInfo.getGender());
			   completedPersons.add(personRecord);
			   expandedPersons.remove(targetIndex);
			   i++;
			   totalGenerated++;

		   }
	   }

	   
	   private List<PersonRecord> expandLastNameList(){
		   Iterator<PersonRaw> iterator =rawPersons.iterator();
		   int ttl=0;
		   while(iterator.hasNext()) {
			   PersonRaw personRaw = iterator.next();
			   int i=0;
			   while(i<personRaw.getNumberToGen()) {
				   i++;
				   ttl++;
				   PersonRecord personRecord = new PersonRecord();
				   personRecord.setLastName(personRaw.getLastName());
				   personRecord.setPersonId(1000000+personRaw.getRank()*20000+i);
				   double randomItem =  Math.random();
				   long daysBackToDob = (long)(randomItem * dateRandomizeSeed);
				   personRecord.setDateOfBirth(LocalDate.now().minusDays(daysBackToDob));
				   expandedPersons.add(personRecord);
//				   System.out.println("Org Rank: "+personRaw.getFileRank()+"  Rank: "+personRaw.getRank()+"  Id: "+personRecord.getPersonId()+"  LastName: "+personRecord.getLastName()+"  Dob: "+personRecord.getDateOfBirth());
			   }
			   
		   }
		   System.out.println("Total: "+ttl);
		   return expandedPersons;
	   }
	   
	   private List<PersonRaw> readLastNameFile(String csvFile) {
		   //log
		   try {
	         File file = new File(csvFile);
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         String[] tempArr;
	         int i=0;
	         while((line = br.readLine()) != null) {
	            tempArr = line.split(BenchmarkConstants.DELIMITER);
	            String rank  = tempArr[0];
	            String lName = tempArr[1];
	            String numGen = tempArr[10];
	            
	            PersonRaw personRaw = new PersonRaw();
	            personRaw.setLastName(lName);
	            personRaw.setNumberToGen(Integer.valueOf(numGen));
	            personRaw.setFileRank(rank);
	            i++;
	            personRaw.setRank(i);
	            rawPersons.add(personRaw);
	            br.close();
	         }
	         } catch(IOException ioe) {
	            ioe.printStackTrace();
			}
	      return rawPersons;
	   	}	

	   private List<FirstNameInfo> readFirstNameFile(String csvFile, String gender) {
		   //log
		   List<FirstNameInfo> rawFirstNames = new ArrayList<FirstNameInfo>();
	      try {
	         File file = new File(csvFile);
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         String[] tempArr;
	         int i=0;
	         while((line = br.readLine()) != null) {
	            tempArr = line.split(BenchmarkConstants.DELIMITER);
	            String rank  = tempArr[0];
	            String fName = tempArr[1];
	            String numGen = tempArr[3];
	            
	            FirstNameInfo firstNameRaw = new FirstNameInfo();
	            firstNameRaw.setFirstName(fName);
	            firstNameRaw.setNumberToGen(Integer.valueOf(numGen));
	            firstNameRaw.setFileRank(rank);
	            firstNameRaw.setGender(gender);
	            i++;
	            firstNameRaw.setRank(i);
	            rawFirstNames.add(firstNameRaw);
	            br.close();
	         }
	         } catch(IOException ioe) {
	            ioe.printStackTrace();
			}
	      return rawFirstNames;
	   	}	


}
