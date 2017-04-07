package william.miranda.github.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import william.miranda.github.api.ApiResponse;
import william.miranda.github.api.GithubApi;
import william.miranda.github.model.Repository;
import william.miranda.github.model.User;
import william.miranda.github.ui.activities.RepositoryDetailActivity;

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
     * Mostra os detalhes de um dado Repositório
     * @param repository
     */
    public void displayRepositoryDetails(Context context, Repository repository) {
        /* Como iremos mostrar poucos dados, passaremos todos eles em um Bundle.
            Poderiamos também fazer nosso POJO implementar a interface Parcelable e passar o objeto inteiro,
            porém como podemos ter muitos dados, levaria a um TooLargeTransactionException por causa
            das restrições do Binder (1MB de dados no máximo).
            Se fosse necessário passar um volume grande de dados, deveriamos salvar os Dados em um
            DB ou arquivo e passar apenas a referência para o Fragment, que ficaria responsável por
            obter o conjunto completo dos dados.
            Também poderíamos passar apenas o ID e fazer uma nova busca na Api
         */
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra("name", repository.getName());
        intent.putExtra("owner", repository.getOwner().getLogin());
        intent.putExtra("description", repository.getDescription());
        context.startActivity(intent);
    }

    /**
     * Mostra os detalhes de um dado Usuário
     * @param user
     */
    public void displayUserDetails(Context context, User user) {
        /* Como iremos mostrar poucos dados, passaremos todos eles em um Bundle.
            Poderiamos também fazer nosso POJO implementar a interface Parcelable e passar o objeto inteiro,
            porém como podemos ter muitos dados, levaria a um TooLargeTransactionException por causa
            das restrições do Binder (1MB de dados no máximo).
            Se fosse necessário passar um volume grande de dados, deveriamos salvar os Dados em um
            DB ou arquivo e passar apenas a referência para o Fragment, que ficaria responsável por
            obter o conjunto completo dos dados.
            Também poderíamos passar apenas o ID e fazer uma nova busca na Api
         */
//        Intent intent = new Intent(context, UserDetailActivity.class);
//        intent.putExtra("name", user.getLogin());
//        intent.putExtra("avatarUrl", user.getAvatarUrl());
//        context.startActivity(intent);
    }
}
