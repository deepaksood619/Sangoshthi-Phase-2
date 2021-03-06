/*
 * Copyright (c) 2017. Created by Deepak Sood
 */

package io.github.varunj.sangoshthi_ivr.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.models.NotificationModel;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = NotificationsRecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<NotificationModel> notificationModelList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotification;
        TextView tvNotificationDate;
        CardView cvNotification;

        MyViewHolder(View itemView) {
            super(itemView);
            tvNotification = itemView.findViewById(R.id.tv_notification);
            tvNotificationDate = itemView.findViewById(R.id.tv_notification_date);
            cvNotification = itemView.findViewById(R.id.cv_notification);
        }
    }

    public NotificationsRecyclerViewAdapter(Context context, List<NotificationModel> notificationModelList) {
        this.context = context;
        this.notificationModelList = notificationModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(notificationModelList.get(position).getRead_status() == 0) {
            holder.cvNotification.setBackgroundColor(ContextCompat.getColor(context, R.color.cv_notification_unread));
        } else {
            holder.cvNotification.setBackgroundColor(ContextCompat.getColor(context, R.color.cv_notification_read));
        }
        holder.tvNotification.setText(notificationModelList.get(position).getBody());
        holder.tvNotificationDate.setText(notificationModelList.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }
}
