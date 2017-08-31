package hr.optimit.mt2a.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.R;
import hr.optimit.mt2a.adapter.UtActivityAdapter;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.service.UtActivityService;
import hr.optimit.mt2a.task.UtAbstractAsyncTask;
import hr.optimit.mt2a.timeSelect.TimeSelectManager;
import hr.optimit.mt2a.util.DateUtil;

/**
 * Created by tomek on 17.08.16..
 */
public class UtActivityListFragment extends Fragment {

    /**
     * The Date util.
     */
    @Inject
    DateUtil dateUtil;

    private RecyclerView listRecyclerView;
    private UtActivityAdapter utActivityAdapter;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private TextView startDateText;
    private TextView endDateText;
    private List<UtActivity> activities = new ArrayList<>();
    private Long selectedUtActivityId = null;
    private View selectedView = null;
    private FloatingActionButton addCopyButton;
    private FloatingActionsMenu floatingActionsMenu;

    private Callbacks mCallbacks;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((Mt2AApplication) getActivity().getApplication()).getComponent().inject(this);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utactivity_list, container, false);

        listRecyclerView = (RecyclerView) view
                .findViewById(R.id.utactivity_recycler_view);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addCopyButton = (FloatingActionButton) view.findViewById(R.id.add_copy_button);
        addCopyButton.setOnClickListener(addCopyButtonClickListener);
        floatingActionsMenu = (FloatingActionsMenu) view.findViewById(R.id.add_floating_menu);
        createTimeSelectOptions(inflater);
        startDateText = (TextView) view.findViewById(R.id.startDateText);
        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), startDatePickerListener, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDateText = (TextView) view.findViewById(R.id.endDateText);
        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), endDatePickerListener, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void createTimeSelectOptions(LayoutInflater inflater) {
        TimeSelectManager timeSelectManager = TimeSelectManager.getInstance();
        timeSelectManager.setDependencies(inflater, floatingActionsMenu, this);
        timeSelectManager.addTimeSelect(new ManualTimeSelect());
        timeSelectManager.addTimeSelect(new StartStopTimeSelect());
    }

    /**
     * Update ui.
     */
    public void updateUI() {
        Pair<Date, Date> startAndEndDate = dateUtil.getStartAndEndDate();
        Date startDate = startAndEndDate.first;
        Date endDate = startAndEndDate.second;

        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        try {
            new GetActivitiesAsyncTask(getActivity(), startDate, endDate).execute((Void[]) null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        startDateText.setText(dateUtil.getFormattedDate(startDate));
        endDateText.setText(dateUtil.getFormattedDate(endDate));

        if (endDate.before(startDate)) {
            endDateText.setError("Početni datum prije završnog");
        } else {
            endDateText.setError(null);
        }

        floatingActionsMenu.collapse();
    }

    private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateUtil.setCalendarToBeginingOfDay(startCalendar);
            dateUtil.updateStartDate(startCalendar.getTime());
            updateUI();
        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateUtil.setCalendarToEndOfDay(endCalendar);
            dateUtil.updateEndDate(endCalendar.getTime());
            updateUI();
        }
    };

    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        /**
         * On ut activity clicked.
         *
         * @param utActivity   the ut activity
         * @param utActivities the ut activities
         */
        void onUtActivityClicked(UtActivity utActivity, List<UtActivity> utActivities, int timeSelectId);
    }

    /**
     * The type Get activities async task.
     */
    public class GetActivitiesAsyncTask extends UtAbstractAsyncTask<Void, List<UtActivity>> {

        private Date startDate;
        private Date endDate;

        /**
         * Instantiates a new Get activities async task.
         *
         * @param activity  the activity
         * @param startDate the start date
         * @param endDate   the end date
         */
        public GetActivitiesAsyncTask(Activity activity, Date startDate, Date endDate) {
            setActivity(activity);
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        protected List<UtActivity> doInBackground(Void... voids) {
            activities = new ArrayList<>();
            UtActivityService utActivityService = new UtActivityService();
            try {
                activities = utActivityService.getActivities(startDate, endDate);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage(e.getMessage());
            }

            return activities;
        }

        @Override
        public void doOnSuccess(List<UtActivity> utActivities) {
            if (utActivityAdapter == null) {
                utActivityAdapter = new UtActivityAdapter(utActivities, getActivity(), UtActivityListFragment.this);
                listRecyclerView.setAdapter(utActivityAdapter);
            } else {
                utActivityAdapter.setUtActivityList(utActivities);
                utActivityAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Change selection.
     *
     * @param newSelectedView       the new selected view
     * @param newSelectedActivityId the new selected activity id
     */
    public void changeSelection(View newSelectedView, Long newSelectedActivityId) {

        if (dateUtil.isTimeMeasureStarted()) {
            return;
        }

        if (newSelectedActivityId.equals(selectedUtActivityId)) {
            selectedView = null;
            selectedUtActivityId = null;
            adaptViewForSelectedItem(newSelectedView, false);
        } else {
            if (selectedView != null) {
                selectedView.setSelected(false);
                selectedView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bottomborder, null));
            }
            adaptViewForSelectedItem(newSelectedView, true);
            selectedView = newSelectedView;
            selectedUtActivityId = newSelectedActivityId;
        }
    }

    private View.OnClickListener addCopyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UtActivity utActivity = new UtActivity();
            UtActivity selectedUtActivity = null;
            for (UtActivity activity : activities) {
                if (activity.getId().equals(selectedUtActivityId)) {
                    selectedUtActivity = activity;
                    break;
                }
            }
            utActivity.setUtProjectId(selectedUtActivity.getUtProjectId());
            utActivity.setUtProjectShortName(selectedUtActivity.getUtProjectShortName());
            utActivity.setUtProjectName(selectedUtActivity.getUtProjectName());
            utActivity.setUtTaskId(selectedUtActivity.getUtTaskId());
            utActivity.setUtTaskName(selectedUtActivity.getUtTaskName());
            utActivity.setUtLocationId(selectedUtActivity.getUtLocationId());
            utActivity.setBillable(selectedUtActivity.getBillable());
            invokeCreateNewActivity(TimeSelectManager.getInstance().getDefaultTimeSelectId());
            selectedView = null;
            selectedUtActivityId = null;
        }
    };

    private void enableAndShowFloatingButton(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        button.setEnabled(true);
    }

    private void disableAndHideFloatingButton(FloatingActionButton button) {
        button.setVisibility(View.GONE);
        button.setEnabled(false);
    }

    private void adaptViewForSelectedItem(View view, boolean selected) {
        view.setSelected(selected);

        if (selected) {
            view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.backgroundSelected, null));
            enableAndShowFloatingButton(addCopyButton);
            floatingActionsMenu.setEnabled(false);
            floatingActionsMenu.setVisibility(View.GONE);
            floatingActionsMenu.collapse();
        } else {
            view.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bottomborder, null));
            floatingActionsMenu.setEnabled(true);
            floatingActionsMenu.setVisibility(View.VISIBLE);
            disableAndHideFloatingButton(addCopyButton);
        }
    }

    /**
     * Gets selected ut activity id.
     *
     * @return the selected ut activity id
     */
    public Long getSelectedUtActivityId() {
        return selectedUtActivityId;
    }

    /**
     * Sets selected ut activity id.
     *
     * @param selectedUtActivityId the selected ut activity id
     */
    public void setSelectedUtActivityId(Long selectedUtActivityId) {
        this.selectedUtActivityId = selectedUtActivityId;
    }

    /**
     * Sets selected view.
     *
     * @param selectedView the selected view
     */
    public void setSelectedView(View selectedView) {
        this.selectedView = selectedView;
    }

    public void invokeCreateNewActivity(int timeSelectedId) {
        UtActivity utActivity = new UtActivity();
        mCallbacks.onUtActivityClicked(utActivity, Collections.singletonList(utActivity), timeSelectedId);
    }
}
