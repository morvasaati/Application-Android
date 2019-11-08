package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import java.util.ArrayList;

public class Instruction extends AppCompatActivity {

    int[] images = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
    String[] names = {"A","B","C","D"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        ListView listView=(ListView) findViewById(R.id.listView);
        Button button_save=(Button) findViewById(R.id.save);
        Button button_next = (Button) findViewById(R.id.next);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRandomPicture();
            }
        });
        CustomAdapter customAdapter= new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    public void openRandomPicture(){
        Intent intent = new Intent(this, Randompicture.class);
        startActivity(intent);
    }

    public class Model {

        private boolean isSelected;
        private String leave;

        public boolean getSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
        public String getLeave() {
            return leave;
        }

        public void setLeave(String animal) {
            this.leave = animal;
        }

    }

    class CustomAdapter extends BaseAdapter {
        public ArrayList<Model> modelArrayList;
        @Override
        public int getViewTypeCount() {
            return getCount();
        }
        @Override
        public int getItemViewType(int position) {

            return position;
        }
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int i) {
            return modelArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        private class ViewHolder{
            RelativeLayout item;
            ImageView image;
            TextView text;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_name = (TextView) view.findViewById(R.id.textView_name);

            imageView.setImageResource(images[i]);
            textView_name.setText(names[i]);
            return view;
        }

    }
}

