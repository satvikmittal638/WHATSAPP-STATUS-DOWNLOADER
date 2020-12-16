package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class WhatsappGallery extends AppCompatActivity {
private RecyclerView recyclerView;
private WA_GalleryAdapter adapter;
private ArrayList<String> typesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatsapp_gallery);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        typesList.add("Video");
        typesList.add("Images");
        typesList.add("Documents");
        typesList.add("Audio");

        recyclerView=findViewById(R.id.recyclerV_types);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new WA_GalleryAdapter(typesList);

        recyclerView.setAdapter(adapter);



    }
}