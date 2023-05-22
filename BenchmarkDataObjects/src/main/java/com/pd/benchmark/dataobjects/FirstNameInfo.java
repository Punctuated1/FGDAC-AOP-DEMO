package com.pd.benchmark.dataobjects;

import java.io.Serializable;

public class FirstNameInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1936093380940681222L;
	public String getFileRank() {
		return fileRank;
	}
	public void setFileRank(String fileRank) {
		this.fileRank = fileRank;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getNumberToGen() {
		return numberToGen;
	}
	public void setNumberToGen(Integer numberToGen) {
		this.numberToGen = numberToGen;
	}
	private int    rank;
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	private String fileRank;
	private String firstName;
	private String gender;
	private Integer numberToGen;
	

}
