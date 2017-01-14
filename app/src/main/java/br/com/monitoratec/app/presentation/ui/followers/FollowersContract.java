package br.com.monitoratec.app.presentation.ui.followers;

import java.util.List;

import br.com.monitoratec.app.domain.entity.User;

/**
 * Created by elder-dell on 2017-01-14.
 */

public interface FollowersContract {

    interface View {

        void onAuthSuccess(String credential, List<User> followers);

        void showError(String message);
    }

    interface Presenter {

        void setView(FollowersContract.View view);

        void callGetUser(String authorization);

        void callGetFollowers(String authorization);
    }

}
