package br.com.monitoratec.app.dragger.module.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.monitoratec.app.R;
import dagger.Module;
import dagger.Provides;

/**
 * Created by elder-dell on 2017-01-12.
 */

@Module
public class PreferenceModule {

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        final String fileName = context.getString(R.string.sp_file_key);
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

//    @Provides
//    @Singleton
//    @Named("default")
//    SharedPreferences providesSharedPreferencesDefault(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context);
//    }

}