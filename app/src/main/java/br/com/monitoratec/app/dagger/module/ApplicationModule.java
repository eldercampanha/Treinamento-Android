package br.com.monitoratec.app.dragger.module.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by elder-dell on 2017-01-12.
 */

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application app) { mApplication = app; }

    @Provides
    @Singleton
    Application providesApplication() { return mApplication; }

    @Provides
    @Singleton
    Context providesContext(Application app) { return app.getBaseContext(); }
}