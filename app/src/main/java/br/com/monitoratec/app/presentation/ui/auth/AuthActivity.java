package br.com.monitoratec.app.presentation.ui.auth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import br.com.monitoratec.app.R;
import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubOAuthService;
import br.com.monitoratec.app.presentation.base.BaseActivity;
import br.com.monitoratec.app.presentation.helper.AppHelper;
import br.com.monitoratec.app.presentation.ui.followers.FollowersActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.monitoratec.app.R.string.sp_credential_key;

public class AuthActivity extends BaseActivity implements AuthContract.View {

    @BindView(R.id.txtStatus)
    TextView txtStatus;
    @BindView(R.id.imgVector)
    ImageView mImageGitHubVectorial;
    @BindView(R.id.tilUsername)
    TextInputLayout usernameWrapper;
    @BindView(R.id.tilPassword)
    TextInputLayout passwordWrapper;

    private static final String TAG = AuthActivity.class.getSimpleName();

    @Inject
    SharedPreferences mSharedPreferences;
    @Inject AppHelper appHelper;
    @Inject AuthContract.Presenter mPresenter;

    @Override
    protected void onResume() {
        super.onResume();

        processOAuthRedirectUri(this);

        mPresenter.loadStatus();
    }

    private void updateScreen(Status.Type type) {

        this.txtStatus.setText(getString(type.getMessageId()));
        int color = ContextCompat.getColor(AuthActivity.this, type.getColorId());
        this.txtStatus.setTextColor(color);
        DrawableCompat.setTint(mImageGitHubVectorial.getDrawable(), color);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // binding
        ButterKnife.bind(this);
        getMyAppliation().getDaggerUiComponent().inject(this);

        mPresenter.setView(this);
        RxTextView.textChanges(usernameWrapper.getEditText())
                .skip(1) // Used to avoid being called in the first time
                // below code used for adding a delay of 2 seconds
                //.debounce(2, TimeUnit.SECONDS)
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(text ->{
                    appHelper.validateRequiredFields(usernameWrapper);
                }
        );
    }

    @OnClick(R.id.btn_login)
    public void loginClicked(View view) {
        hideKeyboard();
        if (appHelper.validateRequiredFields(usernameWrapper, passwordWrapper)) {
            String username = usernameWrapper.getEditText().getText().toString();
            String password = passwordWrapper.getEditText().getText().toString();
            final String credentials = okhttp3.Credentials.basic(username, password);

            mPresenter.callGetUser(credentials);
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @OnClick(R.id.btn_auth)
    public void btnAuthClicked(View view) {
        hideKeyboard();

        final String baseUrl = GitHubOAuthService.BASE_URL + "authorize";
        final String clientId = getString(R.string.oauth_client_id);
        final String redirectUri = getOAuthRedirectUri();
        final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private String getOAuthRedirectUri() {
        return getString(R.string.oauth_schema) + "://" + getString(R.string.oauth_host);
    }

    private void processOAuthRedirectUri(final AuthActivity view) {
        // Os intent-filter's permitem a interação com o ACTION_VIEW
        final Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(this.getOAuthRedirectUri())) {

            String code = uri.getQueryParameter("code");
            if (code != null) {

                //TODO Pegar o access token (Client ID, Client Secret e Code)
                String clientId = getString(R.string.oauth_client_id);
                String clientSecret = getString(R.string.oauth_client_secret);

                mPresenter.callAccessToken(clientId,clientSecret,code);

            } else if (uri.getQueryParameter("error") != null) {
                showError(uri.getQueryParameter("error"));
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

    @Override
    public void onLoadStatusType(Status.Type statusType) {
        updateScreen(statusType);
    }

    @Override
    public void onAuthSuccess(String credential, User user) {
                        String credentialsKey = getString(sp_credential_key);
                        mSharedPreferences.edit()
                                .putString(credentialsKey, credential)
                                .apply(); // using apply because it is sync

        Intent intent =  new Intent(AuthActivity.this, FollowersActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mImageGitHubVectorial, message, Snackbar.LENGTH_LONG).show();
    }
}
