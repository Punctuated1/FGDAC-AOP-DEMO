package com.pd.benchmark.jpa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.dataobjects.PersonRecord;
import com.pd.benchmark.dataobjects.ReadTestDataFileAndOrganizeForTest;
import com.pd.benchmark.dataobjects.RuntimeIndicator;
import com.pd.benchmark.dataobjects.StatisticRecord;
import com.pd.benchmark.dataobjects.SummaryStatisticsRecord;

public abstract class BenchMarkLogic implements BenchMarkLogicInterface {
	protected List<PersonRecord> rawPersonTestData;
	protected List<Integer> multipliedPersons;
	protected Map<Integer, List<StatisticRecord> > keyedInsertStatistics = new HashMap<Integer, List<StatisticRecord>>();
	protected Map<Integer, List<StatisticRecord> >  keyedSelectStatistics = new HashMap<Integer, List<StatisticRecord>>();
	protected Map<Integer, List<StatisticRecord> >  keyedUpdatetStatistics = new HashMap<Integer, List<StatisticRecord>>();
	private String fileName;
	private String fileNameDetail;

	
	protected int numberTestDataItems = BenchmarkConstants.TARGET_GENERATE;
	protected int setSize;
	protected Map<Integer, List<PersonRecord>> keyedSetsOfPersonsRaw = new HashMap<Integer, List<PersonRecord>>();
	protected Map<Integer, List<SummaryStatisticsRecord> > keyedSummaryStatisticsRecordLists 
		= new HashMap<Integer, List<SummaryStatisticsRecord>>();
	
	public ReadTestDataFileAndOrganizeForTest testDataSource = new ReadTestDataFileAndOrganizeForTest();

	private void initStatsOutputFiles() {
		if(RuntimeIndicator.getRuntimeIndicator().runtimeEnvironment.equalsIgnoreCase(BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE)) {
  	         fileName=BenchmarkConstants.COMPLETED_PERSONS_FOLDER
		 			 +BenchmarkConstants.BENCHMARK_STATISTICS_FILE_PREFIX;
  	         fileNameDetail = BenchmarkConstants.COMPLETED_PERSONS_FOLDER
		 			 +BenchmarkConstants.BENCHMARK_STATISTICS_FILE_DETAIL_PREFIX;
		} else {
	         fileName = BenchmarkConstants.BENCHMARK_STATISTICS_FILE_PREFIX;
	         fileNameDetail = BenchmarkConstants.BENCHMARK_STATISTICS_FILE_DETAIL_PREFIX;
		}
		fileName = fileName+LocalDate.now()+LocalTime.now().getHour()+LocalTime.now().getMinute()+LocalTime.now().getSecond()+".txt";
		fileNameDetail = fileNameDetail+LocalDate.now()+LocalTime.now().getHour()+LocalTime.now().getMinute()+LocalTime.now().getSecond()+".csv";
	}

	public String doInserts(String benchmarkTarget, int setKeyId) {
		Integer setKeyInteger = Integer.valueOf(setKeyId);
		if(RuntimeIndicator.getRuntimeIndicator().runtimeEnvironment.equalsIgnoreCase(BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE)) {
			numberTestDataItems = BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_INSERT_COUNT;
		}
		String statusMsg = "Set: "+setKeyInteger+" -- doInserts: "+benchmarkTarget+" start inserts ts:"+LocalDateTime.now();
		System.out.println(statusMsg);
		runInserts(setKeyInteger);
		statusMsg =  "doInserts: "+benchmarkTarget+"   end inserts ts:"+LocalDateTime.now();
		System.out.println(statusMsg);
		doSummaryStatistics(keyedInsertStatistics,BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_INSERTS, setKeyInteger);
		
		String responseInsert = "Set: "+setKeyInteger+" -- doInserts completed for target: "+benchmarkTarget+" -- Inserts Completed: "+setSize;
		System.out.println(responseInsert);
		return responseInsert;
	}

	public String doReadUpdate(String benchmarkTarget, int setKeyId) {
		
		Integer setKeyInteger = Integer.valueOf(setKeyId);
		doSelUpdt(benchmarkTarget,"partial", setKeyInteger);
		String responseSelectUpdate = "Set: "+setKeyInteger+" -- doSelectUpdate completed for target: "+benchmarkTarget+" -- Selects Completed: "+setSize*4+ " -- Updates Completed: "+setSize;
		System.out.println(responseSelectUpdate);
		return responseSelectUpdate;

	
	}
	

	public void doReportSummaryStatistics() {
		reportSummaryStats();
		
	}
	
	private void doSelUpdt(String benchmarkTarget, String opMode, Integer setKeyInteger) {
		String statusMsg = "Set: "+setKeyInteger+" -- "+opMode+" "+benchmarkTarget+" start selects ts:"+LocalDateTime.now();
		System.out.println(statusMsg);
		runSelects(setKeyInteger);
		statusMsg = "Set: "+setKeyInteger+" -- "+opMode+" "+benchmarkTarget+"   end selects ts:"+LocalDateTime.now();
		System.out.println(statusMsg);
		doSummaryStatistics(keyedSelectStatistics,BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_SELECTS,setKeyInteger);

		statusMsg = "Set: "+setKeyInteger+" -- "+opMode+" "+benchmarkTarget+" start updates ts:"+LocalDateTime.now();
		System.out.println(statusMsg);
		runUpdates(setKeyInteger);
		statusMsg = opMode+" "+benchmarkTarget+"   end updates ts:"+LocalDateTime.now();
		System.out.println(statusMsg);
		doSummaryStatistics(keyedUpdatetStatistics,BenchmarkConstants.BENCHMARK_STATISTICS_OP_TYPE_UPDATES,setKeyInteger);
	}

	public String setup() {
		System.out.println("Setup Start: "+LocalDateTime.now());
		initStatsOutputFiles();
		if(RuntimeIndicator.getRuntimeIndicator().runtimeEnvironment.equalsIgnoreCase(BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE)) {
			numberTestDataItems = BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_INSERT_COUNT;
		}
		testDataSource.doIt();
		rawPersonTestData = testDataSource.getRawPersonTestData();
		multipliedPersons = testDataSource.getMultipliedPersons();
		distributeSets();
		String setupResponse = "Setup Completed: "+LocalDateTime.now();
		return setupResponse;

	}
	
	private void distributeSets() {
		int setKey=0;
		setSize=numberTestDataItems/BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_NUMBER_SETS;
		Iterator<PersonRecord> iterator = rawPersonTestData.iterator();
		while(setKey<BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_NUMBER_SETS) {
			setKey++;
			Integer setKeyInteger = Integer.valueOf(setKey);
			int i=0;
			List<PersonRecord> personRecords = new ArrayList<PersonRecord>();
			keyedSetsOfPersonsRaw.put(setKeyInteger, personRecords);
			List<SummaryStatisticsRecord> summaryStatisticsRecords = new ArrayList<SummaryStatisticsRecord>();
			keyedSummaryStatisticsRecordLists.put(setKeyInteger, summaryStatisticsRecords);
			while(i<setSize && iterator.hasNext()) {
				PersonRecord personRecord = iterator.next();
				personRecords.add(personRecord);
				i++;
			}
		}
	}
	
	private void doSummaryStatistics(Map<Integer, List<StatisticRecord>> keyedStatisticRecords,String operationType, Integer setKeyInteger) {
		System.out.println(" -- Set: "+setKeyInteger+" -- operationType: "+operationType+" -- start summary. "+LocalDateTime.now());
		List<SummaryStatisticsRecord> summaryStatisticsRecords = keyedSummaryStatisticsRecordLists.get(setKeyInteger);
		List<StatisticRecord> statisticRecords = keyedStatisticRecords.get(setKeyInteger);
		
		int numberOperations = statisticRecords.size();
		
		SummaryStatisticsRecord summaryStatisticsRecord = 
				computeSummaryStatistics(statisticRecords, numberOperations, operationType, setKeyInteger);
		
		summaryStatisticsRecords.add(summaryStatisticsRecord);
		String statusMsg = " -- Set: "+setKeyInteger+" -- operationType: "+operationType+" --  end summary "+LocalDateTime.now()+" number statistic records: "+statisticRecords.size();
		System.out.println(statusMsg);
	}

	private SummaryStatisticsRecord computeSummaryStatistics(List<StatisticRecord> statisticsRecords, long numberOperations, String opertionType, Integer setKeyInteger) {
		SummaryStatisticsRecord summaryStatisticsRecord = new SummaryStatisticsRecord();
		summaryStatisticsRecord.setNumberOperations(numberOperations);
		summaryStatisticsRecord.setOperationType(opertionType);
		summaryStatisticsRecord.setSetKeyInteger(setKeyInteger);
		long totalDuration = 0;
		long minDuration = 99999999999999999L;
		long maxDuration = 0L;
		double totalSquares = 0.0;
		Iterator<StatisticRecord> iterator = statisticsRecords.iterator();
		int i=0;
		while(iterator.hasNext()) {
			i++;
			StatisticRecord statisticRecord = iterator.next();
			totalDuration = totalDuration + statisticRecord.getDurationOfOperation();
		
			if(i==1 || minDuration>statisticRecord.getDurationOfOperation()) {
				minDuration = statisticRecord.getDurationOfOperation();
			}
			if(i==1 || maxDuration < statisticRecord.getDurationOfOperation() ) {
				maxDuration = statisticRecord.getDurationOfOperation();
			}
//			System.out.println("Start: "+statisticRecord.getStartTime()
//					+" -- End: "+statisticRecord.getEndTime()
//					+" -- Duration: "+String.format("%,d",statisticRecord.getDurationOfOperation())
//					+" -- Max Duration: "+String.format("%,d",maxDuration)
//					+" -- Min Duration: "+String.format("%,d",minDuration));
		}
		summaryStatisticsRecord.setMaxDuration(maxDuration);
		summaryStatisticsRecord.setMinDuration(minDuration);
		summaryStatisticsRecord.setTotalDurationNanos(totalDuration);
		double meanDuration = ((double)(totalDuration) / (double) (numberOperations));
		summaryStatisticsRecord.setMeanDuration(meanDuration);
		Iterator<StatisticRecord> iterator2 = statisticsRecords.iterator();
		while(iterator2.hasNext()) {
			StatisticRecord statisticRecord = iterator2.next();
			double distanceToMean = (((double)(statisticRecord.getDurationOfOperation())-meanDuration));
			totalSquares = totalSquares + (distanceToMean*distanceToMean); 
		}
		double stdDeviation=Math.sqrt( totalSquares/(double)(numberOperations-1) );
		summaryStatisticsRecord.setStandardDeviationDuration(stdDeviation);
		return summaryStatisticsRecord;
	}
	
	public String reportStats() {
		System.out.println("Report Start: "+LocalDateTime.now());
		reportSummaryStats();
		reportDetailStats();
			
		return "Report Complete: "+LocalDateTime.now();
	}

	private void reportSummaryStats() {
		System.out.println("Summary Report Start: "+LocalDateTime.now());
		int setKeyId=0;
			try {
				BufferedWriter  completedStatsWriter
					= new BufferedWriter(new FileWriter(fileName));
				while(setKeyId < BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_NUMBER_SETS) {
					setKeyId++;
					List<SummaryStatisticsRecord> summaryStatisticsRecords = keyedSummaryStatisticsRecordLists.get(setKeyId);
					Iterator<SummaryStatisticsRecord> iterator = summaryStatisticsRecords.iterator();
					while(iterator.hasNext()) {
						SummaryStatisticsRecord summaryStatisticsRecord = iterator.next();
						String outputString = "Set Key: "+summaryStatisticsRecord.getSetKeyInteger()
						+" Operation: "+summaryStatisticsRecord.getOperationType()
						+" Total Duration: "+getStandardNumber(summaryStatisticsRecord.getTotalDurationNanos())
						+" nanos -- Number Operations: "+getStandardNumber(summaryStatisticsRecord.getNumberOperations())
						+" -- Mean: "+getStandardNumber(summaryStatisticsRecord.getMeanDurationNanos())
						+" nanos -- Max: "+getStandardNumber(summaryStatisticsRecord.getMaxDuration())
						+" nanos -- Min: "+getStandardNumber(summaryStatisticsRecord.getMinDuration())
						+" nanos -- Standard Deviation: "+getStandardNumber(+summaryStatisticsRecord.getStandardDeviationDurationNanos())+" nanos";
						completedStatsWriter.write(outputString);
						completedStatsWriter.newLine();
					}
				}
				completedStatsWriter.close();
			} catch(IOException iox) {
				iox.printStackTrace();
			}
			
		System.out.println("Summary Report Complete: "+LocalDateTime.now());
	}

	private void reportDetailStats() {
		System.out.println("Detail Report Start: "+LocalDateTime.now());
			try {
				BufferedWriter  completedStatsWriter
					= new BufferedWriter(new FileWriter(fileNameDetail));
				String headerString = "Operation, SetId, Start TS, End TS, Duration Nanos, Duration, Duration, Duration, Duration, Duration";
				completedStatsWriter.write(headerString);
				completedStatsWriter.newLine();
				
				int opType=0;
				while(opType<3) {
					opType++;
					Map<Integer, List<StatisticRecord>>	keyedStatisticRecords;
					switch(opType) {
					case 1:
						System.out.println("insert");
						keyedStatisticRecords = keyedInsertStatistics;
						break;
					case 2:
						System.out.println("select");
						keyedStatisticRecords = keyedSelectStatistics;
						break;
					default:
						System.out.println("update");
						keyedStatisticRecords = keyedUpdatetStatistics;
					}
					int setKeyId=0;
					while(setKeyId < BenchmarkConstants.BENCHMARK_ENVIRONMENT_ECLIPSE_NUMBER_SETS) {
						setKeyId++;
						List<StatisticRecord> statisticRecords = keyedStatisticRecords.get(setKeyId);
						if(statisticRecords !=null && !statisticRecords.isEmpty()) {
							Iterator<StatisticRecord> iterator = statisticRecords.iterator();
							while(iterator.hasNext()) {
								StatisticRecord statisticRecord = iterator.next();
		
								String outputString = statisticRecord.getOperationType()
								+","+"Set-"+statisticRecord.getSetKeyInteger()
								+","+statisticRecord.getStartTime()
								+","+statisticRecord.getEndTime()
								+","+statisticRecord.getDurationOfOperation()
								+","+statisticRecord.getDurationOfOperation()
								+","+statisticRecord.getDurationOfOperation()
								+","+statisticRecord.getDurationOfOperation()
								+","+statisticRecord.getDurationOfOperation()
								+","+statisticRecord.getDurationOfOperation();
		
								completedStatsWriter.write(outputString);
								completedStatsWriter.newLine();
							}
						}
					}
				}
				completedStatsWriter.close();
			} catch(IOException iox) {
				iox.printStackTrace();
			}

			
		System.out.println("Detail Report Complete: "+LocalDateTime.now());
	}
		
	
	public static String getStandardNumber(long longValue){
		String template="##,###,###,###,##0";
		String blanks  ="..................";
		String numberString = String.format("%,d",longValue);
		if(numberString.length()<template.length()) {
			numberString = blanks.substring(0, template.length()-numberString.length())+numberString;
		}
	    return numberString;
	}
	
	protected abstract void runInserts(Integer setKeyInteger);

	
	protected abstract void runSelects(Integer setKeyInteger);

	
	protected abstract void runUpdates(Integer setKeyInteger);
	

}
