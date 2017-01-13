package br.com.monitoratec.app.dragger.module;

/**
 * Created by elder-dell on 2017-01-12.
 */

import javax.inject.Singleton;

import br.com.monitoratec.app.MainActivity;
import dagger.Component;
import dagger.Provides;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        PreferenceModule.class,
        NetWorkModule.class,
        ServiceModule.class
})
public interface DiComponent {
    void inject(MainActivity activity);
}