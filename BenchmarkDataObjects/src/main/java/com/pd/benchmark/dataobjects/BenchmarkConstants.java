package com.pd.benchmark.dataobjects;

import java.time.format.DateTimeFormatter;

public final class BenchmarkConstants {
	public static final int NUMBER_FIRST_NAMES = 1000; 
	public static final int TARGET_GENERATE = 100000;
	public static final String GENDER_MALE = "Male";
	public static final String GENDER_FEMALE = "Female";
	public static final int MULTIPLIER_ACTIVE_20_PERCENT = 16;
	public static final String BENCHMARK_TARGET_REDIS = "Redis";
	public static final String BENCHMARK_TARGET_MONGO = "Mongo";
	public static final String BENCHMARK_TARGET_POSTGRES = "Postgres";
	public static final String BENCHMARK_ENVIRONMENT_UBUNTU = "Ubuntu";
	public static final String BENCHMARK_ENVIRONMENT_ECLIPSE = "Eclipse";
	public static final int BENCHMARK_ENVIRONMENT_ECLIPSE_INSERT_COUNT = 100000;
	public static final int BENCHMARK_ENVIRONMENT_ECLIPSE_NUMBER_SETS = 1;
	public static final int BENCHMARK_GENERATE_MULTIPLIER = 1;
	
	
	public static final String  BENCHMARK_STATISTICS_OP_TYPE_INSERTS = "Inserts";
	public static final String  BENCHMARK_STATISTICS_OP_TYPE_SELECTS = "Selects";
	public static final String  BENCHMARK_STATISTICS_OP_TYPE_UPDATES = "Updates";
	public static final String  BENCHMARK_STATISTICS_FILE_PREFIX = "PG-BM-SummaryStatistics-";
	public static final String  BENCHMARK_STATISTICS_FILE_DETAIL_PREFIX = "PG-BM-DetailStats-";
	public static final String 	LOG_TOPIC_NAME = "accessLog"; 

			
	public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String DELIMITER = ",";
	public static final String LAST_NAME_FILE = "D:\\Documents\\Punctuated-Dev\\POCWork\\CommonLastNames-5000.csv";
	public static final String FIRST_NAME_FILE_MALE = "D:\\Documents\\Punctuated-Dev\\POCWork\\CommonMaleNames-1000.csv";
	public static final String FIRST_NAME_FILE_FEMALE = "D:\\Documents\\Punctuated-Dev\\POCWork\\CommonFemaleNames-1000.csv";
	public static final String COMPLETED_PERSONS_FILE = "CommonPersons-1000000.csv";
//	public static final String COMPLETED_PERSONS_FOLDER = "D:\\\\Documents\\\\Punctuated-Dev\\\\POCWork\\\\";
	public static final String COMPLETED_PERSONS_FOLDER = "/home/harry/Eclipse/data/";
	public static final String[] CLIENT_NAMES = {"BCBSAL", "BCBSFL", "HCSC"};

}
