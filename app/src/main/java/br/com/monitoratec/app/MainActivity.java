package br.com.monitoratec.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView txtStatus;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        txtStatus = (TextView) this.findViewById(R.id.txtStatus);

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GitHubStatusApi.BASE_URL)
                .build();

        GitHubStatusApi statusApiImpl = retrofit.create(GitHubStatusApi.class);

        statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    Status status = response.body();
                    updateScreen(status);
                    Toast.makeText(MainActivity.this, status.status.name(), Toast.LENGTH_LONG).show();
                } else {

                    try {
                        String error = response.errorBody().string();
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                    } catch (IOException e){
                        Log.e(TAG, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateScreen(Status status) {

        this.txtStatus.setText(status.status.name());
    }

}
