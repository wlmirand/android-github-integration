package william.miranda.github.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import william.miranda.github.R;
import william.miranda.github.api.ApiResponse;
import william.miranda.github.controller.Controller;
import william.miranda.github.model.Repository;
import william.miranda.github.ui.adapters.GenericPagerAdapter;
import william.miranda.github.ui.adapters.ItemClickListener;
import william.miranda.github.ui.adapters.RepositoryAdapter;

/**
 * Fragmento para mostrar uma lista de Repositorios em uma dada página
 */
public class RepositoryFragment extends Fragment {

    /**
     * Itens do Layout
     */
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    /**
     * Número da página atual
     */
    private int pageNumber;

    /**
     * String a ser buscada
     */
    private String query;

    /**
     * Callback para tratar o retorno da Api
     */
    private Callback<ApiResponse<Repository>> callback = new Callback<ApiResponse<Repository>>() {
        @Override
        public void onResponse(Call<ApiResponse<Repository>> call, Response<ApiResponse<Repository>> response) {
            //Se chegou algum response, analisamos...
            switch (response.code()) {
                case 200://Tudo OK
                    progressBar.setVisibility(View.GONE);

                    int totalCount = Math.min(response.body().getTotalCount(), 1000);
                    int numPerPage = response.body().getItems().size();
                    int numPages = (int) Math.ceil(totalCount / (float) numPerPage);

                    //Se necessário, atualiza o viewPager
                    GenericPagerAdapter pagerAdapter = ((GenericPagerAdapter) viewPager.getAdapter());
                    if (pagerAdapter.updateNumPages(numPages)) {
                        pagerAdapter.notifyDataSetChanged();
                    }

                    //Atualiza o RecyclerView com os dados
                    ((RepositoryAdapter) recyclerView.getAdapter()).swap(response.body().getItems());
                    break;

                case 403://Forbidden: Provavelmente excedemos o Limite da Api
                    Toast.makeText(getContext(), R.string.return_error, Toast.LENGTH_LONG).show();
                    break;
            }
        }

        @Override
        public void onFailure(Call<ApiResponse<Repository>> call, Throwable t) {
            //Erro na chamada do backend
            Toast.makeText(getContext(), R.string.api_error, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Click Listener que será chamado quando um item do RecyclerView for clicado
     */
    private ItemClickListener clickListener = new ItemClickListener<Repository>() {
        @Override
        public void onItemClickListener(Repository repository) {
            new Controller().displayRepositoryDetails(getContext(), repository);
        }
    };

    /**
     * Container onde nosso fragmento foi inflado
     */
    private ViewPager viewPager;

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

        pageNumber = getArguments().getInt("pageNumber", -1);
        query = getArguments().getString("query");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RepositoryAdapter(clickListener));

        viewPager = (ViewPager) container;

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        return view;
    }

    /**
     * Quando garantimos que o layout está pronto, chamamos a Api
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Controller().getRepositories(callback, query, pageNumber, 100);
    }
}
