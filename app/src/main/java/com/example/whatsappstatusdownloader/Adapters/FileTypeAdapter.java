package com.example.whatsappstatusdownloader.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.example.whatsappstatusdownloader.ParamsAndConstants.Params_Constants;
import com.example.whatsappstatusdownloader.R;
import com.example.whatsappstatusdownloader.Status_Preview;
import com.example.whatsappstatusdownloader.Model.StoryModel;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        if(filetype.equals("Images")||filetype.equals("Video")) {
            holder.filename.setText(file.getFilename());
            Glide.with(holder.del.getContext())
                    .load(file.getFileUri())
                    .into(holder.filetype_prev);
        }

        else if(filetype.equals(Params_Constants.SAVE_FOLDER_NAME)) {

            Glide.with(holder.del.getContext())
                    .load(file.getFileUri())
                    .into(holder.filetype_prev);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context=holder.del.getContext();
                    Intent intent=new Intent(context, Status_Preview.class);
                    intent.putExtra("uri",file.getFileUri().toString());
                    context.startActivity(intent);

                }
            });
            
                    if (file.getFileUri().toString().endsWith(".mp4"))
                        holder.filename.setText("Video");
                    else
                        holder.filename.setText("Image");

            }

        //now works fine
        else  {

                holder.filename.setText(file.getFilename());

            if(filetype.equals("Documents"))
                {
                    if(file.getFileUri().toString().endsWith(".pdf")) {
                    holder.filetype_prev.setImageResource(R.drawable.pdf);
                    Log.d("fdb",".pdf found");
                }

                    else if(file.getFileUri().toString().endsWith(".docx")) {
                    holder.filetype_prev.setImageResource(R.drawable.docx);
                    Log.d("fdb",".docx found");
                }

                    else if(file.getFileUri().toString().endsWith(".xlsx")) {
                    holder.filetype_prev.setImageResource(R.drawable.xlsx);
                    Log.d("fdb",".xlsx found");
                }

                    else if(file.getFileUri().toString().endsWith(".pptx")) {
                    holder.filetype_prev.setImageResource(R.drawable.pptx);
                    Log.d("fdb",".pptx found");
                }

                    else if(file.getFileUri().toString().endsWith(".txt")) {
                    holder.filetype_prev.setImageResource(R.drawable.txt);
                    Log.d("fdb",".txt found");
                }

//                  else{
//                      holder.filetype_prev.setImageResource(R.drawable.doc);
//                      Log.d("fdb","no file found");
//                }

            }

            else
                holder.filetype_prev.setImageResource(R.drawable.audio);


        }

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(holder.del.getContext());

                dialogBuilder.setMessage("Do you want to delete this file ?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Do nothing
                            }
                        })

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                File fileToDelete=new File(file.getPath());
                                if(fileToDelete.delete()) {
                                    //some prbls here
                                    listOfData.remove(position);
//                                  notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(holder.del.getContext(), "File deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                AlertDialog showDialog=dialogBuilder.create();
                showDialog.setTitle("WhatsApp Status Downloader");
                showDialog.show();

            }
        });

        }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }





    public class vH extends RecyclerView.ViewHolder{

    CardView cardView;
    CircleImageView filetype_prev;
    ImageView del;
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
