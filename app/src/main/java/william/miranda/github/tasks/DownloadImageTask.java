package william.miranda.github.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Task para baixar imagens através de uma URL e atualizar um ImageView
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];

        Bitmap bitmap = null;
        try {
            InputStream in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}