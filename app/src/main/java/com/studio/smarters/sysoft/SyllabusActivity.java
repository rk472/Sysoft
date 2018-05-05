package com.studio.smarters.sysoft;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SyllabusActivity extends AppCompatActivity {
    private PDFView mView;
    private RelativeLayout progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        mView=findViewById(R.id.syllabus_view);
        progress=findViewById(R.id.syllabus_progress);
        String url=getIntent().getExtras().getString("url");
        new PdfViewer().execute(url);

    }
    class PdfViewer extends AsyncTask<String,Void,InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection h=(HttpURLConnection)url.openConnection();
                if(h.getResponseCode()==200){
                    inputStream=new BufferedInputStream(h.getInputStream());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return  inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            mView.fromStream(inputStream).load();
            progress.setVisibility(View.GONE);
        }
    }
}
