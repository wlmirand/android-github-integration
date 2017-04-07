package william.miranda.github.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import william.miranda.github.R;

/**
 * Fragmento para mostrar os detalhes de cada Repositório
 */
public class RepositoryDetailActivity extends AppCompatActivity {

    private TextView repositoryName;
    private TextView repositoryOwner;
    private TextView repositoryDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        repositoryName = (TextView) findViewById(R.id.repository_name);
        repositoryOwner = (TextView) findViewById(R.id.repository_owner);
        repositoryDescription = (TextView) findViewById(R.id.repository_description);
    }

    @Override
    public void onStart() {
        super.onStart();
        repositoryName.setText(getIntent().getStringExtra("name"));
        repositoryOwner.setText(getIntent().getStringExtra("owner"));
        repositoryDescription.setText(getIntent().getStringExtra("description"));
    }
}
