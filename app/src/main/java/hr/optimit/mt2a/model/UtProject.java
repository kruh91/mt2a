package hr.optimit.mt2a.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Ut project.
 */
public class UtProject implements Serializable {
	
	private Long id;
	
	private String projectName;
	
	private String projectShortname;
	
	private Date projectStartDate;
	
	private Date projectEndDate;

    private Long partnerId;

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
	 * Gets project name.
	 *
	 * @return the project name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets project name.
	 *
	 * @param projectName the project name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Gets project shortname.
	 *
	 * @return the project shortname
	 */
	public String getProjectShortname() {
		return projectShortname;
	}

	/**
	 * Sets project shortname.
	 *
	 * @param projectShortname the project shortname
	 */
	public void setProjectShortname(String projectShortname) {
		this.projectShortname = projectShortname;
	}

	/**
	 * Gets project start date.
	 *
	 * @return the project start date
	 */
	public Date getProjectStartDate() {
		return projectStartDate;
	}

	/**
	 * Sets project start date.
	 *
	 * @param projectStartDate the project start date
	 */
	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	/**
	 * Gets project end date.
	 *
	 * @return the project end date
	 */
	public Date getProjectEndDate() {
		return projectEndDate;
	}

	/**
	 * Sets project end date.
	 *
	 * @param projectEndDate the project end date
	 */
	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	/**
	 * Gets partner id.
	 *
	 * @return the partner id
	 */
	public Long getPartnerId() {
		return partnerId;
	}

	/**
	 * Sets partner id.
	 *
	 * @param partnerId the partner id
	 */
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public String toString() {
		return getProjectName();
	}
}
