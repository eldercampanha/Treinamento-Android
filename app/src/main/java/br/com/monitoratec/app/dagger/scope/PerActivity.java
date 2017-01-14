package br.com.monitoratec.app.dragger.module.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by elder-dell on 2017-01-13.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity { }