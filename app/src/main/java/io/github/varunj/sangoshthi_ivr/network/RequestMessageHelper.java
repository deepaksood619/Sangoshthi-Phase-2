/*
 * Copyright (c) 2017. Created by Deepak Sood
 */

package io.github.varunj.sangoshthi_ivr.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.github.varunj.sangoshthi_ivr.models.TutorialListenModel;
import io.github.varunj.sangoshthi_ivr.utilities.SharedPreferenceManager;


public class RequestMessageHelper {

    private static RequestMessageHelper instance;

    private RequestMessageHelper() {

    }

    public static synchronized RequestMessageHelper getInstance() {
        if (instance == null) {
            instance = new RequestMessageHelper();
        }
        return instance;
    }

    /* All the JSON Packets */

    /**
     * App install
     * <p>
     * Request - {"objective" : "app_install_notify", "broadcaster" : “9716517818”, “timestamp” : “2017-06-15 18:00:00” }
     * Response - {"objective" : "ack", "info" : “2” } // cohort id
     * Response - {"objective" : "ack", "info" : “-1” } // in case fetching of cohort id failed
     *
     * @param broadcaster
     */
    public void appInstallNotify(String broadcaster) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "app_install_notify");
            jsonObject.put("broadcaster", broadcaster);
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Upcoming Show Data (Host Show)
     * <p>
     * Request - {"objective" : "get_upcoming_show", "broadcaster" : "9716517818", "cohort_id" : "3", "timestamp" : "erfs" }
     * Response - {"objective": "upcoming_show_data", "show_id": "show_3", "time_of_airing": "2017-06-15 18:00:00", "topic": "play and communication"}'
     */
    public void getUpcomingShow() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "get_upcoming_show");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get upcoming show data list
     * <p>
     * Request - {"objective":"get_upcoming_show_list","broadcaster":"7011030818","cohort_id":"15","timestamp":"2019-03-07 12:13:56"}
     * Response - {"objective":"upcoming_show_list_data","show_34":{"topic":"Women's Health","show_id":"show_34","time_of_airing":"2019-03-04 19:15:00","local_name":"महिलाओं का स्वास्थ"},"show_35":{"topic":"Pediatrician","show_id":"show_35","time_of_airing":"2019-03-04 19:30:00","local_name":"बच्चों का चिकित्सक"},"show_32":{"topic":"Pregnancy","show_id":"show_32","time_of_airing":"2019-03-05 14:30:00","local_name":"गर्भावस्था"},"show_33":{"topic":"Menstruation","show_id":"show_33","time_of_airing":"2019-03-05 19:00:00","local_name":"माहवारी"}}
     */
    public void getUpcomingShowList() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "get_upcoming_show_list");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Request - {"objective":"get_final_feedback_for_show","broadcaster":"7011030818","cohort_id":"15","timestamp":"2019-03-07 22:12:08","update":"yes","show_id":"show_32"}
     * Response - {"objective":"get_final_feedback_for_show_ack","show_id":"show_32"}
     */
    public void getFinalFeedbackForShow(String update_val) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "get_final_feedback_for_show");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            jsonObject.put("update", update_val);
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Request - {"objective":"call_quality_update","broadcaster":"8368861819","cohort_id":"[63]","show_id":"show_136","conference_name":"EMPTY","update":"no"}
     * Response - {"objective":"configuration_data","cohort_id":[63],"cohort_size":"1"}
     */
    public void getCallQualityUpdate(String update_val) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "call_quality_update");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("update", update_val);
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * {"objective":"start_show",
     * "broadcaster":"8368861819",
     * "cohort_id":"7",
     * "show_id":"show_61",
     * "timestamp":"2018-06-14 22:54:01"
     * }
     */
    public void startShow() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "start_show");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * {"objective":"dial_listeners",
     * "broadcaster":"8368861819",
     * "cohort_id":"7",
     * "show_id":"show_61",
     * "conference_name":"show_61_2018_06_14_22_53_58",
     * "timestamp":"2018-06-14 22:54:11"
     * }
     */
    public void dialListeners() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "dial_listeners");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * {"objective":"mute",
     * "broadcaster":"8368861819",
     * "cohort_id":"7",
     * "show_id":"show_61",
     * "conference_name":"show_61_2018_06_14_22_53_58",
     * "listener_phoneno":"9716517818",
     * "turn":3,"timestamp":"2018-06-14 22:55:56"
     * }
     *
     * @param listener_phoneno
     */
    public void mute(String listener_phoneno, int turn) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "mute");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("listener_phoneno", listener_phoneno);
            jsonObject.put("turn", turn);
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Unmute
     *
     * @param listener_phoneno
     */
    public void unmute(String listener_phoneno, int turn) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "unmute");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("listener_phoneno", listener_phoneno);
            jsonObject.put("turn", turn);
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * objective - flush_callers
     */
    public void flushCallers() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "flush_callers");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * {"objective":"pause_play_content",
     * "media_order":"1",
     * "broadcaster":"8368861819",
     * "cohort_id":"7",
     * "show_id":"show_61",
     * "conference_name":"show_61_2018_06_14_22_53_58",
     * "timestamp":"2018-06-14 22:56:06"
     * }
     */
    public void pausePlayShowContent(int mediaOrder) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "pause_play_content");
            jsonObject.put("media_order", Integer.toString(mediaOrder));
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
       {
         "objective":"play_show_media",
         "media_order":"3",
         "type":"answer","broadcaster":"8368861819",
         "cohort_id":"9",
         "show_id":"show_20",
         "conference_name":"show_20_2018_04_16_11_02_30",
         "timestamp":"2018-04-16 11:09:56"
       }
    */
    public void playShowMedia(int mediaOrder, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "play_show_media");
            jsonObject.put("media_order", Integer.toString(mediaOrder));
            jsonObject.put("type", type);
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        {"objective":"show_playback_metadata","broadcaster":"9425592627","cohort_id":"7","show_id":"show_23","conference_name":"show_23_2018_05_28_13_52_31","timestamp":"2018-05-28 13:52:50"}
        {"objective":"show_playback_metadata_response","media":[{"duration":135433,"type":"content","order":"1","name":"स्तनपान (सैंपल शो)"},{"duration":4899,"type":"QA","order":"2","name":"QA1"},{"duration":3669,"type":"QA","order":"3","name":"QA2"}]}
     */
    public void showPlaybackMetadata() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "show_playback_metadata");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showEndShow() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "end_show_call");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * getNotifications in json format
     * Request format - {"objective":"get_notifications","broadcaster":"9425592627","cohort_id":"2","show_id":"show_2","conference_name":"show_2_2017_06_17_16_30_02","timestamp":"18-Jun-2017 7:20:06 PM"}
     */
    public void getNotifications() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "get_notifications");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getShowIdForGallery() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "get_show_id_for_gallery");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateShowStatus() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "update_show_status");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("show_id", SharedPreferenceManager.getInstance().getShowId());
            jsonObject.put("conference_name", SharedPreferenceManager.getInstance().getConferenceName());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void broadcasterContentListenEvent(TutorialListenModel tutorialListenModel, int packetId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objective", "broadcaster_content_listen_event");
            jsonObject.put("broadcaster", SharedPreferenceManager.getInstance().getBroadcaster());
            jsonObject.put("cohort_id", SharedPreferenceManager.getInstance().getCohortId());
            jsonObject.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            jsonObject.put("content_id", tutorialListenModel.getShow_id());
            jsonObject.put("show_status", tutorialListenModel.getShow_status());
            jsonObject.put("listen_timestamp", tutorialListenModel.getListen_timestamp());
            jsonObject.put("topic", tutorialListenModel.getTopic());
            jsonObject.put("packet_id", String.valueOf(packetId));
            int totalSeconds = tutorialListenModel.getCountSeconds();
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            int seconds = totalSeconds % 60;
            jsonObject.put("duration", String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds));
            AMQPPublish.getInstance().publishMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
