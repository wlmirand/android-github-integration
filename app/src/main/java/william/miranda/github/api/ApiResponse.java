package william.miranda.github.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import william.miranda.github.model.User;

/**
 * Objeto de Response para a Api
 * Pela documentação, a busca por Repositórios e Usuários retorna os mesmos campos no JSON
 * O que muda é o tipo de dado do array "items".
 * Entáo trataremos o Response utilizando Generics
 */
public class ApiResponse<T> {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<T> items;

    /** getters */
    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<T> getItems() {
        return items;
    }
}
