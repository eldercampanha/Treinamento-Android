package br.com.monitoratec.app;

import android.support.v7.app.AppCompatActivity;

import br.com.monitoratec.app.dragger.module.DiComponent;

/**
 * Created by elder-dell on 2017-01-12.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected MyApplication getMyAppliation(){
        return (MyApplication) getApplication();
    }

    protected DiComponent getDaggerDiComponent(){
        return this.getMyAppliation().getDaggerDiComponent();
    }

}
