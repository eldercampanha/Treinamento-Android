package br.com.monitoratec.app.presentation.ui.list;

import br.com.monitoratec.app.domain.entity.User;

/**
 * Created by elder-dell on 2017-01-13.
 */

public interface RepositoryListContract {

    interface View {

        void onLoadCompleted(User user);

        void showError(String message);
    }

    interface Presenter {

        void setView(RepositoryListContract.View view);

        void callGetUser(String authorization);

        void callAccessToken(String cliId, String cliSecret, String code);
    }
}