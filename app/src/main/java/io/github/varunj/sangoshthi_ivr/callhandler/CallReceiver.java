/*
 * Copyright (c) 2017. Created by Deepak Sood
 */

package io.github.varunj.sangoshthi_ivr.callhandler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import io.github.varunj.sangoshthi_ivr.activities.CallActivity;
import io.github.varunj.sangoshthi_ivr.activities.HomeActivity;
import io.github.varunj.sangoshthi_ivr.activities.ShowActivity;
import io.github.varunj.sangoshthi_ivr.utilities.SharedPreferenceManager;

public class CallReceiver extends PhoneCallReceiver {

    private static final String TAG = CallReceiver.class.getSimpleName();

    ArrayList<String> serverNum;

    public void onCustomCallStateChanged(Context context, int state, String number) {
        super.onCustomCallStateChanged(context, state, number);

        try {

            Log.i(TAG, "state changed: " + state + " number: " + number);

            if (state == 0 && number != null) {
                if (isServerNumber(number)) {
                    Log.d(TAG, "call disconnected");
                    SharedPreferenceManager.getInstance().setCallReceived(false);
                    if (CallActivity.progressDialog != null && CallActivity.progressDialog.isShowing()) {
                        CallActivity.progressDialog.dismiss();
                    }
                    if (ShowActivity.progressDialog != null && ShowActivity.progressDialog != null && SharedPreferenceManager.getInstance().isShowRunning()) {
                        ShowActivity.progressDialog.show();
                    } else {
                        if (!SharedPreferenceManager.getInstance().isShowUpdateStatus()) {
                            Intent startHomeActivity = new Intent(context, HomeActivity.class);
                            startHomeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(startHomeActivity);
                            Log.d(TAG, "Not in show activity");
                        } else {
                            Log.d(TAG, "inside show activity after end show");
                        }
                    }
                }
            }

            // incoming call status = 1
            if (state == 1 && number != null) {
                if (isServerNumber(number)) {
                    // incoming call
                }
            }

            if (state == 2 && number != null) {
                if (isServerNumber(number)) {
                    SharedPreferenceManager.getInstance().setCallReceived(true);
                    if (CallActivity.progressDialog != null && CallActivity.progressDialog.isShowing()) {
                        CallActivity.progressDialog.dismiss();
                    }
                    if (ShowActivity.progressDialog != null && ShowActivity.progressDialog.isShowing() && SharedPreferenceManager.getInstance().isShowRunning()) {
                        ShowActivity.progressDialog.dismiss();
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Illegal Argument Exception" + e);
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e);
        }
    }

    private boolean isServerNumber(String incomingNumber) {
        if (serverNum == null) {
            serverNum = new ArrayList<>();
            serverNum.add("9643099799");
            serverNum.add("9643066633");
            serverNum.add("8377081661");
            serverNum.add("7291048199");
            serverNum.add("8377846695");
            serverNum.add("7291047922");
            serverNum.add("8377084994");
            serverNum.add("7291047811");
            serverNum.add("8377081771");
            serverNum.add("8377842350");
            serverNum.add("7291047622");
            serverNum.add("9560739967");
            serverNum.add("9971126889");
            serverNum.add("9599079805");
        }

        for (String serverNumberItem : serverNum) {
            if (incomingNumber.contains(serverNumberItem)) {
                return true;
            }
        }
        return false;
    }
}
