package com.fabricio.challenge.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Image download handler. This class should be called in the activity or fragments.
 * It download any image from given URL and store it in the Bitmap and in ImageView objects.
 * @author Fabricio Godoi
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = DownloadImageTask.class.getSimpleName();

    private Bitmap image;
    private ImageView imageView;

    public DownloadImageTask(Bitmap image, ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e(TAG, "Could not download the image"+url+": ", e);
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        image = result;
    }
}
