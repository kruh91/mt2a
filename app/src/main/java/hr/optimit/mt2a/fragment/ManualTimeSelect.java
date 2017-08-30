package hr.optimit.mt2a.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import hr.optimit.mt2a.R;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.timeSelect.TimeSelect;

/**
 * Created by tomek on 29.08.17..
 */
public class ManualTimeSelect extends Fragment implements TimeSelect {

    private DatePicker datePicker;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_select_manual, container, false);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        startTimePicker = (TimePicker) view.findViewById(R.id.startTimePicker);
        startTimePicker.setIs24HourView(true);
        endTimePicker = (TimePicker) view.findViewById(R.id.endTimePicker);
        endTimePicker.setIs24HourView(true);

        return view;
    }

    @Override
    public void setTime(UtActivity utActivity) {

        Calendar calendar = Calendar.getInstance();
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
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}
