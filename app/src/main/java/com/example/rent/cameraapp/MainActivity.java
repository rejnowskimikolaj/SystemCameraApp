package com.example.rent.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CAPTURE = 14;
    String currentPhotoPath;
    Uri photoURI = null;

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
        List<File> fileList = getListOfPhotoFiles();
        List<Photo> photoList = getListOfPhotosFromFileList(fileList);
        for(Photo photo: photoList){
            photoAdapter.addPhoto(photo);
        }
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
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(photoFile!=null){
            photoURI = FileProvider.getUriForFile(this,"com.example.rent.cameraapp.fileprovider",photoFile);
        }
        i.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
        startActivityForResult(i,REQUEST_CAPTURE);

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private List<File> getListOfPhotoFiles(){
        List<File> list = new ArrayList<>();
        File containingFolder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(!containingFolder.exists()) return list;

        for (File file : containingFolder.listFiles()) {
            if (file.isFile()&&file.getName().contains("JPEG")) list.add(file);
        }

        return list;
    }

    private List<Photo> getListOfPhotosFromFileList(List<File> fileList){
        List<Photo> photoList = new ArrayList<>();
        for(File file: fileList){
            Date lastModDate = new Date(file.lastModified());
            String timeStamp = SimpleDateFormat.getDateTimeInstance().format(lastModDate);
            Uri uri = FileProvider.getUriForFile(this,"com.example.rent.cameraapp.fileprovider",file);
            photoList.add(new Photo(timeStamp,uri));
        }

        return photoList;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CAPTURE && resultCode==RESULT_OK){

            onMakePhotoResult();

        }
    }

    private void onMakePhotoResult() {


        String timeStamp = SimpleDateFormat.getDateTimeInstance().format(new Date());
        Photo photo = new Photo(timeStamp, photoURI);
        photoAdapter.addPhoto(photo);
    }
}
