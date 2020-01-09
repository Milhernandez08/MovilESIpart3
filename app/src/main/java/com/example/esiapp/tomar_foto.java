package com.example.esiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.esiapp.Interface.CromasBD;
import com.example.esiapp.Modelo.POST;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tomar_foto extends AppCompatActivity {

    private ImageView img;
    private LinearLayout boton;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_foto);

        img = (ImageView)findViewById(R.id.imgCroma);
        boton = (LinearLayout)findViewById(R.id.boton_Camara);

        /*if (ContextCompat.checkSelfPermission(tomar_foto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(tomar_foto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(tomar_foto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }*/

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomar_Foto();
            }
        });

        texto = findViewById(R.id.jsonTex);

        getPost();

    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void tomar_Foto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.esiapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }

    private void getPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.9:3333/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CromasBD cromasBD = retrofit.create(CromasBD.class);

        Call<List<POST>> call = cromasBD.getPost();
        call.enqueue(new Callback<List<POST>>() {
            @Override
            public void onResponse(Call<List<POST>> call, Response<List<POST>> response) {
                if(!response.isSuccessful()){
                    texto.setText("Codigo: " + response.code());
                    return;
                }

                List<POST> res = response.body();

                for (POST p: res){
                    texto.append(p.getNombre());
                }
            }

            @Override
            public void onFailure(Call<List<POST>> call, Throwable t) {
                texto.setText(t.getMessage());
            }
        });
    }



}
