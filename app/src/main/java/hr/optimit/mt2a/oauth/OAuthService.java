package hr.optimit.mt2a.oauth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by tomek on 20.08.16..
 */
public interface OAuthService {

    @POST("/oauth/token")
    @FormUrlEncoded
    Call<OAuthTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Field("username") String username,
                                            @Field("password") String password);

    @POST("/oauth/token")
    @FormUrlEncoded
    Call<OAuthTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken);

}
