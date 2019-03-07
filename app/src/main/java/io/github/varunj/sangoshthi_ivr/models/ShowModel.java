package io.github.varunj.sangoshthi_ivr.models;

import android.support.annotation.NonNull;

public class ShowModel implements Comparable<ShowModel> {

    private static final String TAG = ShowModel.class.getSimpleName();

    private String topic;
    private String show_id;
    private String time_of_airing;
    private String local_name;

    public ShowModel(String topic, String show_id, String time_of_airing, String local_name) {
        this.topic = topic;
        this.show_id = show_id;
        this.time_of_airing = time_of_airing;
        this.local_name = local_name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getShow_id() {
        return show_id;
    }

    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    public String getTime_of_airing() {
        return time_of_airing;
    }

    public void setTime_of_airing(String time_of_airing) {
        this.time_of_airing = time_of_airing;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    @Override
    public String toString() {
        return "ShowModel{" +
                "topic='" + topic + '\'' +
                ", show_id='" + show_id + '\'' +
                ", time_of_airing='" + time_of_airing + '\'' +
                ", local_name='" + local_name + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull ShowModel showModel) {

        Integer a = Integer.parseInt(this.getShow_id().replaceAll("[^0-9]", ""));
        Integer b = Integer.parseInt(showModel.getShow_id().replaceAll("[^0-9]", ""));

        return a.compareTo(b);

        // TODO: debug this compareTo is returning 1 for all case
        // This is sorting using the time_of_airing field
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        Calendar a = Calendar.getInstance();
//        Calendar b = Calendar.getInstance();
//        try {
//            a.setTime(simpleDateFormat.parse(this.getTime_of_airing()));
//            b.setTime(simpleDateFormat.parse(showModel.getTime_of_airing()));
//
//            Log.d(TAG, "values a - " + a + " b - " + b);
//
//            Log.d(TAG, "comapre - " + a.compareTo(b));
//
//            return b.compareTo(a);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return 0;
//        }
    }
}
