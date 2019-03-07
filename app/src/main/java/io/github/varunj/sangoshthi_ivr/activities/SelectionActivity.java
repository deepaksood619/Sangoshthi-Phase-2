package io.github.varunj.sangoshthi_ivr.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.adapters.ShowListRecyclerViewAdapter;
import io.github.varunj.sangoshthi_ivr.models.ShowModel;
import io.github.varunj.sangoshthi_ivr.network.RequestMessageHelper;
import io.github.varunj.sangoshthi_ivr.network.ResponseMessageHelper;
import io.github.varunj.sangoshthi_ivr.utilities.LoadingUtil;

public class SelectionActivity extends AppCompatActivity {

    private static final String TAG = SelectionActivity.class.getSimpleName();

    ArrayList<ShowModel> showModelArrayList;
    private ShowListRecyclerViewAdapter mAdapter;
    private RecyclerView rvListenersContent;

    private TextView tvNoShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        rvListenersContent = findViewById(R.id.rv_show_content);
        tvNoShow = findViewById(R.id.tv_no_show);

        showModelArrayList = new ArrayList<>();
        mAdapter = new ShowListRecyclerViewAdapter(this, showModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvListenersContent.setLayoutManager(layoutManager);
        rvListenersContent.setItemAnimator(new DefaultItemAnimator());
        rvListenersContent.setAdapter(mAdapter);

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    LoadingUtil.getInstance().hideLoading();

                    Log.d(TAG, "Message received: " + msg.getData().getString("msg"));
                    JSONObject jsonObject = new JSONObject(msg.getData().getString("msg"));

                    switch (jsonObject.getString("objective")) {
                        case "upcoming_show_list_data":
                            handleUpcomingShowListData(jsonObject);
                            break;

                        case "get_final_feedback_for_show_ack":
                            handleFeedbackAck(jsonObject);
                            break;

                        default:
                            Log.e(TAG, "objective not matched in SelectionActivity: " + jsonObject.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ResponseMessageHelper.getInstance().subscribeToResponse(incomingMessageHandler);

        LoadingUtil.getInstance().showLoading(getString(R.string.progress_dialog_please_wait), SelectionActivity.this);
        RequestMessageHelper.getInstance().getUpcomingShowList();
    }

    private void handleUpcomingShowListData(JSONObject jsonObject) throws JSONException {
        showModelArrayList.clear();

        Iterator<?> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();

            if (!key.equalsIgnoreCase("objective") && !key.equals("none") && !key.equals("")) {
                JSONObject showObject = new JSONObject(jsonObject.get(key).toString());

                ShowModel newShow = new ShowModel(showObject.get("topic").toString(), showObject.get("show_id").toString(), showObject.get("time_of_airing").toString(), showObject.get("local_name").toString());
                showModelArrayList.add(newShow);
            }
        }

        if (showModelArrayList.size() > 0) {
            tvNoShow.setVisibility(View.INVISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void handleFeedbackAck(JSONObject jsonObject) throws JSONException {
        jsonObject.getString("show_id");

        for (ShowModel showModel : showModelArrayList) {
            if (showModel.getShow_id().equals(jsonObject.getString("show_id"))) {
                showModelArrayList.remove(showModel);
                break;
            }
        }

        mAdapter.notifyDataSetChanged();
    }
}