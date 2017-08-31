package hr.optimit.mt2a.holder;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import hr.optimit.mt2a.R;
import hr.optimit.mt2a.databinding.UtActivityListBinding;
import hr.optimit.mt2a.fragment.UtActivityListFragment;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.timeSelect.TimeSelectManager;
import hr.optimit.mt2a.viewModel.UtActivityViewModel;


/**
 * Created by tomek on 17.08.16..
 */
public class UtActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private UtActivityListBinding binding;
    private UtActivityViewModel viewModel;
    private UtActivityListFragment.Callbacks callback;
    private List<UtActivity> utActivities;
    private UtActivityListFragment fragment;

    /**
     * Instantiates a new Ut activity holder.
     *
     * @param binding      the binding
     * @param activity     the activity
     * @param fragment     the fragment
     * @param utActivities the ut activities
     */
    public UtActivityHolder(UtActivityListBinding binding, Activity activity, UtActivityListFragment fragment, List<UtActivity> utActivities) {

        super(binding.getRoot());

        binding.getRoot().setOnClickListener(this);
        binding.getRoot().setOnLongClickListener(this);
        this.binding = binding;
        this.callback = (UtActivityListFragment.Callbacks) activity;
        this.utActivities = utActivities;
        this.fragment = fragment;
    }

    /**
     * Bind ut activity.
     *
     * @param utActivity the ut activity
     */
    public void bindUtActivity(UtActivity utActivity) {
        viewModel = new UtActivityViewModel(utActivity);
        adaptView();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public void onClick(View view) {
        callback.onUtActivityClicked(viewModel.getUtActivity(), utActivities, TimeSelectManager.getInstance().getDefaultTimeSelectId());
        fragment.setSelectedUtActivityId(null);
        fragment.setSelectedView(null);
    }

    @Override
    public boolean onLongClick(View view) {
        fragment.changeSelection(view, viewModel.getUtActivity().getId());
        return true;
    }

    private void adaptView() {
        Long utActivityId = viewModel.getUtActivity().getId();
        View view = binding.getRoot();
        if(utActivityId.equals(fragment.getSelectedUtActivityId())) {
            view.setSelected(true);
            view.setBackgroundColor(ResourcesCompat.getColor(fragment.getResources(), R.color.backgroundSelected, null));
        } else {
            view.setSelected(false);
            view.setBackground(ResourcesCompat.getDrawable(fragment.getResources(), R.drawable.bottomborder, null));
        }
    }
}
