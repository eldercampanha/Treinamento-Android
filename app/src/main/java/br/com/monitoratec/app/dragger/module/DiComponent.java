package br.com.monitoratec.app.dragger.module;

/**
 * Created by elder-dell on 2017-01-12.
 */

import javax.inject.Singleton;

import br.com.monitoratec.app.dragger.module.module.infraestructure.ManagerModule;
import br.com.monitoratec.app.dragger.module.module.infraestructure.NetWorkModule;
import br.com.monitoratec.app.dragger.module.module.infraestructure.ServiceModule;
import br.com.monitoratec.app.dragger.module.module.presentations.HelperModule;
import br.com.monitoratec.app.dragger.module.module.ApplicationModule;
import br.com.monitoratec.app.dragger.module.module.PreferenceModule;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        HelperModule.class,
        PreferenceModule.class,
        NetWorkModule.class,
        ServiceModule.class,
        ManagerModule.class
})
public interface DiComponent {
    UiComponent uiComponent();
}