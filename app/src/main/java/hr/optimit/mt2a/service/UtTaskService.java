package hr.optimit.mt2a.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.api.UtTaskApiService;
import hr.optimit.mt2a.model.UtTask;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tomek on 20.08.16..
 */
public class UtTaskService extends UtAbstractService {

    @Inject
    public UtTaskService() {
        super();
        Mt2AApplication.getComponent().inject(this);
    }

    public List<UtTask> getTasks(Long projectId) throws Exception {
        oAuthUtil.refreshTokenIfNeeded();
        List<UtTask> tasks = new ArrayList<>();
        UtTaskApiService apiService = restRetrofit.create(UtTaskApiService.class);
        Call<List<UtTask>> call = apiService.findTasks(projectId);
        try {
            Response<List<UtTask>> response = call.execute();
            tasks.addAll(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

}