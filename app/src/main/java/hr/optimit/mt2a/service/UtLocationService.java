package hr.optimit.mt2a.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.api.UtActivityApiService;
import hr.optimit.mt2a.api.UtLocationApiService;
import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.model.UtLocation;
import hr.optimit.mt2a.util.Constants;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tomek on 20.08.16..
 */
public class UtLocationService extends UtAbstractService {

    /**
     * Instantiates a new Ut location service.
     */
    @Inject
    public UtLocationService() {
        super();
        Mt2AApplication.getComponent().inject(this);
    }

    /**
     * Gets locations.
     *
     * @param partnerId the partner id
     * @return the locations
     * @throws Exception the exception
     */
    public List<UtLocation> getLocations(Long partnerId) throws Exception {
        oAuthUtil.refreshTokenIfNeeded();
        List<UtLocation> locations = new ArrayList<>();
        UtLocationApiService apiService = restRetrofit.create(UtLocationApiService.class);
        Call<List<UtLocation>> call = apiService.findLocations(partnerId);
        try {
            Response<List<UtLocation>> response = call.execute();
            locations.addAll(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locations;
    }

}