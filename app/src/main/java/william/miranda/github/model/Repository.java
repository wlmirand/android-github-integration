package william.miranda.github.model;

import com.google.gson.annotations.SerializedName;

/**
 * POJO para os Repositórios que vem no Response da Api
 * Inicialmente adicionamos somente os campos que precisamos
 */
public class Repository {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("owner")
    private User owner;

    @SerializedName("private")
    private boolean isPrivate;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("language")
    private String language;

    @SerializedName("has_issues")
    private boolean hasIssues;

    @SerializedName("has_projects")
    private boolean hasProjects;

    @SerializedName("has_downloads")
    private boolean hasDownloads;

    @SerializedName("has_wiki")
    private boolean hasWiki;

    @SerializedName("has_pages")
    private boolean hasPages;

    @SerializedName("default_branch")
    private String defaultBranch;

    @SerializedName("score")
    private float score;

    /** getters */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isHasIssues() {
        return hasIssues;
    }

    public boolean isHasProjects() {
        return hasProjects;
    }

    public boolean isHasDownloads() {
        return hasDownloads;
    }

    public boolean isHasWiki() {
        return hasWiki;
    }

    public boolean isHasPages() {
        return hasPages;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public float getScore() {
        return score;
    }
}
