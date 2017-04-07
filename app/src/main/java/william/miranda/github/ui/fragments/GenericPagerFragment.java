package william.miranda.github.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import william.miranda.github.R;
import william.miranda.github.ui.adapters.GenericPagerAdapter;

/**
 * Nossa busca pode retornar muitos itens, que a Api retorna separado em páginas.
 * Então precisaremos implementar algum tipo de estrutura onde o usuário poderá fazer a paginação.
 * Um possível recurso é utilizar um ScrollListener e deixar o RecyclerView "infinito", no entando
 * como podem existir até 1000 resultados, considero inviável o usuário precisar rolar tudo.
 * Neste caso, utilizarei o ViewPager para construir a estrutura que permitirá a paginação.
 */
public class GenericPagerFragment extends Fragment {

    /**
     * Para não precisarmos escrever um Adapter praticamente igual para Users,
     * usamos esta flag para diferenciar
     */
    public enum PageType {
        REPOSITORIES,
        USERS
    }

    private PageType pageType;

    /**
     * Itens do Layout
     */
    private ViewPager viewPager;
    private TabLayout tabLayout;

    /**
     * PagerAdapter
     */
    private GenericPagerAdapter pagerAdapter;

    /**
     * Método para construir o Fragmento passando os parâmetros necessarios
     * @param pageType  tipo de Pagina que teremos (Repos ou Users)
     * @return
     */
    public static GenericPagerFragment newInstance(PageType pageType) {
        GenericPagerFragment fragment = new GenericPagerFragment();
        fragment.setPageType(pageType);
        return fragment;
    }

    /**
     * Define o PageType
     * @param pageType
     */
    private void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    /**
     * Cria o Fragmento e diz que temos algo no Toolbar
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Infla o Layout e binda as Views
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.page_dots);
        return view;
    }

    /**
     * Método para criar o menu
     * Como não estamos utilizando Content provider, podemos obter a
     * string de busca e chamar a Api diretamente
     * @param menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar, menu);

        //Obtem o item do Menu e depois a view associada
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //Definimos o Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Prepara o ViewPager, que irá carregar o Primeiro Fragment,
                //que por sua vez irá atualizar as páginas
                pagerAdapter = new GenericPagerAdapter(pageType, query, getFragmentManager());
                viewPager.setAdapter(pagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
