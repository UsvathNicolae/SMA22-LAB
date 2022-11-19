package com.example.myapplication.SmartWallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.SmartWallet.model.MonthlyExpenses;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WalletActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String currentMonth;
    private ValueEventListener databaseListener;
    private TextView entries;
    private EditText income, expenses, searchRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        entries = findViewById(R.id.tStatus);
        income =  findViewById(R.id.eIncome);
        expenses =  findViewById(R.id.eExpenses);
        searchRes =  findViewById(R.id.eSearch);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //databaseReference = database.getReference();
    }

    /*private  void createNewDBListener() {
        if (databaseReference != null && currentMonth != null && databaseListener != null)
            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);
        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MonthlyExpenses monthlyExpense = dataSnapshot.getValue(MonthlyExpenses.class);
                // explicit mapping of month name from entry key
                monthlyExpense.month = dataSnapshot.getKey();

                income.setText(String.valueOf(monthlyExpense.getIncome()));
                expenses.setText(String.valueOf(monthlyExpense.getExpenses()));
                entries.setText("Found entry for " + currentMonth);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        };

        databaseReference.child("calendar").child(currentMonth).addValueEventListener((ValueEventListener) databaseListener);
    }*/

}