//TakePicture.java for showing video with delay and with progress bar
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.CaseMap;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.LogRecord;


import static android.os.Environment.getExternalStoragePublicDirectory;

public class TakePicture extends AppCompatActivity {

    Button btnTakePic;
    ImageView imageView;
    String pathToFile;
    long startTime;
    long endTime;
    long seconds;
    boolean flag = false;
    String st;
    Bitmap bitmap;
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    int[] imagesLeaves = {R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
            R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n};
    Random rand = new Random();
    int repeatednumber = rand.nextInt(imagesLeaves.length);
    //public static SQLiteHelper sqLiteHelper;
//    SQLiteHelper db;
    boolean isProcessing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mLoadingText = (TextView) findViewById(R.id.loadingCompleteTextView);
        btnTakePic = findViewById(R.id.btnTakePic);
        imageView = findViewById(R.id.imageView);
        st = getIntent().getExtras().getString("Value3");
        setRandomImage(repeatednumber);

//        db = new SQLiteHelper(this);
        isProcessing = false;

        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                if (!isProcessing) {
                    isProcessing = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mProgressStatus == 0 || mProgressStatus == 50) {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                dispatchPictureTakerAction();
                            } else if (mProgressStatus == 25 || mProgressStatus == 75) {
                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                dispatchPictureTakerAction();
                            } else {
                                Intent intent = new Intent(TakePicture.this, FirstPage.class);
                                startActivity(intent);
                                //finish();
                                //System.exit(0);
                            }
                            //Toast.makeText(getApplicationContext(),"Activity is Finished", Toast.LENGTH_LONG).show();
                            mProgressBar.setScaleY(4f);
                            mProgressStatus += 25;
                            //try to sleep the thread for 2000 miliseconds

                            //update the progress bar
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(mProgressStatus);
                                    mLoadingText.setText(mProgressStatus + "%");
                                }
                            });
                            isProcessing = false;
                        }
                    }).start();
                }
            }
        });
    }

    public int getRandomNum(){
        while (true) {
            int randomNumber = rand.nextInt(imagesLeaves.length);
            if (randomNumber != repeatednumber) {
                repeatednumber= randomNumber;
                setRandomImage(repeatednumber);
                return repeatednumber;
            }
        }
    }
    public void setRandomImage(int randNum){
        imageView.setImageResource(imagesLeaves[randNum]);
    }


    private byte[] imageViewToByte(Bitmap bitmap){
        //Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private byte[] imageViewToByte2(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                bitmap = BitmapFactory.decodeFile(pathToFile);
                getRandomNum();
                //imageView.setImageBitmap(bitmap);
                endTime = System.currentTimeMillis();
            }
            System.out.println(seconds);
            seconds = durationActivity(startTime,endTime);
//            db.insertData(st,imageViewToByte2(imageView),imageViewToByte(bitmap),seconds);
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

