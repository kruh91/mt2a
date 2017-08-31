package hr.optimit.mt2a.timeSelect;

import android.support.v4.app.Fragment;

import hr.optimit.mt2a.model.UtActivity;

/**
 * Created by tomek on 29.08.17..
 */
public interface TimeSelect {

    void setTime(UtActivity utActivity);

    Fragment getFragment();

    int getIcon();
}
