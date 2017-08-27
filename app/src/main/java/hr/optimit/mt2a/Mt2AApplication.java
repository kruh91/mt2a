package hr.optimit.mt2a;

import android.app.Application;

import hr.optimit.mt2a.dipendencyInjection.component.AppComponent;
import hr.optimit.mt2a.dipendencyInjection.component.DaggerAppComponent;
import hr.optimit.mt2a.dipendencyInjection.module.AppModule;

/**
 * Created by tomek on 21.08.16..
 */
public class Mt2AApplication extends Application {

    private static AppComponent component;

    /**
     * Gets component.
     *
     * @return the component
     */
    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
