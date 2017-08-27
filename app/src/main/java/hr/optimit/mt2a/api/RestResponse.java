package hr.optimit.mt2a.api;

import java.io.Serializable;

/**
 * The Class RestResponse.
 */
public class RestResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;
	
	private String message;

	/**
	 * Instantiates a new rest response.
	 */
	public RestResponse() {
		
	}

	/**
	 * Instantiates a new rest response.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public RestResponse(String status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
