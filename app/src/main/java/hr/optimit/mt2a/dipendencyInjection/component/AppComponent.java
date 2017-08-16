package hr.optimit.mt2a.dipendencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
import hr.optimit.mt2a.activity.LoginActivity;
import hr.optimit.mt2a.activity.StartingActivity;
import hr.optimit.mt2a.dipendencyInjection.module.AppModule;
import hr.optimit.mt2a.fragment.UtActivityFragment;
import hr.optimit.mt2a.fragment.UtActivityListFragment;
import hr.optimit.mt2a.oauth.OAuthUtil;
import hr.optimit.mt2a.service.UtAbstractService;
import hr.optimit.mt2a.service.UtActivityService;
import hr.optimit.mt2a.util.DateUtil;

/**
 * Created by tomek on 21.08.16..
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(LoginActivity.UserLoginTask userLoginTask);

    void inject(OAuthUtil oAuthUtil);

    void inject(UtActivityService utActivityService);

    void inject(UtAbstractService utAbstractService);

    void inject(StartingActivity.CheckUserStatusAsyncTask task);

    void inject(UtActivityListFragment fragment);

    void inject(DateUtil util);

    void inject(UtActivityFragment fragment);
}
