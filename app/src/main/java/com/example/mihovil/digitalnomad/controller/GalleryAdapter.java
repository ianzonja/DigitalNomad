package com.example.mihovil.digitalnomad.controller;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mihovil.digitalnomad.R;

/**
 * Created by Ian on 1/22/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    public static class GalleryViewHolder extends RecyclerView.ViewHolder{
        public ImageView photo;
        public CardView mCardView;
        GalleryViewHolder(final View itemView){
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            photo = (ImageView) itemView.findViewById(R.id.workspace_gallery_photo);
        }
    }

    Bitmap[] bitmapGallery;

    public GalleryAdapter(Bitmap[] workspaceGallery){
        this.bitmapGallery = workspaceGallery;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_workspace_galery, parent, false);
        GalleryAdapter.GalleryViewHolder wvh = new GalleryAdapter.GalleryViewHolder(v);
        return wvh;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.GalleryViewHolder holder, int position) {
        System.out.println(position);
        holder.photo.setImageBitmap(bitmapGallery[position]);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return bitmapGallery.length;
    }
}
