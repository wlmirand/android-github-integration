package william.miranda.github.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import william.miranda.github.R;

/**
 * Fragmento para mostrar os detalhes de cada Usuário
 */
public class UserDetailFragment extends Fragment {

    private TextView userName;
    private TextView userAvatarUrl;
    private TextView userHtmlUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        userName = (TextView) view.findViewById(R.id.user_name);
        userAvatarUrl = (TextView) view.findViewById(R.id.user_avatar_url);
        userHtmlUrl = (TextView) view.findViewById(R.id.user_html_url);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        userName.setText(arguments.getString("login"));
        userAvatarUrl.setText(arguments.getString("avatarUrl"));
        userHtmlUrl.setText(arguments.getString("htmlUrl"));
    }
}
