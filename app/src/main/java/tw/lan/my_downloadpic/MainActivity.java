package tw.lan.my_downloadpic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mDownloadPicCompress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processView();
    }

    private void processView() {
        mDownloadPicCompress = (Button) findViewById(R.id.downloadPic_compress);
        mDownloadPicCompress.setOnClickListener(buttonClickListener);
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.downloadPic_compress:
                    intent.setClass(MainActivity.this, DownloadPicCompressActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };
}
