package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Status extends AppCompatActivity {
private SwipeRefreshLayout swipeRefreshLayout;
private RecyclerView recyclerView;

private RecyclerViewAdapter adapter;
File[] files;  //array of files
private ArrayList<StoryModel> filesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerView=findViewById(R.id.recyclerView);



        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        setupLayout();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void setupLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               swipeRefreshLayout.setRefreshing(true);
                setupRecV();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_SHORT).show();
                    }
                },1000);
            }
        });



    }

    private void setupRecV(){
        filesList.clear();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Status.this));
        adapter=new RecyclerViewAdapter(Status.this,getArrayListOfTheData());

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();//tells the UI thread to update itself because the data has been changed
    }
    private ArrayList<StoryModel> getArrayListOfTheData() {
        StoryModel model;
        String targetPath= Environment.getExternalStorageDirectory().getAbsolutePath()+Params_Constants.FOLDER_NAME+"Media/.Statuses";
        File targetDirectory=new File(targetPath);

        files=targetDirectory.listFiles();

        for (File file : files) {
            model = new StoryModel();

            model.setFileUri(Uri.fromFile(file));
            model.setPath(file.getAbsolutePath());
            model.setFilename(file.getName());

            //ignored the .nomedia file
            if (!(model.getFileUri().toString().endsWith(".nomedia")))
                filesList.add(model);
        }

        return filesList;
    }


}