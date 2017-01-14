package br.com.monitoratec.app.presentation.ui.followers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import br.com.monitoratec.app.R;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.presentation.base.BaseActivity;
import br.com.monitoratec.app.presentation.ui.followers.adapter.FollowerListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.monitoratec.app.R.string.sp_credential_key;

public class FollowersActivity extends BaseActivity implements FollowersContract.View{

    private static final String TAG = FollowersActivity.class.getSimpleName();
    private RecyclerView.LayoutManager mLayoutManager;

    @Inject FollowersContract.Presenter mPresenter;
    @Inject SharedPreferences mSharedPreferences;

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private FollowerListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        // binding
        ButterKnife.bind(this);
        getMyAppliation().getDaggerUiComponent().inject(this);
        mPresenter.setView(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        String credentialsKey = getString(sp_credential_key);
        mPresenter.callGetFollowers(mSharedPreferences.getString(credentialsKey, ""));
    }

    @Override
    public void onAuthSuccess(String credential, List<User> followers) {

        String credentialsKey = getString(sp_credential_key);
        mPresenter.callGetFollowers(credentialsKey);


        // set Recycler View
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FollowerListAdapter(followers);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showError(String message) {

    }
}
