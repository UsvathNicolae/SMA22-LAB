package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText eText;
    private Button bClick;
    private TextView tName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText eText = (EditText) findViewById(R.id.eName);
        Button bClick = (Button) findViewById(R.id.bClick);
        TextView tName = (TextView) findViewById(R.id.tName);

        bClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String s = "Hello, " + eText.getText() + "!";
                tName.setText(s);
            }
        });
    }

    public void clicked(View view){
        switch (view.getId()){
            case R.id.bClick:
                EditText eText = (EditText) findViewById(R.id.eName);
                TextView tName = (TextView) findViewById(R.id.tName);
                String s = "Hello , " + eText.getText();
                tName.setText(s);
                break;
            default: break;
        }
    }
}