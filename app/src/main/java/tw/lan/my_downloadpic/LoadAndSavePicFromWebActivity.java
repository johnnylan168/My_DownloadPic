package tw.lan.my_downloadpic;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class LoadAndSavePicFromWebActivity extends AppCompatActivity {

    private ImageView mImageView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_and_save_pic_from_web);

        getPermission();
    }

    private void getPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            processView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                processView();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("必須允許讀寫權限才能顯示資料")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }
        }
    }

    private void processView() {
        mImageView = (ImageView) findViewById(R.id.load_and_save_pic_from_web_imageView);
        mEditText = (EditText) findViewById(R.id.load_and_save_pic_from_web_url_editText);
    }

    public void clearUrl(View v) {
        mEditText.setText("");
    }

    public void downloadAndSavePic(View v) {
        new MyAsyncTask().execute(mEditText.getText().toString());
    }

    class MyAsyncTask extends AsyncTask<String, Integer, InputStream> {

        @Override
        protected InputStream doInBackground(String... params) {
            InputStream inputStream = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpUrlConnection.getInputStream();
                Calendar calendar = Calendar.getInstance();
                String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/"+ String.valueOf(calendar.getTimeInMillis()) + "." + transferImageType(httpUrlConnection.getContentType());
                FileOutputStream out = new FileOutputStream(new File(path));
                Bitmap bitmap = BitmapFactory.decodeStream(httpUrlConnection.getInputStream());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                bitmap.recycle();
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                    out.flush();
                }
                out.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream in) {
//            try {
//                Log.d("rd26", String.valueOf(in.read()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Bitmap bitmap = BitmapFactory.decodeStream(in);
//
//            mImageView.setImageBitmap(bitmap);
        }

        // 自定義對應副檔名
        public String transferImageType(String type) {
            switch (type) {
                case "image/jpeg":
                    return "jpg";
                case "image/png":
                    return "png";
                case "image/gif":
                    return "gif";
                default:
                    return "";
            }
        }
    }
}
