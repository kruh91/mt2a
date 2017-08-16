package hr.optimit.mt2a.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.api.UtProjectApiService;
import hr.optimit.mt2a.model.UtProject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tomek on 20.08.16..
 */
public class UtProjectService extends UtAbstractService {

    @Inject
    public UtProjectService() {
        super();
        Mt2AApplication.getComponent().inject(this);
    }

    public List<UtProject> getProjects() throws Exception {
        oAuthUtil.refreshTokenIfNeeded();
        List<UtProject> projects = new ArrayList<>();
        UtProjectApiService apiService = restRetrofit.create(UtProjectApiService.class);
        Call<List<UtProject>> call = apiService.findProjects();
        try {
            Response<List<UtProject>> response = call.execute();
            projects.addAll(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projects;
    }

}