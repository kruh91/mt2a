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

    @Inject
    protected SharedPreferences sharedPreferences;

    @Inject
    @Named("restRetrofit")
    protected Retrofit restRetrofit;

    @Inject
    protected OAuthUtil oAuthUtil;

    @Inject
    public UtAbstractService() {
        Mt2AApplication.getComponent().inject(this);
    }
}
