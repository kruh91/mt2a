package hr.optimit.mt2a.api;

import java.util.List;

import hr.optimit.mt2a.model.UtActivity;
import hr.optimit.mt2a.model.UtLocation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tomek on 18.08.16..
 */
public interface UtLocationApiService {

    @GET("/mt2a/api/locations")
    Call<List<UtLocation>> findLocations(@Query("partnerId") Long partnerId);
}
