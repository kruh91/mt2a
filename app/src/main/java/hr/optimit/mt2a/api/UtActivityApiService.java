package hr.optimit.mt2a.api;

import java.util.List;

import hr.optimit.mt2a.model.UtActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tomek on 18.08.16..
 */
public interface UtActivityApiService {

    @GET("/mt2a/api/activities")
    Call<List<UtActivity>> findByActivityUserUserUsernameAndActivityStartDateBetween(@Query("username") String username,
                                                                                     @Query("startDate") String startDate, @Query("endDate") String endDate);

    @POST("/mt2a/api/activities/save")
    Call<RestResponse> saveActivity(@Body UtActivity utActivity);
}
