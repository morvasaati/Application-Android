package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.content.Intent;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    Button clk;
    VideoView vid;
    MediaController mediac;
    Button inst;
    String st;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         vid = (VideoView)findViewById(R.id.videoView);
         clk = (Button) findViewById(R.id.btnPlay);
         mediac = new MediaController(this);
         inst = (Button) findViewById(R.id.btninst);
         st = getIntent().getExtras().getString("Value");
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Instruction.class);
                intent.putExtra("Value2" , st );
                startActivity(intent);
                //ShowInstruction();
            }
        });
    }
    public void PlayVideo(View v){
        vid.setMediaController(mediac);
        String videopath ="android.resource://com.example.myapplication/"+R.raw.typesofleaves;
        vid.setVideoURI(Uri.parse(videopath));
        mediac.setAnchorView(vid);
        vid.start();

    }
    public void ShowInstruction(){
        Intent intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }

}

