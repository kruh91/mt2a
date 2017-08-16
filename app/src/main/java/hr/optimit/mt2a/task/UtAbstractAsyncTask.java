package hr.optimit.mt2a.task;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

/**
 * Created by tomek on 12.07.17..
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

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract void doOnSuccess(T t);
}
