package com.example.whatsappstatusdownloader;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
private Context context;
private ArrayList<StoryModel> filesList;

    public RecyclerViewAdapter(Context context, ArrayList<StoryModel> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_status,parent,false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        StoryModel storyModel=filesList.get(position);

        if(storyModel.getFileUri().toString().endsWith(".mp4")) {
            holder.filetype.setText("Video");
            holder.filetypeImage.setImageResource(R.drawable.video);
        }
        else {
            holder.filetype.setText("Photo");
            holder.filetypeImage.setImageResource(R.drawable.image);
        }

        Glide.with(context)
                .load(storyModel.getFileUri())
                .into(holder.mainImage);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker();
                //initial file from whatApp folder
                String init_path=storyModel.getPath();
                final File init_file=new File(init_path);

                //destination folder for saving whatsApp status
                String dest_path=Environment.getExternalStorageDirectory().getAbsolutePath()+Params_Constants.SAVE_FOLDER_NAME;
                File dest_file=new File(dest_path);

                try {
                    FileUtils.copyFileToDirectory(init_file,dest_file);
                } catch (IOException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                //now file is copied

                MediaScannerConnection.scanFile(
                        context,
                        new String[]{dest_path + storyModel.getFilename()},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            //not used here
                            @Override
                            public void onMediaScannerConnected() {

                            }
                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        }
                );

                Toast.makeText(context,"Saved file successfully to: "+dest_path+storyModel.getFilename(),Toast.LENGTH_SHORT).show();

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Status_Preview.class);
                intent.putExtra("uri",storyModel.getFileUri().toString());
                context.startActivity(intent);
            }
        });



    }

    private void checker() {
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+Params_Constants.FOLDER_NAME;
        File dir=new File(path);
        boolean isDirectoryCreated=dir.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated=dir.mkdir();

//        if(isDirectoryCreated)
//            Toast.makeText(context,"Directory Already Created",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
     CardView cardView;

    ImageView download,filetypeImage;
    CircleImageView mainImage;
    TextView filetype,date;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            download=itemView.findViewById(R.id.download_btn);
            mainImage=itemView.findViewById(R.id.stat_image);
            cardView=itemView.findViewById(R.id.cardview);

            filetype=itemView.findViewById(R.id.stat_filetype);
            filetypeImage=itemView.findViewById(R.id.filetypeImage);
            date=itemView.findViewById(R.id.stat_date);

        }
    }
}
