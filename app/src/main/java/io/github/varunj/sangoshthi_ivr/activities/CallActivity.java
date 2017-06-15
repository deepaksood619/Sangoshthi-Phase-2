package io.github.varunj.sangoshthi_ivr.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.network.RequestMessageHelper;
import io.github.varunj.sangoshthi_ivr.network.ResponseMessageHelper;
import io.github.varunj.sangoshthi_ivr.utilities.ConstantUtil;
import io.github.varunj.sangoshthi_ivr.utilities.SharedPreferenceManager;

public class CallActivity extends AppCompatActivity {

    private static final String TAG = CallActivity.class.getSimpleName();
    private Context context;

    private Button btnCall;

    public static ProgressDialog progressDialog;
    public static Thread dismissThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        this.context = this;

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Log.d(TAG, "Message received: " + msg.getData().getString("msg"));
                    JSONObject jsonObject = new JSONObject(msg.getData().getString("msg"));

                    if(jsonObject.getString("objective").equals("start_show_response")) {
                        switch (jsonObject.getString("info")) {
                            case "FAIL":
                                Toast.makeText(context, "calling failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;

                            default:
                                Log.d(TAG, "start_show_response" + jsonObject.getString("info"));
                        }
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "" + e);
                }
            }
        };
        ResponseMessageHelper.getInstance().subscribeToResponse(incomingMessageHandler);

        btnCall = (Button) findViewById(R.id.btn_call);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCall.getText().equals(getResources().getString(R.string.btn_call_broadcaster))) {
                    Toast.makeText(CallActivity.this, "calling broadcaster", Toast.LENGTH_SHORT).show();
                    RequestMessageHelper.getInstance().startShow();
                    progressDialog.show();

                } else if(btnCall.getText().equals(getResources().getString(R.string.btn_call_listeners))) {
                    RequestMessageHelper.getInstance().dialListeners();
                    Toast.makeText(CallActivity.this, "calling listeners", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ShowActivity.class);
                    startActivity(intent);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.string_calling_broadcaster));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);

        dismissThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ConstantUtil.TWENTY_SECOND_CLOCK);

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                } catch (InterruptedException e) {
                    Log.d(TAG, "thread stopped because incoming call received");
                    Log.e(TAG, "" + e);
                }
            }
        });
        dismissThread.start();

        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(SharedPreferenceManager.getInstance().isCallReceived()) {
                    // call received, change button to call listeners
                    btnCall.setText(getResources().getString(R.string.btn_call_listeners));
                    btnCall.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.drawable.call_listeners), null, null);
                }
            }
        });

    }
}