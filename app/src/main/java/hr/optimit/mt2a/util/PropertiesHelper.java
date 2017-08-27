package hr.optimit.mt2a.util;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tomek on 12.08.16..
 */
public class PropertiesHelper {

    private static String serverUrl;
    private static String oauthClientId;
    private static String oauthClientSecret;
    private static int defaultDayRange;
    private static int timpickerMinutesInterval;


    /**
     * Load properties.
     *
     * @param baseContext the base context
     * @throws IOException the io exception
     */
    public static void loadProperties(Context baseContext) throws IOException {
        Properties properties = new Properties();
        properties.load(baseContext.getAssets().open("application.properties"));

        serverUrl = properties.getProperty("server.url");
        oauthClientId = properties.getProperty("oauth.client.id");
        oauthClientSecret = properties.getProperty("oauth.client.secret");
        defaultDayRange = Integer.valueOf(properties.getProperty("default.dayRange", "7"));
        timpickerMinutesInterval = Integer.valueOf(properties.getProperty("timepicker.minutes.interval", "1"));
    }

    /**
     * Gets server url.
     *
     * @return the server url
     */
    public static String getServerUrl() {
        return serverUrl;
    }

    /**
     * Gets oauth client id.
     *
     * @return the oauth client id
     */
    public static String getOauthClientId() {
        return oauthClientId;
    }

    /**
     * Gets oauth client secret.
     *
     * @return the oauth client secret
     */
    public static String getOauthClientSecret() {
        return oauthClientSecret;
    }

    /**
     * Gets default day range.
     *
     * @return the default day range
     */
    public static int getDefaultDayRange() {
        return defaultDayRange;
    }

    /**
     * Gets timpicker minutes interval.
     *
     * @return the timpicker minutes interval
     */
    public static int getTimpickerMinutesInterval() {
        return timpickerMinutesInterval;
    }
}
