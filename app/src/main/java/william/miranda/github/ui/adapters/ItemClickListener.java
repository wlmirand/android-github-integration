package william.miranda.github.ui.adapters;

/**
 * Interface para tratar os eventos de Click no RecyclerView
 * Fizemos genérico porque podemos ter cliques em qualquer tipo de objeto
 * @param <T>
 */
public interface ItemClickListener<T> {
    void onItemClickListener(T item);
}
