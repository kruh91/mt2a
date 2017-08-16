package hr.optimit.mt2a.util;

import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomek on 15.08.17..
 */
public class ComponentUtil {

    public static void setTimePickerInterval(TimePicker timePicker) {
        try {
            int interval = PropertiesHelper.getTimpickerMinutesInterval();
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            // Field timePickerField = classForid.getField("timePicker");

            Field field = classForid.getField("minute");
            NumberPicker minutePicker = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));

            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(7);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += interval) {
                displayedValues.add(String.format("%02d", i));
            }
            for (int i = 0; i < 60; i += interval) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues
                    .toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
