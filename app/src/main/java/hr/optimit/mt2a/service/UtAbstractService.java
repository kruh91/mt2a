package hr.optimit.mt2a.service;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Named;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.oauth.OAuthUtil;
import retrofit2.Retrofit;

/**
 * Created by tomek on 12.07.17..
 */
public class UtAbstractService {

    /**
     * The Shared preferences.
     */
    @Inject
    protected SharedPreferences sharedPreferences;

    /**
     * The Rest retrofit.
     */
    @Inject
    @Named("restRetrofit")
    protected Retrofit restRetrofit;

    /**
     * The O auth util.
     */
    @Inject
    protected OAuthUtil oAuthUtil;

    /**
     * Instantiates a new Ut abstract service.
     */
    @Inject
    public UtAbstractService() {
        Mt2AApplication.getComponent().inject(this);
    }
}
