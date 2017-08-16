package hr.optimit.mt2a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import hr.optimit.mt2a.R;
import hr.optimit.mt2a.fragment.UtActivityFragment;
import hr.optimit.mt2a.model.UtActivity;

/**
 * Created by tomek on 23.06.17..
 */
public class UtActivityPagerActivity extends AppCompatActivity {

    private static final String ARG_ACTIVITY = "utActivity";
    private static final String ARG_ACTIVITIES = "utActivities";

    private ViewPager mViewPager;
    private UtActivity mUtActivity;
    private List<UtActivity> utActivities;

    public static Intent newIntent(Context packageContext, UtActivity utActivity, List<UtActivity> utActivities) {
        Intent intent = new Intent(packageContext, UtActivityPagerActivity.class);
        intent.putExtra(ARG_ACTIVITY, utActivity);

        UtActivity[] activityArray = new UtActivity[utActivities.size()];
        utActivities.toArray(activityArray);
        intent.putExtra(ARG_ACTIVITIES, activityArray);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utactivity_pager);

        mUtActivity = (UtActivity) getIntent().getSerializableExtra(ARG_ACTIVITY);
        UtActivity[] activityArray = (UtActivity[]) getIntent().getSerializableExtra(ARG_ACTIVITIES);
        utActivities = Arrays.asList(activityArray);

        mViewPager = (ViewPager) findViewById(R.id.activity_utactivity_pager_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                UtActivity utActivity;
                if(mUtActivity.getId() == null) {
                    utActivity = mUtActivity;
                } else {
                    utActivity = utActivities.get(position);
                }
                return UtActivityFragment.newInstance(utActivity);
            }

            @Override
            public int getCount() {
                return utActivities.size();
            }
        });

        if(mUtActivity.getId()  != null) {
            for (int i = 0; i < utActivities.size(); i++) {
                if (utActivities.get(i).getId().equals(mUtActivity.getId())) {
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }
}
