package hr.optimit.mt2a.model;

import java.io.Serializable;

public class UtLocation implements Serializable {

	private Long id;
	
	private String locationName;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public String toString() {
		return getLocationName();
	}
}
