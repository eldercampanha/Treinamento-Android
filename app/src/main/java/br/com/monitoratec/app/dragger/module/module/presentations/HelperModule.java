package br.com.monitoratec.app.dragger.module.module.presentations;

import android.content.Context;

import br.com.monitoratec.app.presentation.helper.AppHelper;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Created by elder-dell on 2017-01-13.
 */

@Module
public class HelperModule {
    @Provides
    @Reusable
    AppHelper provideTextHelper(Context context) {
        return new AppHelper(context);
    }


}