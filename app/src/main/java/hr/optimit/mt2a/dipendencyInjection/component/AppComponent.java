package hr.optimit.mt2a.dipendencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
import hr.optimit.mt2a.activity.LoginActivity;
import hr.optimit.mt2a.activity.SingleFragmentActivity;
import hr.optimit.mt2a.activity.StartingActivity;
import hr.optimit.mt2a.dipendencyInjection.module.AppModule;
import hr.optimit.mt2a.fragment.StartStopTimeSelect;
import hr.optimit.mt2a.fragment.UtActivityFragment;
import hr.optimit.mt2a.fragment.UtActivityListFragment;
import hr.optimit.mt2a.oauth.OAuthUtil;
import hr.optimit.mt2a.service.UtAbstractService;
import hr.optimit.mt2a.service.UtActivityService;
import hr.optimit.mt2a.timeSelect.TimeSelect;
import hr.optimit.mt2a.util.DateUtil;

/**
 * Created by tomek on 21.08.16..
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    /**
     * Inject.
     *
     * @param userLoginTask the user login task
     */
    void inject(LoginActivity.UserLoginTask userLoginTask);

    /**
     * Inject.
     *
     * @param oAuthUtil the o auth util
     */
    void inject(OAuthUtil oAuthUtil);

    /**
     * Inject.
     *
     * @param utActivityService the ut activity service
     */
    void inject(UtActivityService utActivityService);

    /**
     * Inject.
     *
     * @param utAbstractService the ut abstract service
     */
    void inject(UtAbstractService utAbstractService);

    /**
     * Inject.
     *
     * @param task the task
     */
    void inject(StartingActivity.CheckUserStatusAsyncTask task);

    /**
     * Inject.
     *
     * @param fragment the fragment
     */
    void inject(UtActivityListFragment fragment);

    /**
     * Inject.
     *
     * @param util the util
     */
    void inject(DateUtil util);

    /**
     * Inject.
     *
     * @param fragment the fragment
     */
    void inject(UtActivityFragment fragment);

    /**
     * Inject.
     *
     * @param logOutAsyncTask the log out async task
     */
    void inject(SingleFragmentActivity.UserLogOutAsyncTask logOutAsyncTask);

    void inject(StartStopTimeSelect startStopTimeSelect);
}
