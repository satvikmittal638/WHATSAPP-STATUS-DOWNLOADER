package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Status_Preview extends AppCompatActivity {
ImageView prev,share,play,close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status__preview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prev=findViewById(R.id.image_prev);
        play=findViewById(R.id.play_btn);
        share=findViewById(R.id.share_btn);
        close=findViewById(R.id.close_btn);

        Intent intent=getIntent();
        Uri uri= Uri.parse((intent.getStringExtra("uri")));

        if(!(uri.toString().endsWith(".mp4"))){
            play.setVisibility(View.INVISIBLE);
        }

        Glide.with(this)
                .load(uri)
                .into(prev);


        String type;
        type=uri.toString().endsWith(".mp4")?"video/mp4":"image/*";
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType(type);

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "WS Downloader");
                startActivity(Intent.createChooser(shareIntent, "Choose one"));
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playvid = new Intent(Intent.ACTION_VIEW);
                playvid.setDataAndType(uri, "video/*");
                playvid.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(playvid);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}