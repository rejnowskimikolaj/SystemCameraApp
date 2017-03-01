package com.example.rent.cameraapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-02-28.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    List<Photo> list;
    Context context;

    public PhotoAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder( PhotoViewHolder holder, int position) {

         Photo photo = list.get(position);

        Picasso.with(context).load(photo.getUri()).fit().centerInside().into(holder.imageView);

        //holder.imageView.setImageBitmap(photo.getBitmap());
        holder.timeStampTextView.setText(photo.getTimeStamp());
    }

    public void addPhoto(Photo photo){
        list.add(0,photo);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        private TextView timeStampTextView;
        private ImageView imageView;


        public PhotoViewHolder(View v) {
            super(v);
            timeStampTextView = (TextView) v.findViewById(R.id.dateStamp_textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }
}
