
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstPage extends AppCompatActivity {

  Button btn;
  EditText et;
  //String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        TextView textView = findViewById(R.id.text_view);
        String text = "How well do you know your leaves?" ;
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLACK);
        ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.rgb(72,163,84));

        ss.setSpan(fcsBlue , 0 , 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsGreen , 26, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        et = findViewById(R.id.edittext);
        closeKeyboard();
        btn = (Button) findViewById(R.id.btnWatchVideo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, MainActivity.class);
                intent.putExtra("Value" , et.getText().toString());
                startActivity(intent);
               // watchVideo();
            }
        });


    }
    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void watchVideo(){
       Intent intent = new Intent(this, MainActivity.class);
       // Intent.putExtra("Value" , et.getText().toString());
        startActivity(intent);
    }
}
