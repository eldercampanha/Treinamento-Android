package br.com.monitoratec.app.presentation.ui.auth;

import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;

/**
 * Created by elder-dell on 2017-01-13.
 */

public interface AuthContract {

    interface View {

        void onLoadStatusType(Status.Type statusType);

        void onAuthSuccess(String credential, User entity);

        void showError(String message);
    }

    interface Presenter {
        void setView(AuthContract.View view);

        void loadStatus();

        void callGetUser(String authorization);

        void callAccessToken(String cliId, String cliSecret, String code);
    }
}