package william.miranda.github.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import william.miranda.github.R;
import william.miranda.github.model.Repository;
import william.miranda.github.ui.fragments.RepositoryDetailFragment;
import william.miranda.github.ui.fragments.RepositoriesFragment;

/**
 * Activity para coordenar os fragmentos relacionados aos Repositórios
 * Por simplicidade, esta classe irá atuar como o Controller para os eventos dos
 * fragmentos inflados aqui.
 */
public class RepositoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialmente mostramos o fragmento que lista os repositórios
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new RepositoriesFragment(), "repositories")
                .commit();
    }

    /**
     * Método chamado pelo RepositoriesFragment, para mostrar os detalhes de um dado Repositório
     * @param repository
     */
    public void displayDetails(Repository repository) {
        /* Como iremos mostrar poucos dados, passaremos todos eles em um Bundle.
            Poderiamos também fazer nosso POJO implementar a interface Parcelable e passar o objeto inteiro,
            porém como podemos ter muitos dados, levaria a um TooLargeTransactionException por causa
            das restrições do Binder (1MB de dados no máximo).
            Se fosse necessário passar um volume grande de dados, deveriamos salvar os Dados em um
            DB ou arquivo e passar apenas a referência para o Fragment, que ficaria responsável por
            obter o conjunto completo dos dados.
            Também poderíamos passar apenas o ID e fazer uma nova busca na Api
         */
        Fragment detailFragment = new RepositoryDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString("name", repository.getName());
        arguments.putString("owner", repository.getOwner().getLogin());
        arguments.putString("description", repository.getDescription());
        detailFragment.setArguments(arguments);

        //Faz a transação animada, para deixar bonito. Utilizamos o hide e add ao invés do replace
        //para manter o estado do primeiro fragment ao voltar e não precisar criar do zero
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .hide(getSupportFragmentManager().findFragmentByTag("repositories"))
                .add(R.id.frame_layout, detailFragment, "repository_detail")
                .addToBackStack(null)
                .commit();
    }

}
