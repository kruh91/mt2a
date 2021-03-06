package hr.optimit.mt2a.util;

import android.content.SharedPreferences;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;

/**
 * Created by tomek on 20.07.17..
 */
public class DateUtil {

    /**
     * The Shared preferences.
     */
    @Inject
    SharedPreferences sharedPreferences;

    /**
     * Instantiates a new Date util.
     */
    @Inject
    public DateUtil() {
        Mt2AApplication.getComponent().inject(this);
    }

    /**
     * Gets start date with day precision.
     *
     * @param date the date
     * @return the start date with day precision
     */
    public Date getStartDateWithDayPrecision(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendarToBeginingOfDay(calendar);

        return calendar.getTime();
    }

    /**
     * Sets calendar to begining of day.
     *
     * @param calendar the calendar
     */
    public void setCalendarToBeginingOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Gets end date with day precision.
     *
     * @param date the date
     * @return the end date with day precision
     */
    public Date getEndDateWithDayPrecision(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalendarToEndOfDay(calendar);

        return calendar.getTime();
    }

    /**
     * Sets calendar to end of day.
     *
     * @param calendar the calendar
     */
    public void setCalendarToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    /**
     * Gets day in past.
     *
     * @param date       the date
     * @param daysInPast the days in past
     * @return the day in past
     */
    public Date getDayInPast(Date date, int daysInPast) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -daysInPast);

        return getStartDateWithDayPrecision(calendar.getTime());
    }

    /**
     * Gets default start and end date.
     *
     * @return the default start and end date
     */
    public Pair<Date, Date> getDefaultStartAndEndDate() {
        Date endDate = getStartDateWithDayPrecision(new Date());
        Date startDate = getDayInPast(endDate, PropertiesHelper.getDefaultDayRange());

        return Pair.create(startDate, endDate);
    }

    /**
     * Gets start and end date.
     *
     * @return the start and end date
     */
    public Pair<Date, Date> getStartAndEndDate() {
        Pair<Date, Date> defaultStartAndEndDate = getDefaultStartAndEndDate();
        Long startDateInMilis = sharedPreferences.getLong(Constants.LIST_START_DATE, 0L);
        Date startDate = startDateInMilis.longValue() == 0l ? defaultStartAndEndDate.first : new Date(startDateInMilis);
        Long endDateInMilis = sharedPreferences.getLong(Constants.LIST_END_DATE, 0L);
        Date endDate = endDateInMilis.longValue() == 0l ? defaultStartAndEndDate.second : new Date(endDateInMilis);

        return Pair.create(startDate, endDate);
    }

    /**
     * Update start date.
     *
     * @param startDate the start date
     */
    public void updateStartDate(Date startDate) {
        storeDateInSharedPreferences(Constants.LIST_START_DATE, startDate);
    }

    /**
     * Update end date.
     *
     * @param endDate the end date
     */
    public void updateEndDate(Date endDate) {
        storeDateInSharedPreferences(Constants.LIST_END_DATE, endDate);
    }

    /**
     * Gets formatted date.
     *
     * @param date the date
     * @return the formatted date
     */
    public String getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    /**
     * Gets formatted time.
     *
     * @param date the date
     * @return the formatted time
     */
    public String getFormattedTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    /**
     * Sets ut activity start time.
     *
     * @param date the date
     */
    public void setUtActivityStartTime(Date date) {
        storeDateInSharedPreferences(Constants.UT_ACTIVITY_START_TIME, date);
    }

    private void storeDateInSharedPreferences(String name, Date date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (date == null) {
            editor.remove(name);
        } else {
            editor.putLong(name, date.getTime());
        }

        editor.commit();
    }

    /**
     * Gets stored ut activity start time.
     *
     * @return the stored ut activity start time
     */
    public Date getStoredUtActivityStartTime() {
        long timeInMilis = sharedPreferences.getLong(Constants.UT_ACTIVITY_START_TIME, 0l);
        if (timeInMilis == 0) {
            return null;
        }

        return new Date(timeInMilis);
    }

    /**
     * Is time measure started boolean.
     *
     * @return the boolean
     */
    public boolean isTimeMeasureStarted() {
        return getStoredUtActivityStartTime() != null;
    }
}
