package com.example.myapplication.smartWallet;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.smartWallet.model.Payment;


import java.util.Arrays;
import java.util.Date;

public class AddPaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Payment payment;
    private String payment_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        EditText name = (EditText) findViewById(R.id.name);
        EditText cost = (EditText) findViewById(R.id.cost);
        Button save = (Button) findViewById(R.id.saveBtn);
        Button delete = (Button) findViewById(R.id.deleteBtn);
        TextView timeOfPayment = (TextView) findViewById(R.id.timestamp);
        String timestamp = "";

        Spinner spinnerPaymentType = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentType.setAdapter(adapter);

        spinnerPaymentType.setOnItemSelectedListener(this);

        payment = AppState.get().getCurrentPayment();
        if (payment != null) {
            name.setText(payment.getName());
            cost.setText(String.valueOf(payment.getCost()));
            timeOfPayment.setText("Time of payment: " + payment.timestamp);
            spinnerPaymentType.setSelection(Arrays.asList(R.array.payment).indexOf(payment.getType()));
        } else {
            timeOfPayment.setText("");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment != null){
                    if(cost.getText() != null && name.getText() != null){
                        payment.setCost(Double.parseDouble(cost.getText().toString()));
                        payment.setName(name.getText().toString());
                        payment.setType(payment_type);
                    }
                    //save(payment.timestamp);
                    AppState.get().getDatabaseReference().child(payment.timestamp).setValue(payment);
                }
                else {
                    if(cost.getText() != null && name.getText() != null){
                        Payment newPayment = new Payment(getCurrentTime(),Double.parseDouble(cost.getText().toString()),name.getText().toString(),payment_type);
                        AppState.get().getDatabaseReference().child(getCurrentTime()).setValue(newPayment);

                    }else{
                        Toast.makeText(getApplicationContext(), "Please fill the cost and description fields!", Toast.LENGTH_SHORT).show();
                    }

                }
                AppState.get().setCurrentPayment(null);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (payment != null)
                    AppState.get().getDatabaseReference().child(payment.timestamp).removeValue();
                else
                    Toast.makeText(getApplicationContext(), "Payment does not exist", Toast.LENGTH_SHORT).show();
                AppState.get().setCurrentPayment(null);
                finish();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        payment_type = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    public static String getCurrentTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}