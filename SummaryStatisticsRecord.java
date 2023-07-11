package com.pd.benchmark.dataobjects;

public class SummaryStatisticsRecord {
	private String operationType;
	private Integer setKeyInteger;
	private long numberOperations;
	private long totalDurationNanos;
	private double meanDuration;
	private double standardDeviationDuration;
	private long maxDuration;
	private long minDuration;

	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Integer getSetKeyInteger() {
		return setKeyInteger;
	}
	public void setSetKeyInteger(Integer setKeyInteger) {
		this.setKeyInteger = setKeyInteger;
	}

	public long getNumberOperations() {
		return numberOperations;
	}
	public void setNumberOperations(long numberOperations) {
		this.numberOperations = numberOperations;
	}
	public long getTotalDurationNanos() {
		return totalDurationNanos;
	}
	public void setTotalDurationNanos(long totalDurationNanos) {
		this.totalDurationNanos = totalDurationNanos;
	}
 
	public long getMeanDurationNanos() {
		return (long)meanDuration;
	}
	public double getMeanDuration() {
		return meanDuration;
	}
	public void setMeanDuration(double meanDuration) {
		this.meanDuration = meanDuration;
	}
	public long getStandardDeviationDurationNanos() {
		return (long)standardDeviationDuration;
	}
	public double getStandardDeviationDuration() {
		return standardDeviationDuration;
	}
	public void setStandardDeviationDuration(double standardDeviationDuration) {
		this.standardDeviationDuration = standardDeviationDuration;
	}
	public long getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(long maxDuration) {
		this.maxDuration = maxDuration;
	}
	public long getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(long minDuration) {
		this.minDuration = minDuration;
	}

	
}
