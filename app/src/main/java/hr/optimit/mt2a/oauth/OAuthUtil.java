package hr.optimit.mt2a.oauth;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.exception.InvalidTokenException;
import hr.optimit.mt2a.exception.UtException;
import hr.optimit.mt2a.util.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by tomek on 21.08.16..
 */
public class OAuthUtil {

    /**
     * The Shared preferences.
     */
    @Inject
    SharedPreferences sharedPreferences;

    /**
     * The Rest retrofit.
     */
    @Inject
    @Named("restRetrofit")
    Retrofit restRetrofit;

    /**
     * The Oauth retrofit.
     */
    @Inject
    @Named("oauthRetrofit")
    Retrofit oauthRetrofit;

    /**
     * Instantiates a new O auth util.
     */
    @Inject
    public OAuthUtil() {
        Mt2AApplication.getComponent().inject(this);
    }

    /**
     * Authenticate.
     *
     * @param username the username
     * @param password the password
     * @throws UtException           the ut exception
     * @throws InvalidTokenException the invalid token exception
     * @throws IOException           the io exception
     */
    public void authenticate(String username, String password) throws UtException, InvalidTokenException, IOException {

        OAuthService service = oauthRetrofit.create(OAuthService.class);

        Call<OAuthTokenResponse> call = service.getAccessToken("password",
                username, password);

        processOAuthResponse(call, new Date().getTime(), username);

    }

    /**
     * Refresh token if needed.
     *
     * @throws UtException           the ut exception
     * @throws InvalidTokenException the invalid token exception
     * @throws IOException           the io exception
     */
    public void refreshTokenIfNeeded() throws UtException, InvalidTokenException, IOException {

        long tokenExpiresIn = (sharedPreferences.getLong(Constants.EXPIRE_DATE, 0) - System.currentTimeMillis()) / 1000;
        if (tokenExpiresIn < 60) {

            OAuthService service = oauthRetrofit.create(OAuthService.class);

            Call<OAuthTokenResponse> call = service.getAccessToken("refresh_token", sharedPreferences.getString(Constants.REFRESH_TOKEN, ""));

            processOAuthResponse(call, new Date().getTime(), sharedPreferences.getString(Constants.USERNAME, ""));
        }
    }

    /**
     * Logout.
     */
    public void logout() {
        OAuthService service = restRetrofit.create(OAuthService.class);
            Call<Void> call = service.logout();
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void processOAuthResponse(Call<OAuthTokenResponse> call, long callTime, String username) throws UtException, InvalidTokenException, IOException {

        Response<OAuthTokenResponse> response = call.execute();

        if (response.code() != 200) {
            ResponseBody errorBody = response.errorBody();
            BufferedReader reader = new BufferedReader(errorBody.charStream());

            Gson gson = new GsonBuilder().create();
            OAuthErrorResponse error = gson.fromJson(reader.readLine(), OAuthErrorResponse.class);

            if (error.getError().equals("invalid_grant")) {
                throw new InvalidTokenException();
            }

            throw new UtException(error.getErrorDesc());
        }

        OAuthTokenResponse tokenResponse = response.body();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, tokenResponse.getAccessToken());
        editor.putString(Constants.REFRESH_TOKEN, tokenResponse.getRefreshToken());
        editor.putString(Constants.TOKEN_TYPE, tokenResponse.getTokenType());
        editor.putLong(Constants.EXPIRE_DATE, callTime + (tokenResponse.getExpiresIn() * 1000));
        editor.putString(Constants.USERNAME, username);
        editor.commit();

    }

    /**
     * Is user logged in boolean.
     *
     * @return the boolean
     * @throws IOException the io exception
     * @throws UtException the ut exception
     */
    public boolean isUserLoggedIn() throws IOException, UtException {

        boolean hasAccessToken = sharedPreferences.getString(Constants.ACCESS_TOKEN, null) == null ? false : true;
        if (hasAccessToken) {
            try {
                refreshTokenIfNeeded();
                return true;
            } catch (InvalidTokenException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }
}
