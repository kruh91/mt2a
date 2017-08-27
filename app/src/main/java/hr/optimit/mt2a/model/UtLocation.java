package hr.optimit.mt2a.model;

import java.io.Serializable;

/**
 * The type Ut location.
 */
public class UtLocation implements Serializable {

	private Long id;
	
	private String locationName;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets location name.
	 *
	 * @return the location name
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * Sets location name.
	 *
	 * @param locationName the location name
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public String toString() {
		return getLocationName();
	}
}
