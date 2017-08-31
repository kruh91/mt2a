package hr.optimit.mt2a.timeSelect;

import android.view.LayoutInflater;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.optimit.mt2a.R;
import hr.optimit.mt2a.fragment.UtActivityListFragment;
import hr.optimit.mt2a.model.UtActivity;

/**
 * Created by tomek on 31.08.17..
 */
public class TimeSelectManager {
    private static TimeSelectManager ourInstance = new TimeSelectManager();

    public static TimeSelectManager getInstance() {
        return ourInstance;
    }

    private LayoutInflater inflater;
    private FloatingActionsMenu floatingActionsMenu;
    private UtActivityListFragment listFragment;
    private Integer defaultTimeSelectId;

    private List<TimeSelect> timeSelectOptionList = new ArrayList<>();

    private TimeSelectManager() {
    }

    public TimeSelect getTimeSelect(int id) {
        return timeSelectOptionList.get(id);
    }


    public void addTimeSelect(TimeSelect timeSelect) {
        final int timeSelectId = timeSelectOptionList.size();
        this.timeSelectOptionList.add(timeSelect);
        FloatingActionButton fab = (FloatingActionButton) inflater.inflate(R.layout.floating_action_button, null);
        fab.setIcon(timeSelect.getIcon());
        fab.setId(timeSelectId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFragment.invokeCreateNewActivity(timeSelectId);
            }
        });

        floatingActionsMenu.addButton(fab);

        if(defaultTimeSelectId == null) {
            defaultTimeSelectId = timeSelectId;
        }
    }


    public void setDependencies(LayoutInflater inflater, FloatingActionsMenu floatingActionsMenu, UtActivityListFragment listFragment) {
        this.inflater = inflater;
        this.floatingActionsMenu = floatingActionsMenu;
        this.listFragment = listFragment;
    }

    public Integer getDefaultTimeSelectId() {
        return defaultTimeSelectId;
    }
}
