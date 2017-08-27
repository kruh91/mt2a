package hr.optimit.mt2a.api;

import java.util.List;

import hr.optimit.mt2a.model.UtLocation;
import hr.optimit.mt2a.model.UtProject;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tomek on 18.08.16..
 */
public interface UtProjectApiService {

    /**
     * Find projects call.
     *
     * @return the call
     */
    @GET("/mt2a/api/projects")
    Call<List<UtProject>> findProjects();
}
