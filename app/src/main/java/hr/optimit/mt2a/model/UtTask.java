package hr.optimit.mt2a.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Ut task.
 */
public class UtTask implements Serializable {

	private Long id;
	
	private String taskName;
	
	private Long projectId;
	
	private Date taskStartDate;
	
	private Date taskEndDate;

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
	 * Gets task name.
	 *
	 * @return the task name
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets task name.
	 *
	 * @param taskName the task name
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Gets project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * Sets project id.
	 *
	 * @param projectId the project id
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Gets task start date.
	 *
	 * @return the task start date
	 */
	public Date getTaskStartDate() {
		return taskStartDate;
	}

	/**
	 * Sets task start date.
	 *
	 * @param taskStartDate the task start date
	 */
	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	/**
	 * Gets task end date.
	 *
	 * @return the task end date
	 */
	public Date getTaskEndDate() {
		return taskEndDate;
	}

	/**
	 * Sets task end date.
	 *
	 * @param taskEndDate the task end date
	 */
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	@Override
	public String toString() {
		return getTaskName();
	}
}
