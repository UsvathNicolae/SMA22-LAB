package com.example.myapplication.laborator4;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ImageActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        if(myApplication.getBitmap() == null){
            Toast.makeText(this, "Error transmitting URL",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            ImageView imageView = (ImageView) findViewById(R.id.imageview);
            imageView.setImageBitmap(myApplication.getBitmap());
        }
    }


}
