package william.miranda.github.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import william.miranda.github.R;
import william.miranda.github.api.GithubApi;
import william.miranda.github.api.RepositoryResponse;
import william.miranda.github.model.Repository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<RepositoryResponse> call = GithubApi.getInstance()
                .getRestInterface().getRepositories("tetris");
        call.enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                Log.d("BRUTUS", "Sucesso!!!");
                for (Repository repo : response.body().getItems()) {
                    Log.d("BRUTUS", repo.getName());
                }
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                Log.d("BRUTUS", "Fail!!!");
                Log.d("BRUTUS", t.getMessage());
            }
        });
    }
}
