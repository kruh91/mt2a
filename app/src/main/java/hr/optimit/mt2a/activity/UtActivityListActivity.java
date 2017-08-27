package hr.optimit.mt2a.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import hr.optimit.mt2a.R;
import hr.optimit.mt2a.fragment.UtActivityListFragment;
import hr.optimit.mt2a.model.UtActivity;

/**
 * The type Ut activity list activity.
 */
public class UtActivityListActivity extends SingleFragmentActivity implements UtActivityListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new UtActivityListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onUtActivityClicked(UtActivity activity, List<UtActivity> utActivities) {
        Intent intent = UtActivityPagerActivity.newIntent(this, activity, utActivities);
        startActivity(intent);
    }
}
