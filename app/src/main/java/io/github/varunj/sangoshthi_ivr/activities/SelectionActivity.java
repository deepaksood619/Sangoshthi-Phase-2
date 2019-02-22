package io.github.varunj.sangoshthi_ivr.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.callhandler.CallReceiver;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private Button hostshow1;
    private Button hostshow2;

    private CallReceiver callReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        hostshow1= (Button) findViewById(R.id.btn_host_show_1);
        hostshow1.setOnClickListener(this);

        hostshow2 = (Button) findViewById(R.id.btn_host_show_2);
        hostshow2.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_host_show_1:
                Intent intentHostShow1 = new Intent(this, HostShowActivity.class);
                startActivity(intentHostShow1);
                break;

            case R.id.btn_host_show_2:
                Intent intentNotifications1 = new Intent(this, NotificationsActivity.class);
                startActivity(intentNotifications1);
                break;

        }
    }
}
