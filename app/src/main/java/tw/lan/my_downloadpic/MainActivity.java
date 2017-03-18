package tw.lan.my_downloadpic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mLoadPicFromWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processView();
    }

    private void processView() {
        mLoadPicFromWeb = (Button) findViewById(R.id.load_pic_from_web_main_btn);
        mLoadPicFromWeb.setOnClickListener(buttonClickListener);
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.load_pic_from_web_main_btn:
                    intent.setClass(MainActivity.this, LoadPicFromWebActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };
}
