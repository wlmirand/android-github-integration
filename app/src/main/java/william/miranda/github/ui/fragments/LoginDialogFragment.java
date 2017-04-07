package william.miranda.github.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import william.miranda.github.R;

public class LoginDialogFragment extends DialogFragment {

    /**
     * Campos do Layout
     */
    private EditText username;
    private EditText password;

    /**
     * ClickListener do Dialog
     */
    private DialogInterface.OnClickListener listener;

    public static LoginDialogFragment newInstance(DialogInterface.OnClickListener listener) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog loginDialog = new AlertDialog.Builder(getContext())
                .setTitle("Login Github")
                .setPositiveButton("Ok", listener)
                .setNegativeButton("Cancelar", null)
                .setView(R.layout.fragment_login)
                .create();

        return loginDialog;
    }

    private void setListener(DialogInterface.OnClickListener listener) {
        this.listener = listener;
    }
}
