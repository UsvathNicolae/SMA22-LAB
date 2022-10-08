package com.example.myapplication.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class MainIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent);
    }

    public void clicked(View view){
        switch (view.getId()){
            case R.id.buttona:
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                String url = "https://www.google.com/";
                viewIntent.setData(Uri.parse(url));
                if (viewIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewIntent);
                }
                break;
            case R.id.buttonb:
                Intent viewIntent2 = new Intent(Intent.ACTION_VIEW);
                String phone = "tel:00401213456";
                viewIntent2.setData(Uri.parse(phone));
                if (viewIntent2.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewIntent2);
                }
                break;
            case R.id.buttonc:

                break;
            case R.id.buttond:

                break;
            default:
                break;
        }
    }
}