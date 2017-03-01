package com.example.rent.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CAPTURE = 14;

    PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRecyclerView();
    }

    private void setRecyclerView(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        photoAdapter = new PhotoAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id) {
            case R.id.action_makePhoto:
                launchMakePhoto();

        }
        return super.onOptionsItemSelected(item);
    }

    private void launchMakePhoto() {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CAPTURE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CAPTURE && resultCode==RESULT_OK){

            onMakePhotoResult(data);

        }
    }

    private void onMakePhotoResult(Intent data) {


        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");
        String timeStamp = SimpleDateFormat.getDateTimeInstance().format(new Date());
        Photo photo = new Photo(timeStamp, bitmap);
        photoAdapter.addPhoto(photo);
    }
}
