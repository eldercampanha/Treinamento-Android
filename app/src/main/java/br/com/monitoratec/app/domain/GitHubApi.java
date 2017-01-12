package br.com.monitoratec.app.domain;

import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by elder-dell on 2017-01-11.
 */

public interface GitHubApi {

    String BASE_URL = "https://status.github.com/";

    // declaring retrofit
    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GitHubStatusApi.BASE_URL)
            .build();


    @GET("user")
    Call<User> basicAuth(@Header("Authorization") String credential);

}
