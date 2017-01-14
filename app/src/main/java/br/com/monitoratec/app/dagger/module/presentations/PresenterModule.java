package br.com.monitoratec.app.dragger.module.module.presentations;

import br.com.monitoratec.app.domain.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.domain.repository.GitHubRepository;
import br.com.monitoratec.app.domain.repository.GitHubStatusRepository;
import br.com.monitoratec.app.dragger.module.scope.PerActivity;
import br.com.monitoratec.app.presentation.ui.auth.AuthContract;
import br.com.monitoratec.app.presentation.ui.auth.AuthPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by elder-dell on 2017-01-13.
 */

@Module
public class PresenterModule {

    @PerActivity
    @Provides
    AuthContract.Presenter provideMainPresenter(
            GitHubRepository gitHubRepository,
            GitHubStatusRepository gitHubStatusRepository,
            GitHubOAuthRepository gitHubOAuthRepository) {
        return new AuthPresenter(gitHubRepository,
                gitHubStatusRepository,
                gitHubOAuthRepository);
    }
}