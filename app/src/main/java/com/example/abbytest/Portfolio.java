package com.example.abbytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;

/*
Вторая активность, отображающая данные
по отдельно выбранному в CatList коту
 */

public class Portfolio extends AppCompatActivity {
    TextView name;
    TextView desc;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        name = (TextView) findViewById(R.id.namePt);
        desc = (TextView) findViewById(R.id.descPt);
        photo = (ImageView) findViewById(R.id.photoPt);

        Bitmap bmp = null;
        String filemane = getIntent().getStringExtra("photo");
        try {
            FileInputStream is = this.openFileInput(filemane);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
            photo.setImageBitmap(bmp);
            desc.setText(getIntent().getStringExtra("desc"));
            name.setText(getIntent().getStringExtra("name"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
