package hr.optimit.mt2a.dipendencyInjection.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.optimit.mt2a.oauth.OAuthUtil;
import hr.optimit.mt2a.util.Constants;
import hr.optimit.mt2a.util.PropertiesHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tomek on 21.08.16..
 */
@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences(Constants.SHARED_PREF_NAME, 0);
    }

    @Singleton
    @Provides
    OAuthUtil provideOAuthUtil() {
        return new OAuthUtil();
    }

    @Singleton
    @Provides
    @Named("oauthRetrofit")
    Retrofit getRetrofitForOAuthAuthentication() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                        String credentials = PropertiesHelper.getOauthClientId() + ":" + PropertiesHelper.getOauthClientSecret();
                        String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                        Request request = chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", basicAuth).build();

                        return chain.proceed(request);

                    }
                })
                .build();

        return createRetrofit(client);

    }

    @Singleton
    @Provides
    @Named("restRetrofit")
    Retrofit getRetrofitForRestCall() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                        SharedPreferences sharedPreferences = provideSharedPreferences();

                        String basicAuth = sharedPreferences.getString(Constants.TOKEN_TYPE, "Bearer")
                                + " "
                                + sharedPreferences.getString(Constants.ACCESS_TOKEN, "");

                        Request request = chain.request().newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", basicAuth).build();

                        return chain.proceed(request);

                    }
                })
                .build();

        return createRetrofit(client);

    }

    private Retrofit createRetrofit(OkHttpClient client) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PropertiesHelper.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;

    }
}