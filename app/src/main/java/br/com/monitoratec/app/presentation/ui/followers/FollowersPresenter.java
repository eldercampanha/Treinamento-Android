package br.com.monitoratec.app.presentation.ui.followers;

import br.com.monitoratec.app.domain.repository.GitHubRepository;

/**
 * Created by elder-dell on 2017-01-14.
 */

public class FollowersPresenter implements FollowersContract.Presenter{

    private FollowersContract.View mView;

    private GitHubRepository mGitHubRepository;

    public FollowersPresenter(GitHubRepository gitHubRepository) {
        mGitHubRepository = gitHubRepository;
    }

    @Override
    public void setView(FollowersContract.View view) {
        mView = view;
    }

    @Override
    public void callGetUser(String authorization) {

    }

    @Override
    public void callGetFollowers(String authorization) {
        mGitHubRepository.getFollowers(authorization)
                .subscribe(followers -> {
                    mView.onAuthSuccess(authorization, followers);
                }, error -> {
                    mView.showError(error.getMessage());
                });
    }
}
