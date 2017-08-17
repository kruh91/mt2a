package hr.optimit.mt2a.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.R;
import hr.optimit.mt2a.api.RestResponse;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.model.UtLocation;
import hr.optimit.mt2a.model.UtProject;
import hr.optimit.mt2a.model.UtTask;
import hr.optimit.mt2a.service.UtActivityService;
import hr.optimit.mt2a.service.UtLocationService;
import hr.optimit.mt2a.service.UtProjectService;
import hr.optimit.mt2a.service.UtTaskService;
import hr.optimit.mt2a.task.UtAbstractAsyncTask;
import hr.optimit.mt2a.util.ComponentUtil;
import hr.optimit.mt2a.util.Constants;
import hr.optimit.mt2a.util.DateUtil;

/**
 * A fragment representing a list of Items.
 */
public class UtActivityFragment extends Fragment {

    private static final String ARG_ACTIVITY = "utActivity";

    @Inject
    DateUtil dateUtil;

    private UtActivity utActivity;
    private DatePicker datePicker;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private Spinner projectSpinner;
    private Spinner taskSpinner;
    private Spinner locationSpinner;
    private CheckBox billable;
    private EditText description;
    private ArrayAdapter<UtLocation> locationArrayAdapter;
    private ArrayAdapter<UtTask> taskArrayAdapter;
    private ArrayAdapter<UtProject> projectArrayAdapter;
    private boolean isNew = false;
    private Calendar calendar = Calendar.getInstance();
    private TextView projectSpinnerTitle;
    private TextView taskSpinnerTitle;
    private TextView locationSpinnerTitle;
    private TextView descriptionTitle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UtActivityFragment() {

    }

    public static UtActivityFragment newInstance(UtActivity utActivity) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTIVITY, utActivity);

        UtActivityFragment fragment = new UtActivityFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((Mt2AApplication) getActivity().getApplication()).getComponent().inject(this);
        this.utActivity = (UtActivity) getArguments().getSerializable(ARG_ACTIVITY);
        isNew = utActivity.getId() == null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utactivity, container, false);

        projectSpinnerTitle = (TextView) view.findViewById(R.id.project_spinner_title);
        taskSpinnerTitle = (TextView) view.findViewById(R.id.task_spinner_title);
        locationSpinnerTitle = (TextView) view.findViewById(R.id.location_spinner_title);
        descriptionTitle = (TextView) view.findViewById(R.id.description_title);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        startTimePicker = (TimePicker) view.findViewById(R.id.startTimePicker);
        startTimePicker.setIs24HourView(true);
        endTimePicker = (TimePicker) view.findViewById(R.id.endTimePicker);
        endTimePicker.setIs24HourView(true);
        projectSpinner = (Spinner) view.findViewById(R.id.project);
        projectArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        projectSpinner.setAdapter(projectArrayAdapter);
        projectSpinner.setOnItemSelectedListener(projectSpinnerItemSelectedListener);
        taskSpinner = (Spinner) view.findViewById(R.id.task);
        taskArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        taskSpinner.setOnItemSelectedListener(taskSpinnerItemSelectedListener);
        taskSpinner.setAdapter(taskArrayAdapter);
        locationSpinner = (Spinner) view.findViewById(R.id.location);
        locationArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        locationSpinner.setOnItemSelectedListener(locationSpinnerItemSelectedListener);
        locationSpinner.setAdapter(locationArrayAdapter);
        description = (EditText) view.findViewById(R.id.description);
        description.setText(utActivity.getDescription());
        billable = (CheckBox) view.findViewById(R.id.billable);
        billable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                utActivity.setBillable(checked);
            }
        });
        billable.setChecked(BooleanUtils.isTrue(utActivity.getBillable()));
        new GetProjectsAsyncTask(getActivity()).execute((Void[]) null);

        Date startDate = utActivity.getStartDate();
        if (startDate != null) {
            calendar.setTime(startDate);
            datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            startTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            startTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

        Date endDate = utActivity.getEndDate();
        if (endDate != null) {
            calendar.setTime(endDate);
            endTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            endTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

        if (utActivity.getBillable() == null ) {
            utActivity.setBillable(false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        // TODO Update Activity
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_utactivity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_utactivity:
                saveUtActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AdapterView.OnItemSelectedListener projectSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            UtProject project = (UtProject) adapterView.getItemAtPosition(pos);
            Long projectId = project.getId();
            utActivity.setUtProjectId(projectId);
            new GetTasksAsyncTask(getActivity()).execute((Void) null);
            new GetLocationsAsyncTask(getActivity()).execute(project.getPartnerId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener locationSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            UtLocation location = (UtLocation) adapterView.getItemAtPosition(pos);
            utActivity.setUtLocationId(location.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener taskSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            UtTask task = (UtTask) adapterView.getItemAtPosition(pos);
            utActivity.setUtTaskId(task.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public class GetLocationsAsyncTask extends UtAbstractAsyncTask<Long, List<UtLocation>> {

        public GetLocationsAsyncTask(Activity activity) {
            setActivity(activity);
        }

        @Override
        protected List<UtLocation> doInBackground(Long... params) {
            List<UtLocation> locations = new ArrayList<>();
            Long partnerId = params[0];
            if (partnerId != null) {
                UtLocationService utLocationService = new UtLocationService();
                try {
                    locations = utLocationService.getLocations(partnerId);
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorMessage(e.getMessage());
                }
            }

            return locations;
        }

        @Override
        public void doOnSuccess(List<UtLocation> utLocations) {
            locationArrayAdapter.clear();
            UtLocation dummyLocation = new UtLocation();
            dummyLocation.setLocationName("");
            locationArrayAdapter.add(dummyLocation);
            locationArrayAdapter.addAll(utLocations);

            Long utLocationId = utActivity.getUtLocationId();
            if (utLocationId != null) {
                boolean locationExists = false;
                for (UtLocation utLocation : utLocations) {
                    if (utLocation.getId().equals(utLocationId)) {
                        locationSpinner.setSelection(locationArrayAdapter.getPosition(utLocation));
                        locationExists = true;
                        break;
                    }
                }

                if (!locationExists) {
                    locationSpinner.setSelection(0);
                }
            }
        }
    }

    public class GetProjectsAsyncTask extends UtAbstractAsyncTask<Void, List<UtProject>> {

        public GetProjectsAsyncTask(Activity activity) {
            setActivity(activity);
        }

        @Override
        protected List<UtProject> doInBackground(Void... voids) {
            List<UtProject> projects = new ArrayList<>();
            UtProjectService utProjectService = new UtProjectService();
            try {
                projects = utProjectService.getProjects();
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage(e.getMessage());
            }

            return projects;
        }

        @Override
        public void doOnSuccess(List<UtProject> utProjects) {
            UtProject dummyProject = new UtProject();
            dummyProject.setProjectName("");
            projectArrayAdapter.add(dummyProject);
            projectArrayAdapter.addAll(utProjects);

            Long utProjectId = utActivity.getUtProjectId();
            if (utProjectId != null) {
                for (UtProject utProject : utProjects) {
                    if (utProject.getId().equals(utProjectId)) {
                        projectSpinner.setSelection(projectArrayAdapter.getPosition(utProject));
                        return;
                    }
                }
            }
        }
    }

    public class GetTasksAsyncTask extends UtAbstractAsyncTask<Void, List<UtTask>> {

        public GetTasksAsyncTask(Activity activity) {
            setActivity(activity);
        }

        @Override
        protected List<UtTask> doInBackground(Void... voids) {
            List<UtTask> tasks = new ArrayList<>();
            Long projectId = utActivity.getUtProjectId();
            if (projectId != null) {
                UtTaskService utTaskService = new UtTaskService();
                try {
                    tasks = utTaskService.getTasks(projectId);
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorMessage(e.getMessage());
                }
            }

            return tasks;
        }

        @Override
        public void doOnSuccess(List<UtTask> tasks) {
            taskArrayAdapter.clear();
            UtTask dummyTask = new UtTask();
            dummyTask.setTaskName("");
            taskArrayAdapter.add(dummyTask);
            taskArrayAdapter.addAll(tasks);

            Long utTaskId = utActivity.getUtTaskId();
            if (utTaskId != null) {
                boolean taskExists = false;
                for (UtTask utTask : tasks) {
                    if (utTask.getId().equals(utTaskId)) {
                        taskSpinner.setSelection(taskArrayAdapter.getPosition(utTask));
                        taskExists = true;
                        break;
                    }
                }

                if (!taskExists) {
                    taskSpinner.setSelection(0);
                }
            }
        }
    }

    public class SaveUtActivityAsyncTask extends UtAbstractAsyncTask<Void, RestResponse> {

        public SaveUtActivityAsyncTask(Activity activity) {
            setActivity(activity);
        }

        @Override
        protected RestResponse doInBackground(Void... voids) {
            UtActivityService service = new UtActivityService();
            RestResponse restResponse = new RestResponse();
            try {
                restResponse = service.saveActivity(utActivity);
                if (Constants.RESPONSE_STATUS_ERR.equals(restResponse.getStatus()))
                    setErrorMessage(restResponse.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage(e.getMessage());
            }

            return restResponse;
        }

        @Override
        public void doOnSuccess(RestResponse restResponse) {
            if (Constants.RESPONSE_STATUS_OK.equals(restResponse.getStatus())) {
                Toast.makeText(getActivity(), restResponse.getMessage(), Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

    private void saveUtActivity() {

        descriptionTitle.setError(null);
        projectSpinnerTitle.setError(null);
        taskSpinnerTitle.setError(null);
        locationSpinnerTitle.setError(null);

        boolean hasErrors = false;
        if(StringUtils.isBlank(description.getText().toString()))
        {
            descriptionTitle.setError("Obavezno polje");
            hasErrors = true;
        }

        if(projectSpinner.getSelectedItemId() == 0) {
            projectSpinnerTitle.setError("Obavezno polje");
            hasErrors = true;
        }

        if(taskSpinner.getSelectedItemId() == 0) {
            taskSpinnerTitle.setError("Obavezno polje");
            hasErrors = true;
        }

        if(locationSpinner.getSelectedItemId() == 0) {
            locationSpinnerTitle.setError("Obavezno polje");
            hasErrors = true;
        }

        if(hasErrors) {
            return;
        }

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();
        calendar.set(year, month, dayOfMonth);

        calendar.set(Calendar.HOUR_OF_DAY, startTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, startTimePicker.getCurrentMinute());
        utActivity.setStartDate(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, endTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, endTimePicker.getCurrentMinute());
        utActivity.setEndDate(calendar.getTime());

        utActivity.setDescription(description.getText().toString());

        new SaveUtActivityAsyncTask(getActivity()).execute((Void) null);
    }
}
