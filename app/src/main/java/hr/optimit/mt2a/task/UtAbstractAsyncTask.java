package hr.optimit.mt2a.task;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

/**
 * Created by tomek on 12.07.17..
 *
 * @param <P> the type parameter
 * @param <T> the type parameter
 */
public abstract class UtAbstractAsyncTask<P, T> extends AsyncTask<P, Void, T> {

    private String errorMessage;

    private Activity activity;

    @Override
    protected void onPostExecute(T t) {
        if (errorMessage != null && errorMessage != "") {
            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(errorMessage);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            doOnSuccess(t);
        }
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Do on success.
     *
     * @param t the t
     */
    public abstract void doOnSuccess(T t);
}
