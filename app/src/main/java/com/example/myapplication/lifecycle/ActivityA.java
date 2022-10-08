package com.example.myapplication.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

public class ActivityA extends AppCompatActivity {

    private static final String TAG = "ActivityA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        setTitle("Activity A");
        Log.d(TAG, "onCreate A");


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(ActivityA.TAG, "onRestart A");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(ActivityA.TAG, "onStart A");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(ActivityA.TAG, "onStop A");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ActivityA.TAG, "onResume A");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ActivityA.TAG, "onPause A");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ActivityA.TAG, "onDestroy A");
    }

    public void clicked(View view){
        switch (view.getId()){
            case R.id.buttonA:
                startActivity(new Intent(this, ActivityA.class));
                break;
            case R.id.buttonB:
                startActivity(new Intent(this, ActivityB.class));
                break;
            case R.id.buttonC:
                startActivity(new Intent(this, ActivityC.class));
                break;
        }
    }
}