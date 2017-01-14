package br.com.monitoratec.app.domain.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Interface da API GitHub Status.
 *
 * Created by falvojr on 1/9/17.
 */
public interface GitHubStatusRepository {
    Observable<Status> lastMessage();
}
