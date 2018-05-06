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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.studio.smarters.sysoft.POJO.Faculty;
import com.studio.smarters.sysoft.POJO.Success;
import com.studio.smarters.sysoft.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuccessFragment extends Fragment {

    private AppCompatActivity main;
    private View root;
    private RecyclerView successList;
    private DatabaseReference successRef;
    private RelativeLayout progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("Success Achievers");
        root = inflater.inflate(R.layout.fragment_success, container, false);
        //Nav View
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_success);
        successList=root.findViewById(R.id.success_list);
        progress=root.findViewById(R.id.success_progress);
        successRef= FirebaseDatabase.getInstance().getReference().child("success");
        successRef.keepSynced(true);
        FirebaseRecyclerAdapter<Success,SuccessViewHolder> f=new FirebaseRecyclerAdapter<Success, SuccessViewHolder>(
                Success.class,
                R.layout.success_row,
                SuccessViewHolder.class,
                successRef
        ) {
            @Override
            protected void populateViewHolder(SuccessViewHolder viewHolder, Success model, int position) {
                progress.setVisibility(View.GONE);
                viewHolder.setWord(model.getWord());
                viewHolder.setDp(getActivity(),model.getPic());
                viewHolder.setName(model.getName());
                viewHolder.setCompany(model.getCompany());
            }
        };
        successList.setAdapter(f);
        successList.setHasFixedSize(true);
        successList.setLayoutManager(new LinearLayoutManager(main));
        // Inflate the layout for this fragment
        return root;
    }
    public static class SuccessViewHolder extends RecyclerView.ViewHolder{
        CircleImageView dp;
        TextView name,company,word;
        public SuccessViewHolder(View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.success_dp);
            name=itemView.findViewById(R.id.success_name);
            company=itemView.findViewById(R.id.success_company);
            word=itemView.findViewById(R.id.success_words);
        }
        void setName(String s){
            name.setText(s);
        }
        void setWord(String s){
            word.setText("\""+s+"\"");
        }
        void setCompany(String s){
            company.setText(s);
        }
        void setDp(final Context ctx, final String s){
            Picasso.with(ctx).load(s).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.dp)
                    .into(dp, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(ctx).load(s).placeholder(R.drawable.dp).into(dp);
                        }
                    });
        }
    }

}
