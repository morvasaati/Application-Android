package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class Randompicture extends AppCompatActivity {

    ImageView imageView;
    Button button_takephoto;
    int[] images = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randompicture);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(images[rand.nextInt(images.length)]);
        button_takephoto = (Button) findViewById(R.id.button);
        button_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTakePicture();
            }
        });
    }
    public void OpenTakePicture(){
        Intent intent = new Intent(this,TakePicture.class);
        startActivity(intent);
    }

}