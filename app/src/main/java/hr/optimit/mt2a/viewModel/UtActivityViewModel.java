package hr.optimit.mt2a.viewModel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import hr.optimit.mt2a.model.UtActivity;

/**
 * Created by tomek on 16.08.16..
 */
public class UtActivityViewModel extends BaseObservable {

    private UtActivity utActivity;

    /**
     * Instantiates a new Ut activity view model.
     *
     * @param utActivity the ut activity
     */
    public UtActivityViewModel(UtActivity utActivity) {
        this.utActivity = utActivity;
    }

    /**
     * Gets ut activity.
     *
     * @return the ut activity
     */
    public UtActivity getUtActivity() {
        return utActivity;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    @Bindable
    public String getTitle() {
        return this.utActivity.getUtProjectName() + ": " + this.utActivity.getUtTaskName();
    }

    /**
     * Gets date range.
     *
     * @return the date range
     */
    @Bindable
    public String getDateRange() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy", new Locale("hr", "HR"));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        return StringUtils.capitalize(dateFormat.format(this.utActivity.getStartDate())) + ": "
                + timeFormat.format(this.utActivity.getStartDate()) + " - " + timeFormat.format(this.utActivity.getEndDate());
    }
}
