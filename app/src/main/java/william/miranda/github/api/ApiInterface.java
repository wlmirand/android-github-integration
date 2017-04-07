package william.miranda.github.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import william.miranda.github.model.Commit;
import william.miranda.github.model.Repository;
import william.miranda.github.model.User;

/**
 * Interface do Retrofit contendo os endpoints da Api
 */
public interface ApiInterface {
    @GET("search/repositories")
    Call<ApiResponse<Repository>> getRepositories(@QueryMap Map<String, String> queryMap);

    @GET("search/users")
    Call<ApiResponse<User>> getUsers(@QueryMap Map<String, String> queryMap);

    @GET("user")
    Call<User> getAuthUser();

    @GET("search/commits")
    Call<ApiResponse<Commit>> getCommits(@QueryMap Map<String, String> queryMap);
}
