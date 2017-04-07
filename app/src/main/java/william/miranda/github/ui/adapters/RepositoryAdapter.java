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
import william.miranda.github.model.Repository;

/**
 * Adapter responsável por mostrar uma lista de Repositórios
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    /**
     * Lista de dados a serem mostrados
     */
    private List<Repository> data;

    /**
     * Click Listener
     */
    private final ItemClickListener clickListener;

    /**
     * Construtor onde inicializamos uma lista vazia e guardamos o click listener
     * a ser chamado em cada item
     */
    public RepositoryAdapter(ItemClickListener clickListener) {
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
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_repository, parent, false);
        return new RepositoryViewHolder(view);
    }

    /**
     * Popula o layout criado acima com as informações do POJO
     * @param holder    ViewHolder
     * @param position  Posição a ser "desenhada"
     */
    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = data.get(position);
        holder.setData(repository);
        holder.setListeners(clickListener, repository);
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
    public void swap(Collection<Repository> newData) {
        this.data.clear();
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * Classe que representa o ViewHolder
     */
    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        /**
         * Campos do Layout
         */
        private final TextView repositoryName;
        private final TextView repositoryOwner;
        private final ImageView repositoryLanguageIcon;

        /**
         * Construtor
         * Apenas bindamos as views do Layout
         * @param itemView
         */
        public RepositoryViewHolder(View itemView) {
            super(itemView);
            repositoryName = (TextView) itemView.findViewById(R.id.repository_name);
            repositoryOwner = (TextView) itemView.findViewById(R.id.repository_owner);
            repositoryLanguageIcon = (ImageView) itemView.findViewById(R.id.repository_language_icon);
        }

        /**
         * Método que preenche de fato as informações do POJO nas views do Layout
         * @param repository
         */
        public void setData(Repository repository) {
            repositoryName.setText(repository.getName());
            repositoryOwner.setText(repository.getOwner().getLogin());
            repositoryLanguageIcon.setImageResource(getLanguageIcon(repository.getLanguage()));
        }

        /**
         * Método que define o Click Listener de cada item
         * @param clickListener listener que vem do fragment
         * @param repository    item que foi clicada
         */
        public void setListeners(final ItemClickListener clickListener, final Repository repository) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClickListener(repository);
                    }
                }
            });
        }

        /**
         * Método que retorna um ícone
         * @param language  nome da linguagem
         * @return          ícone associado
         */
        private int getLanguageIcon(String language) {
            int defaultRes = 0;

            if (language == null) {
                return defaultRes;
            }

            switch (language.toLowerCase()) {
                case "java":
                    defaultRes = R.drawable.ic_java_sq;
                    break;
                case "css":
                    defaultRes = R.drawable.ic_css_sq;
                    break;
                case "c#":
                    defaultRes = R.drawable.ic_cs_sq;
                    break;
                case "swift":
                    defaultRes = R.drawable.ic_swift_sq;
                    break;
                case "python":
                    defaultRes = R.drawable.ic_python_sq;
                    break;
                case "perl":
                    defaultRes = R.drawable.ic_perl_sq;
                    break;
                case "objective-c":
                    defaultRes = R.drawable.ic_objc_sq;
                    break;
                case "c":
                    defaultRes = R.drawable.ic_c_sq;
                    break;
                case "c++":
                    defaultRes = R.drawable.ic_cpp_sq;
                    break;
                case "javascript":
                    defaultRes = R.drawable.ic_js_sq;
                    break;
                case "shell":
                    defaultRes = R.drawable.ic_shell_sq;
                    break;
                case "html":
                    defaultRes = R.drawable.ic_html_sq;
                    break;
                case "clojure":
                    defaultRes = R.drawable.ic_clojure_sq;
                    break;
                case "lua":
                    defaultRes = R.drawable.ic_lua_sq;
                    break;
                case "go":
                    defaultRes = R.drawable.ic_go_sq;
                    break;
                case "php":
                    defaultRes = R.drawable.ic_php_sq;
                    break;
                case "assembly":
                    defaultRes = R.drawable.ic_asm_sq;
                    break;
                case "scala":
                    defaultRes = R.drawable.ic_scala_sq;
                    break;
                case "haskell":
                    defaultRes = R.drawable.ic_haskell_sq;
                    break;
                case "groovy":
                    defaultRes = R.drawable.ic_groovy_sq;
                    break;
                case "ruby":
                    defaultRes = R.drawable.ic_ruby_sq;
                    break;
                case "coffeescript":
                    defaultRes = R.drawable.ic_coffeescript_sq;
                    break;
                case "emacs lisp":
                    defaultRes = R.drawable.ic_lisp_sq;
                    break;
                case "r":
                    defaultRes = R.drawable.ic_r_sq;
                    break;
                default:
                    defaultRes = 0;
            }

            return defaultRes;
        }
    }
}
