package hr.optimit.mt2a.oauth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tomek on 20.08.16..
 */
public class OAuthErrorResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDesc;


    /**
     * Gets error desc.
     *
     * @return the error desc
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * Sets error desc.
     *
     * @param errorDesc the error desc
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }
}
