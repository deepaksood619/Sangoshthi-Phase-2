package io.github.varunj.sangoshthi_ivr.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.adapters.ShowListRecyclerViewAdapter;
import io.github.varunj.sangoshthi_ivr.models.ShowModel;
import io.github.varunj.sangoshthi_ivr.network.RequestMessageHelper;
import io.github.varunj.sangoshthi_ivr.network.ResponseMessageHelper;
import io.github.varunj.sangoshthi_ivr.utilities.SharedPreferenceManager;

public class SelectionActivity extends AppCompatActivity {

    private static final String TAG = SelectionActivity.class.getSimpleName();

    ArrayList<ShowModel> showModelArrayList;
    private ShowListRecyclerViewAdapter mAdapter;
    private RecyclerView rvListenersContent;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        showModelArrayList = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvListenersContent = (RecyclerView) findViewById(R.id.rv_show_content);

        mAdapter = new ShowListRecyclerViewAdapter(this, showModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvListenersContent.setLayoutManager(layoutManager);
        rvListenersContent.setItemAnimator(new DefaultItemAnimator());
        rvListenersContent.setAdapter(mAdapter);

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Log.d(TAG, "Message received: " + msg.getData().getString("msg"));
                    JSONObject jsonObject = new JSONObject(msg.getData().getString("msg"));

                    if (jsonObject.getString("objective").equals("upcoming_show_list_data")) {
                        Iterator<?> keys = jsonObject.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();

                            if (!key.equalsIgnoreCase("objective") && !key.equals("none") && !key.equals("")) {
                                Log.d(TAG, "show details: " + jsonObject.get(key));
                                JSONObject showObject = new JSONObject(jsonObject.get(key).toString());

                                ShowModel newShow = new ShowModel(showObject.get("topic").toString(), showObject.get("show_id").toString(), showObject.get("time_of_airing").toString(), showObject.get("local_name").toString());
                                showModelArrayList.add(newShow);
                            }
                        }
                    }

                    SharedPreferenceManager.getInstance().setShowListData(showModelArrayList);

                    mAdapter.notifyDataSetChanged();

//                if (!jsonObject.getString("show_id").equals("none")) {
//                    if (jsonObject.getString("local_name").equals("none")) {
//                        // show topic in english if local_name is none
//                        Name.setText(jsonObject.getString("topic"));
//                    } else {
//                        Name.setText(jsonObject.getString("local_name"));
//                    }
//
//                } else {
//                    Log.d(TAG, "no show present");
//                    Name.setText(getString(R.string.placeholder_tv_show_topic));
//                    LoadingUtil.getInstance().hideLoading();
//                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ResponseMessageHelper.getInstance().subscribeToResponse(incomingMessageHandler);

        RequestMessageHelper.getInstance().getUpcomingShowList();
    }
//
//    class CustomAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return showModelArrayList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);
//
//
//            TextView Name = (TextView) convertView.findViewById(R.id.Name);
//            Button Feedback = (Button) convertView.findViewById(R.id.feedback);
//            Button Feedback2 = (Button) convertView.findViewById(R.id.Feedback2);
//
//            Feedback2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getApplicationContext(), HostShowActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//
//            if (position == 0) {
//                Feedback.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent intentHostShow1 = new Intent(getApplicationContext(), HostShowActivity.class);
//                        startActivity(intentHostShow1);
//                    }
//                });
//                if (position == 0) {
//                    Feedback2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SelectionActivity.this);
//                            builder.setTitle(R.string.dialog_box_end_show_title)
//                                    .setMessage(R.string.dialog_box_end_show_message)
//                                    .setCancelable(false);
//
//                            builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Log.d(TAG, "End show ok");
//                                    LoadingUtil.getInstance().showLoading(getString(R.string.progress_dialog_please_wait), SelectionActivity.this);
//                                    RequestMessageHelper.getInstance().getFinalFeedbackForShow();
//                                    SharedPreferenceManager.getInstance().setShowRunning(false);
//                                    //SharedPreferenceManager.getInstance().setShowUpdateStatus(true);
//
//
//                                }
//                            });
//
//                            builder.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.cancel();
//                                }
//                            });
//
//                            android.support.v7.app.AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//                        }
//                    });
//                }
//                Name.setText(names[position]);
//            }
//            return convertView;
//        }
//    }
}
