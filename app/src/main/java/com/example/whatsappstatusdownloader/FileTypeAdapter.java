package com.example.whatsappstatusdownloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class FileTypeAdapter extends RecyclerView.Adapter<FileTypeAdapter.vH> {
    private String filetype;
    private ArrayList<StoryModel> listOfData;

    public FileTypeAdapter(String filetype,ArrayList<StoryModel> listOfData) {
        this.filetype = filetype;
        this.listOfData = listOfData;
    }

    @NonNull
    @Override
    public FileTypeAdapter.vH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_individual_filetype,parent,false);
        return new vH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vH holder, int position) {
        StoryModel file=listOfData.get(position);

        if(filetype.equals(Params_Constants.SAVE_FOLDER_NAME)) {

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context=holder.del.getContext();
                    Intent intent=new Intent(context,Status_Preview.class);
                    intent.putExtra("uri",file.getFileUri().toString());
                    context.startActivity(intent);

                }
            });
            
                if (file.getFileUri().toString().endsWith(".mp4"))
                    holder.filename.setText("Video");
                else
                    holder.filename.setText("Image");
            }
            else
                holder.filename.setText(file.getFilename());


            Glide.with(holder.del.getContext())
                    .load(file.getFileUri())
                    .into(holder.filetype_prev);

        }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }





    public class vH extends RecyclerView.ViewHolder{

    CardView cardView;
    ImageView filetype_prev,del;
    TextView filename,date_file;
        public vH(@NonNull View itemView) {
            super(itemView);
            filetype_prev=itemView.findViewById(R.id.filetype_prev);
            del=itemView.findViewById(R.id.delete_file);
            filename=itemView.findViewById(R.id.filename);
            date_file=itemView.findViewById(R.id.filetype_date);
            cardView=itemView.findViewById(R.id.cardview_filetype);
        }
    }
}
