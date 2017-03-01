package com.example.rent.cameraapp;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by RENT on 2017-02-28.
 */

public class Photo {

    String timeStamp;
    private Uri uri;

    public Photo(String timeStamp, Uri uri) {
        this.timeStamp = timeStamp;
        this.uri = uri;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
