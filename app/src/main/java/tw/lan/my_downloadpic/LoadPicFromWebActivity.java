package tw.lan.my_downloadpic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadPicFromWebActivity extends AppCompatActivity {

    private ImageView mImageView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pic_from_web);

        processView();
    }

    private void processView() {
        mImageView = (ImageView) findViewById(R.id.load_pic_from_web_imageView);
        mEditText = (EditText) findViewById(R.id.load_pic_from_web_url_editText);
    }

    public void clearUrl(View v) {
        mEditText.setText("");
    }

    public void downloadPic(View v) {
        new MyAsyncTask().execute(mEditText.getText().toString());
    }

    class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                bitmap = BitmapFactory.decodeStream(httpUrlConnection.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }
}
