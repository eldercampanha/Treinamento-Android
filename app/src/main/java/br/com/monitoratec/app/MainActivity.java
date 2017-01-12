package br.com.monitoratec.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.monitoratec.app.domain.GitHubOAuthApi;
import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.GitHubApi;
import br.com.monitoratec.app.domain.entity.AccessToken;
import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.util.MySubscriber;
import br.com.monitoratec.app.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static br.com.monitoratec.app.R.string.sp_credential_key;
import static br.com.monitoratec.app.R.string.sp_file;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.imgVector)
    ImageView mImgVectorial;
    @BindView(R.id.tilUsername)
    TextInputLayout usernameWrapper;
    @BindView(R.id.tilPassword)
    TextInputLayout passwordWrapper;

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubStatusApi statusApiImpl;
    private GitHubApi gitHubApi;
    private SharedPreferences mSharedPreferences;
    private GitHubOAuthApi gitHubOAuthApi;

    @Override
    protected void onResume() {
        super.onResume();

        processOAuthRedirectUri(this);

        statusApiImpl.lastMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
                updateScreen(Status.Type.MAJOR);
            }

            @Override
            public void onNext(Status status) {
                updateScreen(status.type);
            }
        });
//        statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                if (response.isSuccessful()) {
//                    Status status = response.body();
//                    updateScreen(status.type.getColorId(), response.body().body);
//                } else {
//
//                    try {
//                        String error = response.errorBody().toString();
//                        updateScreen(Status.Type.MAJOR.getColorId(), error);
//                    } catch (Exception e) {
//                        Log.e(TAG, e.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                updateScreen(Status.Type.MAJOR.getColorId(), t.getMessage());
//            }
//        });
    }

    private void updateScreen(Status.Type type) {

        this.txtStatus.setText(getString(type.getMessageId()));
        int color = ContextCompat.getColor(MainActivity.this, type.getColorId());
        this.txtStatus.setTextColor(color);
        DrawableCompat.setTint(mImgVectorial.getDrawable(), color);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding
        ButterKnife.bind(this);

        // starting API
        statusApiImpl = GitHubStatusApi.RETROFIT.create(GitHubStatusApi.class);
        gitHubApi = GitHubApi.RETROFIT.create(GitHubApi.class);
        gitHubOAuthApi = GitHubOAuthApi.RETROFIT.create(GitHubOAuthApi.class);

        mSharedPreferences = getSharedPreferences(getString(sp_file), MODE_PRIVATE);
        RxTextView.textChanges(usernameWrapper.getEditText())
                .skip(1) // Used to avoid being called in the first time
                // below code used for adding a delay of 2 seconds
                //.debounce(2, TimeUnit.SECONDS)
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(text ->{
                    Util.validateRequiredFields(this,usernameWrapper);
                }
        );
    }

    @OnClick(R.id.btn_login)
    public void loginClicked(View view) {
        hideKeyboard();
        if (Util.validateRequiredFields(this, usernameWrapper, passwordWrapper)) {
            doLogin(view);
        }
    }


    private void doLogin(final View view) {

        String username = usernameWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();
        final String credentials = okhttp3.Credentials.basic(username, password);


        gitHubApi.basicAuth(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<User>() {
                    @Override
                    public void onNext(User user) {
                        String credentialsKey = getString(sp_credential_key);
                        mSharedPreferences.edit()
                                .putString(credentialsKey, credentials)
                                .apply(); // using apply because it is sync
                        Snackbar.make(view, user.login, Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    protected void onError(String message) {

                    }
                });

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void btnAuthClicked(View view) {
        hideKeyboard();

        final String baseUrl = GitHubOAuthApi.BASE_URL + "authorize";
        final String clientId = getString(R.string.oauth_client_id);
        final String redirectUri = getOAuthRedirectUri();
        final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private String getOAuthRedirectUri() {
        return getString(R.string.oauth_schema) + "://" + getString(R.string.oauth_host);
    }

    private void processOAuthRedirectUri(final MainActivity view) {
        // Os intent-filter's permitem a interação com o ACTION_VIEW
        final Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(this.getOAuthRedirectUri())) {

            String code = uri.getQueryParameter("code");
            if (code != null) {

                //TODO Pegar o access token (Client ID, Client Secret e Code)
                String clientId = getString(R.string.oauth_client_id);
                String clientSecret = getString(R.string.oauth_client_secret);

                gitHubOAuthApi.accessToken(clientId, clientSecret, code)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<AccessToken>() {
                            @Override
                            public void onNext(AccessToken accessToken) {
                                String credentialKey = getString(R.string.sp_credential_key);
                                mSharedPreferences.edit()
                                        .putString(credentialKey, accessToken.getAuthCredential())
                                        .apply();
                                txtStatus.setText(accessToken.access_token);
                            }

                            @Override
                            protected void onError(String message) {
                                updateScreen(Status.Type.MAJOR);
                            }
                        });

            } else if (uri.getQueryParameter("error") != null) {
                //TODO Tratar erro
            }

            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.call_item:
                final String phoneNumber = "+55 16 9972-1123";
                final Uri url = Uri.fromParts("tel", phoneNumber, null);

                //Don`t need permission
                //Intent intent = new Intent(Intent.ACTION_DIAL, url);
                Intent intent = new Intent(Intent.ACTION_CALL, url);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return true;
                }
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
