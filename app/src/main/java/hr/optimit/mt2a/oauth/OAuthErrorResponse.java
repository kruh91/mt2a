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


    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
