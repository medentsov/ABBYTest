package com.example.abbytest;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.example.abbytest.Helper.Cat;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/*
Класс, получающий картинки котов по предоставленным
первой активностью ссылкам
 */

public class GetCatPhotoViaInternet {
    private ArrayList<URL> catURL = new ArrayList<>();
    private String[] catNames;
    private Context context;

    public GetCatPhotoViaInternet(String[] catURLs, String[] catNames, Context context) {
        for (int i = 0; i < catURLs.length; i++) {
            try {
                catURL.add(new URL(catURLs[i]));
            } catch (MalformedURLException exception) {
                Log.d("PALUNDRA", "ВСЕ ПОЛОМАЛОСЬ");
            }
        }
        this.catNames = catNames;
        this.context = context;
    }

    public void createCat(CompleteCallback callback) {
        AddUserTask addUserTask = new AddUserTask(callback, catURL, catNames);
        addUserTask.execute();
    }
    interface CompleteCallback {
        void onComplete(List<Cat> cats);
    }

    class AddUserTask extends AsyncTask<Void, Void, List<Cat>> {

        private final CompleteCallback callback;
        private ArrayList<URL> catURLs;
        private String[] catNames;
        private List<Cat> cats = new ArrayList<>();
        private ProgressDialog pdia;

        AddUserTask(CompleteCallback callback, ArrayList<URL> catURLs, String[] catNames) {

            this.callback = callback;
            this.catURLs = catURLs;
            this.catNames = catNames;
        }

        @Override
        protected void onPreExecute() {
            pdia = new ProgressDialog(context);
            pdia.setCanceledOnTouchOutside(false);
            pdia.setCancelable(false);
            pdia.setMessage("Загрузка...");
            pdia.show();
        }

        protected List<Cat> doInBackground(Void... params) {
            Bitmap tmp = null;

            for (int i = 0; i < catURLs.size(); i++) {
                try {
                    tmp = decodeFile(catURLs.get(i));
                    cats.add(new Cat(tmp, catNames[i]));
                } catch (Exception e) {
                    Log.d("TESTING", "КОТЫ НЕ СФОРМИРОВАНЫ");
                }
            }
            return cats;
        }

        @Override
        protected void onPostExecute(List<Cat> cats) {
            if (callback != null) {
                callback.onComplete(cats);
            }
            if(pdia.isShowing()){
                pdia.dismiss();
            }
        }
    }
    /*
    Метод декодера, изменяющего размер картинок
     */

    private Bitmap decodeFile(URL f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(f.openConnection().getInputStream(), null, o);

            final int REQUIRED_SIZE=80;

            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(f.openConnection().getInputStream(), null, o2);
        } catch (Exception e) {}
        return null;
    }
}
