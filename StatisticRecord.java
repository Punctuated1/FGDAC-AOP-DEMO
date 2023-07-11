package com.pd.benchmark.dataobjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class StatisticRecord {
	private String operationType;
	private Integer setKeyInteger;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private long durationOfOperation;
	
	private void computeDurationOfOperation() {
		durationOfOperation = Duration.between(startTime, endTime).toNanos();
	}

	
	
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



	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		computeDurationOfOperation();
	}

	public long getDurationOfOperation() {
		return durationOfOperation;
	}

	public void setDurationOfOperation(long durationOfOperation) {
		this.durationOfOperation = durationOfOperation;
	}
	
	

}
