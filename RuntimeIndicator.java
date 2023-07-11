package com.pd.benchmark.dataobjects;

public class RuntimeIndicator {
	
	private static RuntimeIndicator runtimeIndicator = null;
	public String runtimeEnvironment;
	
	private RuntimeIndicator() {
		runtimeEnvironment="Ubuntu";
	}

	public static RuntimeIndicator getRuntimeIndicator() {
		if(runtimeIndicator == null) {
			runtimeIndicator = new RuntimeIndicator();
		}
		return runtimeIndicator;
	}
}
