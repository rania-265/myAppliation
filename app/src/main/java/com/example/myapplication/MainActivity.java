package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExambleDialog.ExambleDialogListener {
    Button buttonCamera;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Photos> photosList;
    ImageAdapter imageAdapter;
    Uri imageUri;
    Bitmap bitmap;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            return;
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
        initial();
        buttonCamera = findViewById(R.id.buttonTake);
        buttonCamera.setOnClickListener(v -> confirmFireMissiles());


    }

    public void confirmFireMissiles() {
        ExambleDialog newFragment = new ExambleDialog();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 0:
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap bitmapImage = (Bitmap) extras.getParcelable("data");
            Photos photo = new Photos();
            photo.setImage(bitmapImage);
            photo.setNumber(photosList.size() + 1);
            photosList.add(photo);
            imageAdapter.notifyDataSetChanged();

        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Photos galleryPhoto = new Photos();
            galleryPhoto.setNumber(photosList.size() + 1);
            galleryPhoto.setImage(bitmap);
            photosList.add(galleryPhoto);
            imageAdapter.notifyDataSetChanged();


        }
    }


    public void initial() {
        photosList = new ArrayList<Photos>();
        recyclerView = findViewById(R.id.recyclerImage);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(MainActivity.this, photosList);
        recyclerView.setAdapter(imageAdapter);

    }
}



