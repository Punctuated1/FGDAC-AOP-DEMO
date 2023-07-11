package com.pd.benchmark.dataobjects;

import java.io.Serializable;
import java.time.LocalDate;

public class PersonRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6330852296421961508L;
	private Integer personId;
	private String lastName;
	private String firstName;
	private String gender;
	private LocalDate dateOfBirth;
	private String cag;
	
	/**
	 * 
	 */
	public PersonRecord() {
		super();
	}


	/**
	 * @param String[]
	 */
	public PersonRecord(String[] fileFields, String[] cags) {
		this.personId = Integer.parseInt(fileFields[0]);
		this.firstName = fileFields[1].substring(1, fileFields[1].length() - 1);
		this.lastName = fileFields[2].substring(1, fileFields[2].length() - 1);
		this.gender = fileFields[3].substring(1, fileFields[3].length() - 1);
//		System.out.println("First Name: "+this.firstName+"  lastName: "+this.lastName+" gender: "+this.gender+"  DOB: "+fileFields[4].substring(1, fileFields[4].length() - 1));
		double rndm = Math.random()*cags.length;
		int i = (int)rndm;
		this.cag=cags[i];
		if(this.cag==null) {
			System.out.println("randm: "+i+" -- cas.length: "+cags.length);
			this.cag="1.1.1";
		}
		this.dateOfBirth = LocalDate.parse(fileFields[4].substring(1, fileFields[4].length() - 1),BenchmarkConstants.LOCAL_DATE_FORMATTER);
	}
	
	
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getCag() {
		return cag;
	}


	public void setCag(String cag) {
		this.cag = cag;
	}

}
