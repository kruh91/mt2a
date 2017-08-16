package hr.optimit.mt2a.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * UtActivity.
 */
public class UtActivity implements java.io.Serializable {


    /**
     * The id.
     */
    private Long id;

    /**
     * The ut task id.
     */
    private Long utTaskId;

    /**
     * The ut location id.
     */
    private Long utLocationId;

    /**
     * The description.
     */
    private String description;

    /**
     * The ref.
     */
    private String ref;

    /**
     * The start date.
     */
    private Date startDate;

    /**
     * The end date.
     */
    private Date endDate;

    /**
     * The billable.
     */
    private Boolean billable;

    /**
     * The user id.
     */
    private Long userId;

    /**
     * The modif time.
     */
    private Timestamp modifTime;

    /**
     * The modif user id.
     */
    private Long modifUserId;

    /**
     * The ut task name.
     */
    private String utTaskName;

    /**
     * The ut project short name.
     */
    private String utProjectShortName;

    /** The ut project name. */
    private String utProjectName;

    /** The ut project id. */
    private Long utProjectId;


    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ut task id.
     *
     * @return the ut task id
     */
    public Long getUtTaskId() {
        return utTaskId;
    }

    /**
     * Sets the ut task id.
     *
     * @param utTaskId the new ut task id
     */
    public void setUtTaskId(Long utTaskId) {
        this.utTaskId = utTaskId;
    }

    /**
     * Gets the ut location id.
     *
     * @return the ut location id
     */
    public Long getUtLocationId() {
        return utLocationId;
    }

    /**
     * Sets the ut location id.
     *
     * @param utLocationId the new ut location id
     */
    public void setUtLocationId(Long utLocationId) {
        this.utLocationId = utLocationId;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the ref.
     *
     * @return the ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the ref.
     *
     * @param ref the new ref
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the billable.
     *
     * @return the billable
     */
    public Boolean getBillable() {
        return billable;
    }

    /**
     * Sets the billable.
     *
     * @param billable the new billable
     */
    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the modif time.
     *
     * @return the modif time
     */
    public Timestamp getModifTime() {
        return modifTime;
    }

    /**
     * Sets the modif time.
     *
     * @param modifTime the new modif time
     */
    public void setModifTime(Timestamp modifTime) {
        this.modifTime = modifTime;
    }

    /**
     * Gets the modif user id.
     *
     * @return the modif user id
     */
    public Long getModifUserId() {
        return modifUserId;
    }

    /**
     * Sets the modif user id.
     *
     * @param modifUserId the new modif user id
     */
    public void setModifUserId(Long modifUserId) {
        this.modifUserId = modifUserId;
    }

    /**
     * Gets the ut project short name.
     *
     * @return the ut project short name
     */
    public String getUtProjectShortName() {
        return utProjectShortName;
    }

    /**
     * Sets the ut project short name.
     *
     * @param utProjectShortName the new ut project short name
     */
    public void setUtProjectShortName(String utProjectShortName) {
        this.utProjectShortName = utProjectShortName;
    }

    /**
     * Gets the ut task name.
     *
     * @return the ut task name
     */
    public String getUtTaskName() {
        return utTaskName;
    }

    /**
     * Sets the ut task name.
     *
     * @param utTaskName the new ut task name
     */
    public void setUtTaskName(String utTaskName) {
        this.utTaskName = utTaskName;
    }

    /**
     * Gets the ut project name.
     *
     * @return the ut project name
     */
    public String getUtProjectName() {
        return utProjectName;
    }

    /**
     * Sets the ut project name.
     *
     * @param utProjectName the new ut project name
     */
    public void setUtProjectName(String utProjectName) {
        this.utProjectName = utProjectName;
    }

    /**
     * Gets the ut project id.
     *
     * @return the ut project id
     */
    public Long getUtProjectId() {
        return utProjectId;
    }

    /**
     * Sets the ut project id.
     *
     * @param utProjectId the new ut project id
     */
    public void setUtProjectId(Long utProjectId) {
        this.utProjectId = utProjectId;
    }
}
