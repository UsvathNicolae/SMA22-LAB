package com.example.myapplication.smartWallet;

import android.content.Intent;
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
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainWallet extends AppCompatActivity {

        private DatabaseReference databaseReference;
        private int currentMonth;
        private List<Payment> payments = new ArrayList<>();
        private TextView tStatus;
        private Button bNext, nPrevious;
        private FloatingActionButton fabAdd;
        private ListView listView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_wallet);

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
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Payment payment = dataSnapshot.getValue(Payment.class);
                        payments.add(payment);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainWallet.this, AddPaymentActivity.class));
                }
            });
        }
}
