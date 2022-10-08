package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText eText;
    private Button bClick;
    private TextView tName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* EditText eText = (EditText) findViewById(R.id.eName);
        Button bClick = (Button) findViewById(R.id.bClick);
        TextView tName = (TextView) findViewById(R.id.tName);

        bClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String s = "Hello, " + eText.getText() + "!";
                tName.setText(s);
            }
        });*/
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void clicked(View view){
        switch (view.getId()){
            case R.id.bClick:
                EditText eText = (EditText) findViewById(R.id.eName);
                String s = "Hello , " + eText.getText();


                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setMessage(s);
                dialog.setPositiveButton("Hello!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "You say \"Hello!\"", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "You didn't say \"Hello!\" back", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();

                break;
            default: break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String color = parent.getItemAtPosition(position).toString();
        String colorCode = "#000000";
        bClick = (Button) findViewById(R.id.bClick);
        switch (color){
            case "red":
                colorCode = "#FF0000";
                break;
            case "black":
                colorCode = "#000000";
                break;
            case "white":
                colorCode = "#ffffff";
                break;
            case "blue":
                colorCode = "#0000ff";
                break;
            case "green":
                colorCode = "#00ff00";
                break;
            case "yellow":
                colorCode = "#ffff00";
                break;
        }
        bClick.setTextColor(Color.parseColor(colorCode));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}