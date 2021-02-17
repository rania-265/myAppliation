package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public  class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Photos> photosList;
    private Context context;


    public ImageAdapter(Context context, ArrayList<Photos> photosList) {
        this.context = context;
        this.photosList = photosList;

    }

    public static class ImagePicker extends RecyclerView.ViewHolder {
        TextView textImage;
        ImageView showImage;
        Button deleteImage;

        public ImagePicker(@NonNull View itemView) {
            super(itemView);
            textImage = itemView.findViewById(R.id.textTitle);
            showImage = itemView.findViewById(R.id.imageView);
            deleteImage = itemView.findViewById(R.id.buttonDelete);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_design, parent, false);
        return new ImagePicker(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImagePicker viewHolder = (ImagePicker) holder;
        viewHolder.textImage.setText("index Of Image" + photosList.get(position).getNumber());
        viewHolder.showImage.setImageBitmap(photosList.get(position).getImage());
        viewHolder.showImage.requestLayout();
        viewHolder.showImage.getLayoutParams().height = 500;
        viewHolder.showImage.getLayoutParams().width = 500;
        viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.Deleting);
                builder.setMessage(R.string.sureDeleting);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "This Image  delete", Toast.LENGTH_SHORT).show();
                        photosList.remove(position);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemRemoved(position);
                                notifyItemChanged(position);
                                viewHolder.showImage.setVisibility(v.GONE);
                            }
                        });


                    }


                }).setNegativeButton(context.getString(R.string.canceling), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "This Image is not delete", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                });
                builder.create();
                builder.show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }




}


