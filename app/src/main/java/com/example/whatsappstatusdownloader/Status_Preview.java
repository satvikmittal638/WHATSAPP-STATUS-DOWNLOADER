package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

public class Status_Preview extends AppCompatActivity {
ImageView prev,share,play,close;
VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status__preview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prev=findViewById(R.id.image_prev);
        play=findViewById(R.id.play_btn);
        share=findViewById(R.id.share_btn);
        close=findViewById(R.id.close_btn);
        videoView=findViewById(R.id.videoView);

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
                prev.setVisibility(View.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(uri);
                Log.d("video","uri inserted vv");

                MediaController mediaController=new MediaController(Status_Preview.this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
                play.setVisibility(View.INVISIBLE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}