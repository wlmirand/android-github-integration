package william.miranda.github.api;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe para interceptar as chamadas e, adicionar o drawer_header de autenticação se houver
 */
public class SessionInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request original = chain.request();

        String username = Auth.getInstance().getUsername();
        String password = Auth.getInstance().getPassword();

        if (username != null && !username.isEmpty()
                && password != null && !password.isEmpty()) {
            String str = String.format("%s:%s",
                    username,
                    password);

            Request request = original.newBuilder()
                    .header("Authorization", "Basic " + encodeString(str).replace("\n", ""))
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        } else {
            return chain.proceed(original);
        }
    }

    /**
     * Encoda uma String utilizando o algoritmo Base64
     * @param s
     * @return
     */
    private String encodeString(String s) {
        byte[] data = new byte[0];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return Base64.encodeToString(data, Base64.DEFAULT);
        }
    }
}
