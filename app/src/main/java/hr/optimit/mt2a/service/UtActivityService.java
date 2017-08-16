package hr.optimit.mt2a.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.api.RestResponse;
import hr.optimit.mt2a.api.UtActivityApiService;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.util.Constants;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tomek on 20.08.16..
 */
public class UtActivityService extends UtAbstractService {

    @Inject
    public UtActivityService() {
        super();
        Mt2AApplication.getComponent().inject(this);
    }

    public List<UtActivity> getActivities(Date fromDate, Date toDate) throws Exception {
        oAuthUtil.refreshTokenIfNeeded();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<UtActivity> activities = new ArrayList<>();
        UtActivityApiService apiService = restRetrofit.create(UtActivityApiService.class);
        Call<List<UtActivity>> call = apiService.findByActivityUserUserUsernameAndActivityStartDateBetween(sharedPreferences.getString(Constants.USERNAME, ""), sdf.format(fromDate), sdf.format(toDate));
        try {
            Response<List<UtActivity>> response = call.execute();
            activities.addAll(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activities;
    }

    public RestResponse saveActivity(UtActivity activity) throws Exception {
        RestResponse restResponse = null;
        oAuthUtil.refreshTokenIfNeeded();
        UtActivityApiService apiService = restRetrofit.create(UtActivityApiService.class);
        Call<RestResponse> call = apiService.saveActivity(activity);
        try {
            Response<RestResponse> response = call.execute();
            restResponse = response.body();
        } catch (IOException e) {
            e.printStackTrace();
            restResponse = new RestResponse();
            restResponse.setStatus(Constants.RESPONSE_STATUS_ERR);
            restResponse.setMessage("Greška kod spremanja aktivnosti.");
        }

        return restResponse;
    }
}