package com.example.myapplication.smartWallet;

import static com.example.myapplication.smartWallet.MainWallet.getCurrentTime;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.myapplication.smartWallet.model.Payment;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState singletonObject;

    public static synchronized AppState get() {
        if (singletonObject == null) {
            singletonObject = new AppState();
        }
        return singletonObject;
    }

    // reference to Firebase used for reading and writing data
    private DatabaseReference databaseReference;
    // current payment to be edited or deleted
    private Payment currentPayment;

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void setCurrentPayment(Payment currentPayment) {
        this.currentPayment = currentPayment;
    }

    public Payment getCurrentPayment() {
        return currentPayment;
    }

    public static void updateLocalBackup(Context context, Payment payment, boolean toAdd) {
        String fileName = payment.timestamp;

        try {
            if (toAdd) {
                FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(payment.copy());
                os.close();
                fos.close();
            } else {
                context.deleteFile(fileName);
            }
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean hasLocalStorage(Context context) {
        return context.getFilesDir().listFiles().length > 0;
    }

    public static List<Payment> loadFromLocalBackup(Context context, String month) {
        try {
            List<Payment> payments = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {
                FileInputStream fis = context.openFileInput(file.getName());
                ObjectInputStream is = new ObjectInputStream(fis);
                Payment payment = (Payment) is.readObject();
                if (month.equals(MainWallet.months[parseInt(getCurrentTime().substring(5,7))])) {
                        payments.add(payment);
                }
                is.close();
                fis.close();
            }

            return payments;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}