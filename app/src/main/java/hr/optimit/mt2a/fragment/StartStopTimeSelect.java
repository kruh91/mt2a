package hr.optimit.mt2a.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.R;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.timeSelect.TimeSelect;
import hr.optimit.mt2a.util.DateUtil;

/**
 * Created by tomek on 29.08.17..
 */
public class StartStopTimeSelect extends Fragment implements TimeSelect {

    @Inject
    DateUtil dateUtil;

    private Date startTime;
    private Date endTime;
    private TextView dateValue;
    private TextView startTimeValue;
    private TextView endTimeValue;
    private ImageButton startButton;
    private ImageButton stopButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((Mt2AApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_select_ss, container, false);
        dateValue = (TextView) view.findViewById(R.id.dateValue);
        startTimeValue = (TextView) view.findViewById(R.id.startTimeValue);
        endTimeValue = (TextView) view.findViewById(R.id.endTimeValue);
        startButton = (ImageButton) view.findViewById(R.id.start_time_button);
        startButton.setOnClickListener(startButtonOnClickListener);
        stopButton = (ImageButton) view.findViewById(R.id.stop_time_button);
        stopButton.setOnClickListener(stopButtonOnClickListener);

        return view;
    }

    private View.OnClickListener startButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startTime = new Date();
            dateValue.setText(dateUtil.getFormattedDate(startTime));
            startTimeValue.setText(dateUtil.getFormattedTime(startTime));
            startButton.setVisibility(View.GONE);
            startButton.setEnabled(false);
            dateValue.setVisibility(View.VISIBLE);
            startTimeValue.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener stopButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            endTime = new Date();
            endTimeValue.setText(dateUtil.getFormattedTime(endTime));
            stopButton.setVisibility(View.GONE);
            stopButton.setEnabled(false);
            endTimeValue.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void setTime(UtActivity utActivity) {

        if (startTime == null || endTime == null) {
            return;
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startTime);
        utActivity.setStartDate(calendar.getTime());

        calendar.setTime(endTime);
        utActivity.setEndDate(calendar.getTime());
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
