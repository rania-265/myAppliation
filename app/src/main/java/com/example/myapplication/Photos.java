package com.example.myapplication;

import android.graphics.Bitmap;

public class Photos {

    private Bitmap image;
    private int number;

    public int getNumber() {
        return number;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}


