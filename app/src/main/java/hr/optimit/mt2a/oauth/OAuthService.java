package hr.optimit.mt2a.oauth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by tomek on 20.08.16..
 */
public interface OAuthService {

    /**
     * Gets access token.
     *
     * @param grantType the grant type
     * @param username  the username
     * @param password  the password
     * @return the access token
     */
    @POST("/oauth/token")
    @FormUrlEncoded
    Call<OAuthTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Field("username") String username,
                                            @Field("password") String password);

    /**
     * Gets access token.
     *
     * @param grantType    the grant type
     * @param refreshToken the refresh token
     * @return the access token
     */
    @POST("/oauth/token")
    @FormUrlEncoded
    Call<OAuthTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken);

    /**
     * Logout call.
     *
     * @return the call
     */
    @POST("/oauth/logout")
    Call<Void> logout();
}
