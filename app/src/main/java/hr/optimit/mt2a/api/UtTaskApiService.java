package hr.optimit.mt2a.api;

import java.util.List;

import hr.optimit.mt2a.model.UtLocation;
import hr.optimit.mt2a.model.UtTask;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tomek on 18.08.16..
 */
public interface UtTaskApiService {

    @GET("/mt2a/api/tasks")
    Call<List<UtTask>> findTasks(@Query("projectId") Long projectId);
}
