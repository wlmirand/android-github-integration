package william.miranda.github.api;

import com.google.gson.annotations.SerializedName;

import william.miranda.github.model.Repository;

/**
 * Objeto de Response para os Repositórios
 */
public class RepositoryResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private Repository[] items;

    /** getters */
    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public Repository[] getItems() {
        return items;
    }
}
