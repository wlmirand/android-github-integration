package william.miranda.github.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import william.miranda.github.R;
import william.miranda.github.api.Auth;
import william.miranda.github.controller.Controller;
import william.miranda.github.model.User;
import william.miranda.github.tasks.DownloadImageTask;
import william.miranda.github.ui.fragments.GenericPagerFragment;
import william.miranda.github.ui.fragments.LoginDialogFragment;
import william.miranda.github.ui.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * Itens do Layout
     */
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    /**
     * ClickListener para o Ok da Dialog de Login
     */
    private DialogInterface.OnClickListener loginListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String username = ((EditText) ((AlertDialog) dialogInterface).findViewById(R.id.login_username))
                    .getText().toString();

            String password = ((EditText) ((AlertDialog) dialogInterface).findViewById(R.id.login_password))
                    .getText().toString();

            Auth.getInstance().setAuth(username, password);
            new Controller().getAuthUserInfo(loginCallback);
        }
    };

    /**
     * Callback da chamada da Api
     */
    private Callback<User> loginCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.code() == 200) {//sucesso
                //Remove a view de Login e adiciona a "logada"
                View oldView = navigationView.getHeaderView(0);
                navigationView.removeHeaderView(oldView);
                navigationView.inflateHeaderView(R.layout.drawer_header_logged);

                //popula a nova view com as informações do response
                setLoggedInfo(response.body());
            } else {
                //Erro... zera o Singleton de autenticação
                Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_LONG).show();
                Auth.getInstance().clear();
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            //Nada a fazer
        }
    };

    /**
     * Entry point do App
     * Inicialmente seta o Drawer e os listeners necessários na Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //infla o fragmento de boas vindas
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new WelcomeFragment())
                .commit();

        //Define o Toolbar (usamos o da support lib)
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Prepara o NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Inicialmente define o OnClick para o Login
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostra o dialog
                DialogFragment loginDialog = LoginDialogFragment.newInstance(loginListener);
                loginDialog.show(getSupportFragmentManager(), null);
            }
        });

        //OnClick dos itens do Menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            //Quando o usuário seleciona algo do menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Se escolheu o mesmo, não faz nada
                if (menuItem.isChecked()) {
                    drawerLayout.closeDrawers();
                    return true;
                }

                //Faz highlight no item selecionado
                menuItem.setChecked(true);

                //Fecha o drawer ao selecionar algo
                drawerLayout.closeDrawers();

                //De acordo com o item selecionado, faz alguma coisa
                Fragment fragment = null;
                switch (menuItem.getItemId()){

                    case R.id.menu_repositories:
                        fragment = GenericPagerFragment.newInstance(GenericPagerFragment.PageType.REPOSITORIES);
                        break;

                    case R.id.menu_users:
                        fragment = GenericPagerFragment.newInstance(GenericPagerFragment.PageType.USERS);
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame,fragment)
                        .commit();

                return true;
            }
        });

        // Prepara o DrawerLayout para ter o ícone que abre o Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Define o Listener
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    /**
     * Preenche a área Logada do Drawer com as informações do usuário logado
     * @param user
     */
    private void setLoggedInfo(User user) {
        View header = navigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.logged_username)).setText(user.getLogin());

        new DownloadImageTask((ImageView) header.findViewById(R.id.logged_avatar)).execute(user.getAvatarUrl());
    }
}
