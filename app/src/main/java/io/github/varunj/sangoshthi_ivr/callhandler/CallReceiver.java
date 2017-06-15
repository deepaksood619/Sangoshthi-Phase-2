package io.github.varunj.sangoshthi_ivr.callhandler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

import io.github.varunj.sangoshthi_ivr.activities.CallActivity;
import io.github.varunj.sangoshthi_ivr.utilities.ConstantUtil;
import io.github.varunj.sangoshthi_ivr.utilities.SharedPreferenceManager;

/**
 * Created by deepaksood619 on 27/6/16.
 */
public class CallReceiver extends PhoneCallReceiver {

    private static final String TAG = CallReceiver.class.getSimpleName();

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        super.onIncomingCallStarted(ctx, number, start);
        Log.d(TAG, "call incoming: " + number + " date start: " + start);

        if(CallActivity.dismissThread != null) {
            if(CallActivity.dismissThread.isAlive()) {
                CallActivity.dismissThread.interrupt();
            }
        }
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onIncomingCallEnded(ctx, number, start, end);
        Log.d(TAG, "call incoming ended: " + number + " date start: " + start + "date end: " + end);
        if(number.contains(ConstantUtil.SERVER_NUM)) {

            if(CallActivity.progressDialog != null) {

                Log.v(TAG, "Call disconnected in CallActivity");

                if(CallActivity.progressDialog.isShowing()) {
                    CallActivity.progressDialog.dismiss();
                }

            }

        }

    }

    @Override
    public void onCallStateChanged(Context context, int state, String number) {
        super.onCallStateChanged(context, state, number);

        if(state == 0 && number != null) {

            if(number.contains(ConstantUtil.SERVER_NUM) && SharedPreferenceManager.getInstance().isCallReceived()) {
                Log.d(TAG, "call disconnected in other activity");
                SharedPreferenceManager.getInstance().setCallReceived(false);
                if(CallActivity.progressDialog != null) {
                        if(CallActivity.progressDialog.isShowing()) {
                            CallActivity.progressDialog.dismiss();
                        }
                }
            }
        }

        if(state == 1 && number != null) {
            if(number.contains(ConstantUtil.SERVER_NUM)) {

                SharedPreferenceManager.getInstance().setCallReceived(true);

            }
        }

        Log.d(TAG, "state changed: " + state + " number: " + number);
        if(state == 2 && number != null) {
            if(number.contains(ConstantUtil.SERVER_NUM)) {

                if(CallActivity.progressDialog != null) {
                    if(CallActivity.progressDialog.isShowing()) {
                        CallActivity.progressDialog.dismiss();
                    }
                }
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        super.onOutgoingCallStarted(ctx, number, start);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onOutgoingCallEnded(ctx, number, start, end);
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        super.onMissedCall(ctx, number, start);
    }
}