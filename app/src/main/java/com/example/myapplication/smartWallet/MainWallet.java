package com.example.myapplication.smartWallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.smartWallet.model.Payment;
import com.example.myapplication.smartWallet.ui.PaymentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainWallet extends AppCompatActivity {

        private DatabaseReference databaseReference;
        private int currentMonth;
        private List<Payment> payments = new ArrayList<>();
        private TextView tStatus;
        private Button bNext, nPrevious;
        private FloatingActionButton fabAdd;
        private ListView listView;
        private static String months[] =
            {
                    null , "January" , "February" , "March" , "April", "May",
                    "June", "July", "August", "September", "October",
                    "November", "December"
            };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_wallet);

            SharedPreferences pref = getSharedPreferences("pref", 0);
            currentMonth = pref.getInt("month", -1);

            if(currentMonth == -1){
                currentMonth =1;
            }

            if(currentMonth > 12){
                currentMonth = 1;
            }

            if(currentMonth < 1){
                currentMonth = 12;
            }

            tStatus = (TextView) findViewById(R.id.tStatus);
            nPrevious = (Button) findViewById(R.id.bPrevious);
            bNext = (Button) findViewById(R.id.bNext);
            fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
            listView = (ListView) findViewById(R.id.listPayments);

            PaymentAdapter adaptor = new PaymentAdapter(this,R.layout.payment_item, payments);
            listView.setAdapter(adaptor);


            databaseReference = FirebaseDatabase.getInstance().getReference("wallet");

            databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int count = 0;
                    payments.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Payment payment = dataSnapshot.getValue(Payment.class);
                        if(payment!= null){
                            if(currentMonth == Integer.parseInt(payment.timestamp.substring(5,7))){
                                payments.add(payment);
                                count ++;
                            }
                        }
                    }
                    tStatus.setText("Found " + count + " items for " + months[currentMonth]);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentMonth = currentMonth + 1;
                    pref.edit().putInt("month", currentMonth).apply();
                    recreate();
                }
            });

            nPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentMonth = currentMonth - 1;
                    pref.edit().putInt("month", currentMonth).apply();
                    recreate();
                }
            });



            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainWallet.this, AddPaymentActivity.class));
                }
            });
        }

        public static String getCurrentTime()
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
            Date date = new Date();
            return simpleDateFormat.format(date);
        }
}