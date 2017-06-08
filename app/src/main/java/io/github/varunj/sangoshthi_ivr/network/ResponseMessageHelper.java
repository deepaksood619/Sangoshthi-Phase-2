package io.github.varunj.sangoshthi_ivr.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.varunj.sangoshthi_ivr.utilities.SharedPreferenceManager;

/**
 * Created by Deepak on 07-06-2017.
 */

public class ResponseMessageHelper {

    private static final String TAG = ResponseMessageHelper.class.getSimpleName();

    private static final String OBJECTIVE = "objective";

    private static ResponseMessageHelper instance;
    private Handler handler;

    private ResponseMessageHelper() {}

    public static synchronized ResponseMessageHelper getInstance() {
        if(instance == null)
            instance = new ResponseMessageHelper();
        return instance;
    }

    public void subscribeToResponse(Handler handler) {
        this.handler = handler;
    }


    public void handle(JSONObject message) {
        try {
            switch (message.getString(OBJECTIVE)) {
                case "configuration_data":
                    handleConfigurationData(message);
                    break;

                case "upcoming_show_data":
                    handleUpcomingShowData(message);
                    break;

                case "start_show_response":
                    handleStartShowResponse(message);
                    break;

                case "conf_member_status":
                    handleConfMemberStatus(message);
                    break;

                default:
                    Log.e(TAG, "Objective not matched " + message.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Response - {"objective":"configuration_data","cohort_id":"2"}
     *
     * @param message
     * @throws JSONException
     */
    private void handleConfigurationData(JSONObject message) throws JSONException {
        SharedPreferenceManager.getInstance().setCohortId(message.getString("cohort_id"));
    }

    /**
     * Response - {"objective":"upcoming_show_data","topic":"play","show_id":"show_3","time_of_airing":"2017-06-20 14:20:59"}
     *
     * @param message
     * @throws JSONException
     */
    private void handleUpcomingShowData(JSONObject message) throws JSONException {
        SharedPreferenceManager.getInstance().setShowId(message.getString("show_id"));
        sendCallbackToActivity(message);
    }

    private void handleStartShowResponse(JSONObject message) throws JSONException {
        if(!message.getString("info").equals("FAIL")) {
            SharedPreferenceManager.getInstance().setConferenceName(message.getString("info"));
        }
    }

    private void handleConfMemberStatus(JSONObject message) throws JSONException {
        if(message.getString("task").equals("online") || message.getString("task").equals("offline")) {
            sendCallbackToActivity(message);
        }
    }

    private void sendCallbackToActivity(JSONObject message) {
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("msg", message.toString());
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}