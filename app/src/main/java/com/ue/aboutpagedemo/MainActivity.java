package com.ue.aboutpagedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ue.aboutpage.AboutPageView;
import com.ue.aboutpage.DetailItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] faqTitles = getResources().getStringArray(R.array.faqTitles);
        String[] faqContents = getResources().getStringArray(R.array.faqItems);
        List<DetailItem> detailItems = new ArrayList<>();
        for (int i = 0, len = faqTitles.length; i < len; i++) {
            detailItems.add(new DetailItem(faqTitles[i], faqContents[i]));
        }

        AboutPageView apvAboutPage = findViewById(R.id.apvAboutPage);
        apvAboutPage.setFaqItems(detailItems);
        apvAboutPage.setVerNoteItems(detailItems);
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
