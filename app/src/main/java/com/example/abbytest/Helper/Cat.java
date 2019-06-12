package com.example.abbytest.Helper;

/*
Pojo класс отдельно взятого кота
 */
import android.graphics.Bitmap;

public class Cat{
    private Bitmap catPhoto;
    private String catName;
    private String catDescription = "Я кот. У меня на этом все.";

    public Cat(Bitmap catPhoto, String catName) {
        this.catPhoto = catPhoto;
        this.catName = catName;
    }

    public Bitmap getCatPhoto() {
        return catPhoto;
    }

    public void setCatPhoto(Bitmap catPhoto) {
        this.catPhoto = catPhoto;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }
}
