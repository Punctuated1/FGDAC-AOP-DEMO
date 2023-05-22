package com.pd.objectregistry;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//import org.springframework.data.gemfire.mapping.annotation.Region;

@Entity
public class ObjectRegistry implements Serializable {
	@Id
	private String dataObjectName;
	private Boolean dataObjectFiltering;
	private String filterAttributeName;
	private Boolean hostsSensitiveData;
	
	@OneToMany(cascade = CascadeType.ALL)
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
