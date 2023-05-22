package com.pd.benchmark.dataobjects;

import java.io.Serializable;

public class PersonRaw implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8301972509855362189L;
	private Integer rank;
	private String lastName;
	private Integer numberToGen;
	private String fileRank;
	
	public String getFileRank() {
		return fileRank;
	}
	public void setFileRank(String fileRank) {
		this.fileRank = fileRank;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getNumberToGen() {
		return numberToGen;
	}
	public void setNumberToGen(Integer numberToGen) {
		this.numberToGen = numberToGen;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
