package william.miranda.github.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import william.miranda.github.R;
import william.miranda.github.api.ApiResponse;
import william.miranda.github.api.GithubApi;
import william.miranda.github.model.Repository;
import william.miranda.github.ui.activities.RepositoryActivity;
import william.miranda.github.ui.adapters.RepositoryAdapter;
import william.miranda.github.ui.adapters.ItemClickListener;

/**
 * Fragmento para mostrar uma lista de Repositorios
 */
public class RepositoriesFragment extends Fragment {

    /**
     * Itens do Layout
     */
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView hint;

    /**
     * Click Listener que será chamado quando um item do RecyclerView for clicado
     */
    private ItemClickListener clickListener = new ItemClickListener<Repository>() {
        @Override
        public void onItemClickListener(Repository repository) {
            ((RepositoryActivity) getActivity()).displayDetails(repository);
        }
    };

    /**
     * Callback do onCreate
     * Apenas informamos que há um menu
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Callback chamada para inflar o Layout do nosso fragmento
     * Aproveitamos para bindar os itens do Layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        hint = (TextView) view.findViewById(R.id.hint);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RepositoryAdapter(clickListener));

        return view;
    }

    /**
     * Método para criar o menu
     * Como não estamos utilizando Content provider, podemos obter a
     * string de busca e chamar a Api diretamente
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_repository, menu);

        //Obtem o item do Menu e depois a view associada
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //Definimos o Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Ao clicar na busca, manda para a Api
                progressBar.setVisibility(View.VISIBLE);
                hint.setVisibility(View.GONE);
                getRepositories(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Método para fazer a chamada a Api
     * Podemos se necessário passar os QueryParams como parâmetro
     * De modo a não ferrar com a Main Thread, utilizamos a chamada assíncrona do Retrofit.
     * Poderiamos também colocar o bloco dentro de um AsyncTask, embora o Android possa matar
     * nossa Task em caso de mudança de orientação.
     */
    private void getRepositories(String query) {
        //Prepara os parâmetros
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q", query);

        //Faz a chamada
        Call<ApiResponse<Repository>> call = GithubApi.getInstance()
                .getRestInterface().getRepositories(queryMap);
        call.enqueue(new Callback<ApiResponse<Repository>>() {
            @Override
            public void onResponse(Call<ApiResponse<Repository>> call, Response<ApiResponse<Repository>> response) {
                //Se deu tudo certo, atualizamos o adapter
                progressBar.setVisibility(View.GONE);
                List<Repository> items = response.body().getItems();
                if (items != null && items.size() > 0) {
                    ((RepositoryAdapter) recyclerView.getAdapter()).swap(items);
                } else {
                    hint.setText(R.string.no_results_hint);
                    hint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Repository>> call, Throwable t) {
                //Se deu erro, imprimimos um Log e mostramos um Toast
                Log.e(getClass().getSimpleName(), t.getMessage());
                Toast.makeText(getContext(), R.string.api_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
