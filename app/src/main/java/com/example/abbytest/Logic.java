package com.example.abbytest;


import com.example.abbytest.Helper.Cat;
import java.util.List;

/*
Класс, реализующий логику приложения
 */
public class Logic {

    private CatList catList;
    private Portfolio portfolio;
    private GetCatPhotoViaInternet getCatPhotoViaInternet;


    public Logic(GetCatPhotoViaInternet getCatPhotoViaInternet) {
        this.getCatPhotoViaInternet = getCatPhotoViaInternet;
    }

    public void detachView() {
        catList = null;
    }

    public void attachCatList(CatList catList) {
        this.catList = catList;
    }
    /*
    Метод, реализующий метод интерфейса из GetCatPhotoViaInternet
    и передающий сформированный список котов в первую активность
     */
    public void CatListIsReady() {
        getCatPhotoViaInternet.createCat(new GetCatPhotoViaInternet.CompleteCallback() {
            @Override
            public void onComplete(List<Cat> cats) {
                catList.showCats(cats);
            }
        });
    }
}
