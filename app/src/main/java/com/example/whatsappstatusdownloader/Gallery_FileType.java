package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class Gallery_FileType extends AppCompatActivity {
private RecyclerView rec_forAllFiles;
private FileTypeAdapter fileTypeAdapter;
private String filetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery__file_type);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent=getIntent();
        filetype=intent.getStringExtra("type");

        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        setupRec();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


    }

    private void setupRec(){
        rec_forAllFiles=findViewById(R.id.rec_forAllFiles);
        fileTypeAdapter=new FileTypeAdapter(filetype,getArrayListOfTheData());

        rec_forAllFiles.setHasFixedSize(true);
        rec_forAllFiles.setLayoutManager(new LinearLayoutManager(Gallery_FileType.this));

        rec_forAllFiles.setAdapter(fileTypeAdapter);

    }


    private ArrayList<StoryModel> getArrayListOfTheData() {
        ArrayList<StoryModel> filesList=new ArrayList<>();
        File[] files;
        StoryModel model;
        String targetPath;

        if (filetype.equals(Params_Constants.SAVE_FOLDER_NAME))
            targetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+Params_Constants.SAVE_FOLDER_NAME;
        else
        targetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+Params_Constants.FOLDER_NAME+"Media/WhatsApp "+filetype;

        Log.d("pathsW","Got the path for the desired filetype");
        File targetDirectory=new File(targetPath);

        files=targetDirectory.listFiles();


        for (File file : files) {
            model = new StoryModel();

            if(!(file.getName().equals("Sent")||file.getName().equals("Private"))) {

                model.setFileUri(Uri.fromFile(file));
                model.setPath(file.getAbsolutePath());
                model.setFilename(file.getName());

                //works only if a media is available
                if (!(model.getFileUri().toString().endsWith(".nomedia")))
                    filesList.add(model);
            }
        }

        return filesList;
    }
}