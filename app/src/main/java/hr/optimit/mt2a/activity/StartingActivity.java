package hr.optimit.mt2a.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.exception.UtException;
import hr.optimit.mt2a.oauth.OAuthUtil;
import hr.optimit.mt2a.task.UtAbstractAsyncTask;
import hr.optimit.mt2a.util.PropertiesHelper;

/**
 * Created by tomek on 20.07.17..
 */
public class StartingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(new Locale("hr", "HR"));

        try {
            PropertiesHelper.loadProperties(getBaseContext());
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties.");
        }

        try {
            new CheckUserStatusAsyncTask(StartingActivity.this).execute((Void[]) null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * The type Check user status async task.
     */
    public class CheckUserStatusAsyncTask extends UtAbstractAsyncTask<Void, Boolean> {

        /**
         * The O auth util.
         */
        @Inject
        OAuthUtil oAuthUtil;

        /**
         * Instantiates a new Check user status async task.
         *
         * @param activity the activity
         */
        public CheckUserStatusAsyncTask(Activity activity) {
            setActivity(activity);
            ((Mt2AApplication) getApplication()).getComponent().inject(this);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                return oAuthUtil.isUserLoggedIn();
            } catch (IOException | UtException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        public void doOnSuccess(Boolean aBoolean) {
            Intent i;
            if (aBoolean) {
                i = new Intent(StartingActivity.this, UtActivityListActivity.class);
            } else {
                i = new Intent(StartingActivity.this, LoginActivity.class);
            }

            startActivity(i);
        }
    }
}
