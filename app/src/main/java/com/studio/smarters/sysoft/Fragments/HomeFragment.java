package com.studio.smarters.sysoft.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.smarters.sysoft.POJO.Batch;
import com.studio.smarters.sysoft.POJO.News;
import com.studio.smarters.sysoft.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private AppCompatActivity main;
    private View root;
    private ImageView homeNewsPic;
    private TextView homeNewsTitle,homeNewsDesc;
    private DatabaseReference newsRef;
    private RecyclerView newsList,batchList;
    private ValueEventListener v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        main=(AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Home");
        root=inflater.inflate(R.layout.fragment_home, container, false);
        //Nav View
        NavigationView navigationView =  main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        homeNewsPic=root.findViewById(R.id.home_news_pic);
        homeNewsTitle=root.findViewById(R.id.home_news_title);
        homeNewsDesc=root.findViewById(R.id.home_news_desc);
        newsRef= FirebaseDatabase.getInstance().getReference().child("news");
        v=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title=dataSnapshot.child("main_news").getValue().toString();
                String desc=dataSnapshot.child("main_desc").getValue().toString();
                String imgUrl=dataSnapshot.child("main_pic").getValue().toString();
                homeNewsDesc.setText(desc);
                homeNewsTitle.setText(title);
                Picasso.with(getActivity()).load(imgUrl).into(homeNewsPic);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        newsRef.addValueEventListener(v);
        newsList=root.findViewById(R.id.news_list);
        batchList=root.findViewById(R.id.batch_list);

        FirebaseRecyclerAdapter<News,NewsViewHolder> f1=new FirebaseRecyclerAdapter<News, NewsViewHolder>(
                News.class,
                R.layout.news_row,
                NewsViewHolder.class,
                newsRef.child("other_news")
        ) {
            @Override
            protected void populateViewHolder(NewsViewHolder viewHolder, News model, int position) {
                viewHolder.setDesc(model.getNews_desc());
                viewHolder.setTitle(model.getNews_title());
                viewHolder.setPic(getActivity(),model.getNews_pic());
            }
        };
        FirebaseRecyclerAdapter<Batch,BatchViewHolder> f2=new FirebaseRecyclerAdapter<Batch, BatchViewHolder>(
                Batch.class,
                R.layout.batch_row,
                BatchViewHolder.class,
                newsRef.child("upcoming_batches")
        ) {
            @Override
            protected void populateViewHolder(BatchViewHolder viewHolder, Batch model, int position) {
                viewHolder.setAllData(model.getBatch_name(),model.getBatch_start(),model.getBatch_timing());
                viewHolder.setNew(model.isN());
                viewHolder.setPic(getActivity(),model.getImage());
            }
        };

        newsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        newsList.setHasFixedSize(true);
        newsList.setAdapter(f1);

        batchList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        batchList.setHasFixedSize(true);
        batchList.setAdapter(f2);


        // Inflate the layout for this fragment
        return root;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle,newsDesc;
        CircleImageView newsPic;
        public NewsViewHolder(View itemView) {
            super(itemView);
            newsTitle=itemView.findViewById(R.id.news_row_title);
            newsDesc=itemView.findViewById(R.id.news_row_desc);
            newsPic=itemView.findViewById(R.id.news_row_pic);
        }
        void setTitle(String title){
            newsTitle.setText(title);
        }
        void setDesc(String desc){
            newsDesc.setText(desc);
        }
        void setPic(Context ctx,String url){
            Picasso.with(ctx).load(url).into(newsPic);
        }
    }
    public static class BatchViewHolder extends RecyclerView.ViewHolder{
        TextView batchName,batchStart,batchTiming,newText;
        ImageView img;
        public BatchViewHolder(View itemView) {
            super(itemView);
            batchName=itemView.findViewById(R.id.batch_row_name);
            batchStart=itemView.findViewById(R.id.batch_row_start);
            batchTiming=itemView.findViewById(R.id.batch_row_timing);
            newText=itemView.findViewById(R.id.batch_row_new);
            img=itemView.findViewById(R.id.batch_row_pic);
        }
        void setAllData(String name,String start,String timing){
            batchName.setText(name);
            batchTiming.setText(timing);
            batchStart.setText("starts from : "+start);
        }
        void setNew(boolean b){
            if(b)
                newText.setVisibility(View.VISIBLE);
            else
                newText.setVisibility(View.INVISIBLE);
        }
        void setPic(Context c,String url){
            Picasso.with(c).load(url).into(img);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsRef.removeEventListener(v);
    }
}
