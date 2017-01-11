package br.com.monitoratec.app;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView txtStatus;
    private ImageView mImgVectorial;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding
        txtStatus = (TextView) this.findViewById(R.id.txtStatus);
        mImgVectorial = (ImageView) this.findViewById(R.id.imgVector);

        // setting date format
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();

        // declaring retrofit
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GitHubStatusApi.BASE_URL)
                .build();

        // starting API
        GitHubStatusApi statusApiImpl = retrofit.create(GitHubStatusApi.class);

        statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    Status status = response.body();
                    updateScreen(status.type.getColorId(),response.body().body);
                    Toast.makeText(MainActivity.this, status.type.name(), Toast.LENGTH_LONG).show();
                } else {

                    try {
                        String error = response.errorBody().string();
                        updateScreen(Status.Type.MAJOR.getColorId(), error);
                    } catch (IOException e){
                        Log.e(TAG, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                updateScreen(Status.Type.MAJOR.getColorId(), t.getMessage());
            }
        });
    }

    private void updateScreen(int colorRes, String message) {
        this.txtStatus.setText(message);
        int color = ContextCompat.getColor(MainActivity.this,colorRes);
        this.txtStatus.setTextColor(color);
        DrawableCompat.setTint(mImgVectorial.getDrawable(), color);
    }


    public void btnLoginClicked(View view) {


    }
}
