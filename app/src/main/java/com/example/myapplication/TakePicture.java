package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;


import static android.os.Environment.getExternalStoragePublicDirectory;

public class TakePicture extends AppCompatActivity {

    Button btnTakePic;
    ImageView imageView;
    String pathToFile;
    long startTime;
    long endTime;
    long seconds;
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mLoadingText = (TextView) findViewById(R.id.loadingCompleteTextView);
        btnTakePic = findViewById(R.id.btnTakePic);
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                            if (mProgressStatus == 0 || mProgressStatus == 25 || mProgressStatus == 50 || mProgressStatus==75 ){
                                dispatchPictureTakerAction();
                                imageView = findViewById(R.id.imageView);
                            }
                            mProgressStatus +=25;
                            //try to sleep the thread for 20 miliseconds
                            try{
                            Thread.sleep(200);}
                            catch(InterruptedException e){
                                e.printStackTrace();
                            }
                            //update the progress bar
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(mProgressStatus);
                                    mLoadingText.setText(mProgressStatus + "%");
                                }
                            });


                    }
                }).start();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                imageView.setImageBitmap(bitmap);
                endTime = System.currentTimeMillis();
            }
            seconds = durationActivity(startTime,endTime);
            System.out.println(seconds);
        }
    }
    private long durationActivity(long start, long end ){
        long duration = (end - start)/1000;
        return  duration;

    }
    private void dispatchPictureTakerAction(){
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takepic.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            photoFile = createPhotoFile();
            Log.d("resid inja" , "photo file sakhte shod");
            if (photoFile !=null){
                pathToFile = photoFile.getAbsolutePath();
                 Uri photoURI = FileProvider.getUriForFile(TakePicture.this,"com.example.myapplication.fileprovider",photoFile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takepic,1);
            }

        }
    }
    private File createPhotoFile(){
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
    try{
        image = File.createTempFile(name, ".jpg", storageDir);
    }catch(IOException e){
        Log.d("mylog", "Excep : " + e.toString());
    }
    Log.d("createphotofile" , "photofile was created");
    return image;

    }
}
