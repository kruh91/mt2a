package hr.optimit.mt2a.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hr.optimit.mt2a.R;
import hr.optimit.mt2a.databinding.UtActivityListBinding;
import hr.optimit.mt2a.fragment.UtActivityListFragment;
import hr.optimit.mt2a.holder.UtActivityHolder;
import hr.optimit.mt2a.model.UtActivity;

/**
 * Created by tomek on 17.08.16..
 */
public class UtActivityAdapter extends RecyclerView.Adapter<UtActivityHolder> {

    private List<UtActivity> utActivityList;
    private Activity activity;
    private UtActivityListFragment fragment;

    /**
     * Instantiates a new Ut activity adapter.
     *
     * @param utActivityList the ut activity list
     * @param activity       the activity
     * @param fragment       the fragment
     */
    public UtActivityAdapter(List<UtActivity> utActivityList, Activity activity, UtActivityListFragment fragment) {
        this.utActivityList = utActivityList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public UtActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        UtActivityListBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.ut_activity_list, parent, false);
        return new UtActivityHolder(binding, activity, fragment, utActivityList);
    }

    @Override
    public void onBindViewHolder(UtActivityHolder holder, int position) {
        UtActivity activity = utActivityList.get(position);
        holder.bindUtActivity(activity);
    }

    @Override
    public int getItemCount() {
        return utActivityList.size();
    }

    /**
     * Sets ut activity list.
     *
     * @param utActivityList the ut activity list
     */
    public void setUtActivityList(List<UtActivity> utActivityList) {
        this.utActivityList.clear();
        this.utActivityList.addAll(utActivityList);
    }
}
