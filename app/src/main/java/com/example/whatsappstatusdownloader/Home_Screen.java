package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Home_Screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home__screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void toStatus(View view) {
        startActivity(new Intent(this,Status.class));
    }

    public void toSavedStatus(View view) {
        Intent intent=new Intent(this,Gallery_FileType.class);
        intent.putExtra("type",Params_Constants.SAVE_FOLDER_NAME);
        startActivity(intent);
    }

    public void toWhatsappGallery(View view) {
        startActivity(new Intent(this,WhatsappGallery.class));
    }
}