package br.com.monitoratec.app.domain.repository;

import br.com.monitoratec.app.domain.entity.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by elder-dell on 2017-01-11.
 */

public interface GitHubRepository {
    Observable<User> getUser(String credential);
}
