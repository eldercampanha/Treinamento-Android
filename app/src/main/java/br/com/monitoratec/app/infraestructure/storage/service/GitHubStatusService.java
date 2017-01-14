package br.com.monitoratec.app.infraestructure.storage.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rx.Observable;
import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Interface da API GitHub Status.
 *
 * Created by falvojr on 1/9/17.
 */
public interface GitHubStatusService {

    String BASE_URL = "https://status.github.com/api/";

    // setting date format
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

    // declaring retrofit
    Retrofit RETROFIT = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build();



    @GET("last-message.json")
    Observable<Status> lastMessage();
//    Call<Status> lastMessage();
}
