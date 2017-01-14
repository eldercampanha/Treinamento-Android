package br.com.monitoratec.app.dagger;

import br.com.monitoratec.app.dragger.module.module.presentations.PresenterModule;
import br.com.monitoratec.app.dragger.module.scope.PerActivity;
import br.com.monitoratec.app.presentation.ui.auth.AuthActivity;
import dagger.Subcomponent;

/**
 * Created by elder-dell on 2017-01-13.
 */

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface UiComponent {
    void inject(AuthActivity activity);
}