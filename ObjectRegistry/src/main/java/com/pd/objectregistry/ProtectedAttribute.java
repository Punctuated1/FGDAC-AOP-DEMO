package com.pd.objectregistry;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProtectedAttribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String attributeSetterName;
	private String attributeMaskValue;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAttributeSetterName() {
		return attributeSetterName;
	}
	public void setAttributeSetterName(String attributeSetterName) {
		attributeSetterName = attributeSetterName;
	}
	public String getAttributeMaskValue() {
		return attributeMaskValue;
	}
	public void setAttributeMaskValue(String attributeMaskValue) {
		this.attributeMaskValue = attributeMaskValue;
	}
	
	

}
