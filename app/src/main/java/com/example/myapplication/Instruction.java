package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Instruction extends AppCompatActivity {

    int[] images = {R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
            R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n};
    String[] names = {"Cypress leaf", "Elm leaf", "Holly leaf", "Hornbeam leaf", "Irishyew leaf", "Juniper leaf", "Lime leaf", "Oak leaf", "Pine leaf", "Sycamore leaf"};

    ListView listView;
    ArrayList<String> mylist = new ArrayList<String>();
    public static String strSeparator = "__,__";
    String st;

//    SQLiteHelper db;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);


       // Button button_save = (Button) findViewById(R.id.save);
        Button button_next = (Button) findViewById(R.id.next);

        listView = (ListView) findViewById(R.id.listView);
        st = getIntent().getExtras().getString("Value2");

//        db = new SQLiteHelper(this);


        adapter = new CustomAdapter();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

       //button_next
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.insertFavLeaves(st , convertArrayToString(getNames()));
                Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Instruction.this,TakePicture.class);
                intent.putExtra("Value3" , st );
                startActivity(intent);
                //OpenTakePicture();
            }
        });

    }

    public static String convertArrayToString(ArrayList<String> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            str = str + array.get(i);
            // Do not append comma at the end of last element
            if (i < array.size() - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public ArrayList<String> getNames(){
        ArrayList<String> selected = new ArrayList<String>();
        for (int k = 0;k<adapter.modelArrayList.size();k++) {
            if (adapter.modelArrayList.get(k).isSelected){
                Log.d("tooye if","true e");
                System.out.println(adapter.modelArrayList.get(k).getLeave());
                selected.add(adapter.modelArrayList.get(k).getLeave());
            }
        }
        return selected;

    }

    public void openRandomPicture() {
        Intent intent = new Intent(this, Randompicture.class);
        startActivity(intent);
    }
    public void OpenTakePicture(){
        Intent intent = new Intent(this,TakePicture.class);
        startActivity(intent);
    }

    public ArrayList<String> creatSelectedItemsArr(String name) {
        mylist.add(name);
        return mylist;
    }

    public void showArr(ArrayList<String> s) {
        for (int i = 0; i < s.size(); i++) {
            System.out.print(s.get(i));
        }
    }

    public class Model {

        private boolean isSelected;
        private String leave;

        public Model(String leave, boolean isSelected) {
            this.leave = leave;
            this.isSelected = isSelected;
        }

        public boolean getSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getLeave() {
            return leave;
        }

        public void setLeave(String leaf) {
            this.leave = leaf;
        }

    }

    class CustomAdapter extends BaseAdapter {
        public ArrayList<Model> modelArrayList;
        public HashMap<String,String> checked = new HashMap<String,String>();

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        public CustomAdapter() {
            super();
            modelArrayList = new ArrayList<Model>();
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

        private class ViewHolder {
            RelativeLayout item;
            ImageView image;
            TextView text;
            CheckBox cb;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            Bitmap b = decodeSampledBitmapFromResource(getResources(),images[i], 100, 100);
            imageView.setImageBitmap(b);
            textView_name.setText(names[i]);
            checkBox.setChecked(false);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getApplicationContext(), "" + i,
                            Toast.LENGTH_SHORT).show();
                    if(isChecked){
                        modelArrayList.get(i).setSelected(true);
                    }else{
                        modelArrayList.get(i).setSelected(false);
                    }

                }
            });
            modelArrayList.add(new Model(names[i], false));
            return view;
        }

        public  int calculateInSampleSize(BitmapFactory.Options options,
                                                int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and
                // width
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);

                // Choose the smallest ratio as inSampleSize value, this will
                // guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }

            return inSampleSize;
        }

        public  Bitmap decodeSampledBitmapFromResource(Resources res,
                                                             int resId, int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }
    }
}

