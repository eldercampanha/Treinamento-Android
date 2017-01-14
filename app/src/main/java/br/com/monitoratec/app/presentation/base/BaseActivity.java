package br.com.monitoratec.app.presentation.base;

import android.support.v7.app.AppCompatActivity;

import br.com.monitoratec.app.MyApplication;
import br.com.monitoratec.app.dragger.module.DiComponent;
import br.com.monitoratec.app.dragger.module.UiComponent;

/**
 * Created by elder-dell on 2017-01-12.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected MyApplication getMyAppliation(){
        return (MyApplication) getApplication();
    }

    protected UiComponent getDaggerUiComponent(){
        return this.getMyAppliation().getDaggerUiComponent();
    }

}
