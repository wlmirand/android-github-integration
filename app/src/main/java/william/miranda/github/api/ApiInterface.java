package william.miranda.github.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface do Retrofit contendo os endpoints da Api
 */
public interface ApiInterface {
    @GET("search/repositories")
    Call<RepositoryResponse> getRepositories(@Query("q") String query);
}
