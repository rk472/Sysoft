package com.studio.smarters.sysoft.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.studio.smarters.sysoft.POJO.Gallery;
import com.studio.smarters.sysoft.R;

public class GalleryFragment extends Fragment {
    private AppCompatActivity main;
    private View root;
    private RecyclerView galleryList;
    private DatabaseReference galleryRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main=(AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Gallery");
        root=inflater.inflate(R.layout.fragment_gallery, container, false);
        //Nav View
        NavigationView navigationView =  main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_gallery);
        galleryList=root.findViewById(R.id.gallery_list);
        galleryRef= FirebaseDatabase.getInstance().getReference().child("gallery");
        FirebaseRecyclerAdapter<Gallery,GalleryViewHolder> f=new FirebaseRecyclerAdapter<Gallery, GalleryViewHolder>(
                Gallery.class,
                R.layout.gallery_row,
                GalleryViewHolder.class,
                galleryRef
        ) {
            @Override
            protected void populateViewHolder(GalleryViewHolder viewHolder, Gallery model, int position) {
                viewHolder.setPic(getActivity(),model.getUrl());
            }
        };
        galleryList.setAdapter(f);
        galleryList.setHasFixedSize(true);
        galleryList.setLayoutManager(new GridLayoutManager(getActivity(),2, LinearLayoutManager.HORIZONTAL,false));
        // Inflate the layout for this fragment
        return root;
    }
    public static class GalleryViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public GalleryViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.gallery_row_pic);
        }
        void setPic(Context c, String s){
            Picasso.with(c).load(s).into(img);
        }
    }
}
