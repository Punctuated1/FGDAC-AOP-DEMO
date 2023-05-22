package com.pd.benchmark.fgacdataobjects;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.annotation.Id;
//import org.springframework.data.gemfire.mapping.annotation.Region;

//@Region("objectRegistry")
public class ObjectRegistry implements Serializable {
	@Id
	private String dataObjectName;
	private Boolean dataObjectFiltering;
	private String filterAttributeName;
	private Boolean hostsSensitiveData;
	private Collection<ProtectedAttribute> protectedAttributes; 

	public String getDataObjectName() {
		return dataObjectName;
	}
	public void setDataObjectName(String dataObjectName) {
		this.dataObjectName = dataObjectName;
	}

	public Boolean getDataObjectFiltering() {
		return dataObjectFiltering;
	}
	public void setDataObjectFiltering(Boolean dataObjectFiltering) {
		this.dataObjectFiltering = dataObjectFiltering;
	}
	public Boolean isHostsSensitiveData() {
		return hostsSensitiveData;
	}
	public void setHostsSensitiveData(boolean hostsSensitiveData) {
		this.hostsSensitiveData = hostsSensitiveData;
	}
	public String getFilterAttributeName() {
		return filterAttributeName;
	}
	public void setFilterAttributeName(String filterAttributeName) {
		this.filterAttributeName = filterAttributeName;
	}
	public Collection<ProtectedAttribute> getProtectedAttributes() {
		return protectedAttributes;
	}
	public void setProtectedAttributes(Collection<ProtectedAttribute> protectedAttributes) {
		this.protectedAttributes = protectedAttributes;
	}
	
}
