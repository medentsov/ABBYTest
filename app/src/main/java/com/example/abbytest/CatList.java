package com.example.abbytest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.abbytest.Helper.Cat;
import com.example.abbytest.utils.CustomAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

/*
Класс первой активности, отображающий список котов
 */

public class CatList extends AppCompatActivity {

    private ListView listView;
    private Logic logic;
    private CustomAdapter adapter;

    private String[] picUrls = new String[]
            {"http://pngimg.com/uploads/cat/cat_PNG50546.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50537.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50525.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50511.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50498.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50480.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50433.png",
                    "http://pngimg.com/uploads/cat/cat_PNG50425.png",
                    "http://pngimg.com/uploads/cat/cat_PNG120.png",
                    "http://pngimg.com/uploads/cat/cat_PNG104.png"};

    private String[] catNames = new String[]{"Барсик", "Кэсис", "Ферруцио"
            , "Клементе", "Грампи", "Юппи"
            , "Шелли", "Кактус", "Леопардо"
            , "Жуля"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Коты подгружаются...Подождите", Toast.LENGTH_LONG).show();
        init();
    }
/*
Метод инициализации и привязки класса логики приложения
 */

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
        GetCatPhotoViaInternet getCatPhotoViaInternet = new GetCatPhotoViaInternet(picUrls, catNames, this);
        logic = new Logic(getCatPhotoViaInternet);
        logic.attachCatList(this);
        logic.CatListIsReady();
    }
    /*
    Метод, передающий из Logic сформированный
    список объектов котов в адаптер, а также реализующий
    Listener при нажатии на элемент списка и передающий
    необходимые поля для формирования второй активности
     */
    public void showCats(List<Cat> cats) {
        if (listView.getAdapter() != null){
            adapter.clear();
            adapter.addAll(cats);
        } else {
            adapter = new CustomAdapter(CatList.this,
                    R.layout.custom_view_item, cats);
            listView.setAdapter(adapter);

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getCatName(position) != null) {
                   logic.detachView();
                    Bitmap tmp;
                    try {
                        FileOutputStream stream = CatList.this.openFileOutput("bitmap.png",
                                Context.MODE_PRIVATE);
                        tmp = adapter.getCatPhoto(position);
                        tmp.compress(Bitmap.CompressFormat.PNG,
                                100, stream);
                        stream.close();
                        Intent intent = new Intent(CatList.this, Portfolio.class);
                        intent.putExtra("name", adapter.getCatName(position));
                        intent.putExtra("desc", adapter.getCatDescription(position));
                        intent.putExtra("photo", "bitmap.png");
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /*
    Отвязываем логику от текущей активити
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        logic.detachView();
    }
}
