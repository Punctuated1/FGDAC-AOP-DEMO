package com.pd.benchmark.fgacdataobjects;

import java.time.LocalDateTime;

public class DataAccessContext {
	private String elapsedTime;
	private String securityScopes;
	private String securityRole;
	private String targetRepository;
	private String methodName;
	private String userName;
	private Object[] inputArgs;
	private Object operationResult;
	private LocalDateTime operationTimeStamp;
	

	public String getSecurityScopes() {
		return securityScopes;
	}
	public void setSecurityScopes(String securityScopes) {
		this.securityScopes = securityScopes;
	}
	public String getSecurityRole() {
		return securityRole;
	}
	public void setSecurityRole(String securityRole) {
		this.securityRole = securityRole;
	}
	public String getTargetRepository() {
		return targetRepository;
	}
	public void setTargetRepository(String targetRepository) {
		this.targetRepository = targetRepository;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Object[] getInputArgs() {
		return inputArgs;
	}
	public void setInputArgs(Object[] inputArgs) {
		this.inputArgs = inputArgs;
	}
	public Object getOperationResult() {
		return operationResult;
	}
	public void setOperationResult(Object operationResult) {
		this.operationResult = operationResult;
	}
	public LocalDateTime getOperationTimeStamp() {
		return operationTimeStamp;
	}
	public void setOperationTimeStamp(LocalDateTime operationTimeStamp) {
		this.operationTimeStamp = operationTimeStamp;
	}
	public String getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	};
	
	

}
