package br.com.monitoratec.app.infraestructure.storage.manager;

import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Inject;

import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.domain.repository.GitHubRepository;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by elder-dell on 2017-01-13.
 */

public class GitHubManager implements GitHubRepository {

    private final GitHubService mGitHubService;
    private final SharedPreferences mSharedPreferences;

    @Inject
    public GitHubManager(GitHubService gitHubService, SharedPreferences sp) {
        mGitHubService = gitHubService;
        mSharedPreferences = sp;
    }

    @Override
    public Observable<User> getUser(String authorization) {
        return mGitHubService.basicAuth(authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<User>> getFollowers(String authorization) {
        return mGitHubService.followers(authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void savePreferences(String token) {
        mSharedPreferences.edit().putString("KEY", token).apply();

    }
}