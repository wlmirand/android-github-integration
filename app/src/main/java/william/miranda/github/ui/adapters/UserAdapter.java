package william.miranda.github.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import william.miranda.github.R;
import william.miranda.github.model.User;
import william.miranda.github.tasks.DownloadImageTask;

/**
 * Adapter responsável por mostrar uma lista de Usuários
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    /**
     * Lista de dados a serem mostrados
     */
    private List<User> data;

    /**
     * Click Listener
     */
    private final ItemClickListener clickListener;

    /**
     * Construtor onde inicializamos uma lista vazia e guardamos o click listener
     * a ser chamado em cada item
     */
    public UserAdapter(ItemClickListener clickListener) {
        this.data = new ArrayList<>();
        this.clickListener = clickListener;
    }

    /**
     * Método que infla o layout para cada linha (ViewHolder) a ser mostrada
     * @param parent    viewGroup do recyclerView
     * @param viewType  Tipo de View, usado caso seja necessário inflar layouts diferentes
     * @return          ViewHolder associado a um item da lista de dados
     */
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_user, parent, false);
        return new UserViewHolder(view);
    }

    /**
     * Popula o layout criado acima com as informações do POJO
     * @param holder    ViewHolder
     * @param position  Posição a ser "desenhada"
     */
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = data.get(position);
        holder.setData(user);
        holder.setListeners(clickListener, user);
    }

    /**
     * Retorna o número de itens a serem mostrados
     * @return
     */
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Atualiza o DataSet
     * @param newData
     */
    public void swap(Collection<User> newData) {
        this.data.clear();
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * Classe que representa o ViewHolder
     */
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        /**
         * Campos do Layout
         */
        private final TextView userName;
        private final ImageView userAvatar;

        /**
         * Construtor
         * Apenas bindamos as views do Layout
         * @param itemView
         */
        public UserViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
        }

        /**
         * Método que preenche de fato as informações do POJO nas views do Layout
         * @param user
         */
        public void setData(User user) {
            userName.setText(user.getLogin());
            new DownloadImageTask(userAvatar).execute(user.getAvatarUrl());
        }

        /**
         * Método que define o Click Listener de cada item
         * @param clickListener listener que vem do fragment
         * @param user          item que foi clicada
         */
        public void setListeners(final ItemClickListener clickListener, final User user) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClickListener(user);
                    }
                }
            });
        }
    }
}
