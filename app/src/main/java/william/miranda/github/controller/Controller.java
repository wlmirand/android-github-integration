package william.miranda.github.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import william.miranda.github.api.ApiResponse;
import william.miranda.github.api.GithubApi;
import william.miranda.github.model.Repository;
import william.miranda.github.model.User;

public class Controller {

    /**
     * Método para buscar Repositórios na Api
     * Podemos se necessário passar os QueryParams como parâmetro
     * De modo a não ferrar com a Main Thread, utilizamos a chamada assíncrona do Retrofit.
     * Poderiamos também colocar o bloco dentro de um AsyncTask, embora o Android possa matar
     * nossa Task em caso de mudança de orientação.
     */
    public void getRepositories(Callback callback, String query, int page, int perPage) {
        //Prepara os parâmetros
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q", query);
        queryMap.put("page", String.valueOf(page));
        queryMap.put("per_page", String.valueOf(perPage));

        //Faz a chamada
        Call<ApiResponse<Repository>> call = GithubApi.getInstance()
                .getRestInterface()
                .getRepositories(queryMap);
        call.enqueue(callback);
    }

    /**
     * Método para buscar Usuários na Api
     * Podemos se necessário passar os QueryParams como parâmetro
     * De modo a não ferrar com a Main Thread, utilizamos a chamada assíncrona do Retrofit.
     * Poderiamos também colocar o bloco dentro de um AsyncTask, embora o Android possa matar
     * nossa Task em caso de mudança de orientação.
     */
    public void getUsers(Callback callback, String query, int page, int perPage) {
        //Prepara os parâmetros
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q", query);
        queryMap.put("page", String.valueOf(page));
        queryMap.put("per_page", String.valueOf(perPage));

        //Faz a chamada
        Call<ApiResponse<User>> call = GithubApi.getInstance()
                .getRestInterface()
                .getUsers(queryMap);
        call.enqueue(callback);
    }

    /**
     * Obtém as informações do usuário logado
     * @param callback
     */
    public void getAuthUserInfo(Callback callback) {
        //Faz a chamada
        Call<User> call = GithubApi.getInstance()
                .getRestInterface()
                .getAuthUser();
        call.enqueue(callback);
    }

    /**
     * Abre a página do Github do repositório
     * Poderiamos também abrir uma Activity de Detalhes, onde deveriamos passar as informaçoes
     * através de um Intent (ou devemos persistir e passar um ID caso sejam muitos dados).
     * @param repository
     */
    public void displayRepositoryDetails(Context context, Repository repository) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(repository.getHtmlUrl()));
        context.startActivity(intent);
    }

    /**
     * Abre a página do Github do usuário
     * @param user
     */
    public void displayUserDetails(Context context, User user) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(user.getHtmlUrl()));
        context.startActivity(intent);
    }
}
