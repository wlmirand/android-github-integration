package william.miranda.github.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import william.miranda.github.R;

/**
 * Fragmento para mostrar os detalhes de cada Repositório
 */
public class RepositoryDetailFragment extends Fragment {

    private TextView repositoryName;
    private TextView repositoryOwner;
    private TextView repositoryDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_detail, container, false);
        repositoryName = (TextView) view.findViewById(R.id.repository_name);
        repositoryOwner = (TextView) view.findViewById(R.id.repository_owner);
        repositoryDescription = (TextView) view.findViewById(R.id.repository_description);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        repositoryName.setText(arguments.getString("name"));
        repositoryOwner.setText(arguments.getString("owner"));
        repositoryDescription.setText(arguments.getString("description"));
    }
}
