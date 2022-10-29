package com.example.myapplication.laborator5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            String state = extras.getString(BatteryManager.EXTRA_STATUS);
            Log.d("My_debug_tag",state);
            if(state.equals(BatteryManager.EXTRA_PLUGGED)){
                String level = extras.getString(BatteryManager.EXTRA_LEVEL);
                Log.d("My_debug_tag",level);
            }
        }
    }
}