package com.example.edios;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class CallReceiver extends BroadcastReceiver {


    static boolean ring = false;
    static boolean callReceived = false;
    public String callerPhoneNumber;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context mContext, Intent intent) {
        Log.e(TAG, "onReceive: ");
        // Get the current Phone State
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state == null)
            return;
        // If phone state "Ringing"
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            ring = true;
            // Get the Caller's Phone Number
            Bundle bundle = intent.getExtras();
            assert bundle != null;
            callerPhoneNumber = bundle.getString("incoming_number");
            Toast.makeText(mContext, "It is ringing: " + callerPhoneNumber, Toast.LENGTH_LONG).show();
            Log.d(TAG, "it's Ringing");
        }


        // If incoming call is received
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Toast.makeText(mContext, "It is receives : " + callerPhoneNumber, Toast.LENGTH_LONG).show();
            Log.d(TAG, "it's Received");
            callReceived = true;
        }


        // If phone is Idle
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            // If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
            if (!callReceived)
                if (ring) {
                    Log.d(TAG, "it's Missed CAall");
                    Toast.makeText(mContext, "It was A MISSED CALL from : " + callerPhoneNumber, Toast.LENGTH_LONG).show();
                }
        }

    }


}

