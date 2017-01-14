package br.com.monitoratec.app;

import android.app.Application;

import br.com.monitoratec.app.dragger.module.UiComponent;
import br.com.monitoratec.app.dragger.module.module.ApplicationModule;
import br.com.monitoratec.app.dragger.module.DaggerDiComponent;
import br.com.monitoratec.app.dragger.module.DiComponent;

/**
 * Created by elder-dell on 2017-01-12.
 */

public class MyApplication extends Application {

    private DiComponent mDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDiComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public UiComponent getDaggerUiComponent() {
        return mDiComponent.uiComponent();
    }


}