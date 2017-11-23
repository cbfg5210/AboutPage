package com.ue.aboutpagedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ue.aboutpage.AboutPageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AboutPageView apvAboutPage = findViewById(R.id.apvAboutPage);
        apvAboutPage.setAboutItemClickListener(new AboutPageView.OnAboutItemClickListener() {
            @Override
            public void onVersionClicked() {
                Toast.makeText(MainActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSupportClicked() {
                Toast.makeText(MainActivity.this, "感谢支持", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
