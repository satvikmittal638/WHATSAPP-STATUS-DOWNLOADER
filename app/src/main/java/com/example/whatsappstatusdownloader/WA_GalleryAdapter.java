package com.example.whatsappstatusdownloader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WA_GalleryAdapter extends RecyclerView.Adapter<WA_GalleryAdapter.WA_GalleryViewHolder> {
    ArrayList<String> typesList;

    public WA_GalleryAdapter(ArrayList<String> typesList) {
        this.typesList = typesList;
    }

    @NonNull
    @Override
    public WA_GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wa_single_option,parent,false);
        return new WA_GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WA_GalleryViewHolder holder, int position) {
        String type=typesList.get(position);

        holder.type.setText("Whatsapp "+type);

        switch (type){
            case "Video":
                holder.type_img.setImageResource(R.drawable.video);
                break;
            case "Images":
                holder.type_img.setImageResource(R.drawable.image);
                break;
            case "Documents":
                holder.type_img.setImageResource(R.drawable.doc);
                break;
            case "Audio":
                holder.type_img.setImageResource(R.drawable.audio);
                break;
            default:
                holder.type_img.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.cardView_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=holder.type_img.getContext();
                Intent intent=new Intent(context,Gallery_FileType.class);
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return typesList.size();
    }

    public class WA_GalleryViewHolder extends RecyclerView.ViewHolder{
        ImageView type_img;
        TextView type;
        CardView cardView_nav;
        public WA_GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            type_img=itemView.findViewById(R.id.type_img);
            cardView_nav=itemView.findViewById(R.id.cardview_nav);
            type=itemView.findViewById(R.id.WA_type);
        }
    }

}
