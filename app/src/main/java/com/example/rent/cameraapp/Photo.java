package com.example.rent.cameraapp;

import android.graphics.Bitmap;

/**
 * Created by RENT on 2017-02-28.
 */

public class Photo {

    String timeStamp;
    Bitmap bitmap;

    public Photo(String timeStamp, Bitmap bitmap) {
        this.timeStamp = timeStamp;
        this.bitmap = bitmap;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
