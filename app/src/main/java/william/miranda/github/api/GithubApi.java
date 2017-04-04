package william.miranda.github.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe para acessarmos o objeto do Retrofit
 * Foi implementado como Singleton pois custa caro instanciar o objeto do Retrofit
 * sempre que precisarmos da Api
 */
public class GithubApi
{
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    private static GithubApi instance;

    /**
     * Construtor onde inicializamos o objeto do Retrofit
     * e possíveis parâmetros caso necessário (Interceptors, Autenticação, ...)
     */
    private GithubApi()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    /**
     * Obtém a instância
     * @return
     */
    public static GithubApi getInstance()
    {
        if (instance == null) {
            instance = new GithubApi();
        }

        return instance;
    }

    /**
     * Getter para obter o objeto do Retrofit
     * @return
     */
    public ApiInterface getRestInterface() {
        return apiInterface;
    }
}
