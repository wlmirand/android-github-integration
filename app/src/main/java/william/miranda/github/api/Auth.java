package william.miranda.github.api;

public class Auth {

    private String username;
    private String password;

    private static Auth instance;

    public static Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }

        return instance;
    }

    private Auth() {

    }

    public void setAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void clear() {
        this.username = null;
        this.password = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
