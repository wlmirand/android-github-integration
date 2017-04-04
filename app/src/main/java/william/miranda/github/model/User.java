package william.miranda.github.model;

import com.google.gson.annotations.SerializedName;

/**
 * POJO para os Usuários que vem no Response da Api
 * Inicialmente adicionamos somente os campos que precisamos
 */
public class User {

    @SerializedName("id")
    private long id;

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("site_admin")
    private boolean isSiteAdmin;

    /** getters */
    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public boolean isSiteAdmin() {
        return isSiteAdmin;
    }
}
