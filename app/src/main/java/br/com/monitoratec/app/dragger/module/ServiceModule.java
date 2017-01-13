package br.com.monitoratec.app.dragger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.monitoratec.app.domain.GitHubApi;
import br.com.monitoratec.app.domain.GitHubOAuthApi;
import br.com.monitoratec.app.domain.GitHubStatusApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by elder-dell on 2017-01-12.
 */

@Module
public class ServiceModule {

    static final String RETROFIT_GITHUB = "GitHub";
    static final String RETROFIT_GITHUB_STATUS = "GitHubStatus";
    static final String RETROFIT_GITHUB_OAUTH = "GitHubOAuth";


    @Singleton
    @Provides
    GitHubApi providesGitHub(
            @Named(RETROFIT_GITHUB) Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }

    @Singleton
    @Provides
    GitHubStatusApi providesGitHubStatus(
            @Named(RETROFIT_GITHUB_STATUS) Retrofit retrofit) {
        return retrofit.create(GitHubStatusApi.class);
    }

    @Singleton
    @Provides
    GitHubOAuthApi providesGitHubOAuth(
            @Named(RETROFIT_GITHUB_OAUTH) Retrofit retrofit) {
        return retrofit.create(GitHubOAuthApi.class);
    }

}