package io.github.varunj.sangoshthi_ivr.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.models.ShowModel;

public class ShowListRecyclerViewAdapter extends RecyclerView.Adapter<ShowListRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = ShowListRecyclerViewAdapter.class.getSimpleName();

    private List<ShowModel> showModelList;
    private Context context;

    public ShowListRecyclerViewAdapter(Context context, List<ShowModel> showList) {
        this.context = context;
        this.showModelList = showList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_item_row, parent, false);
        return new ShowListRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvListenerNumber.setText(showModelList.get(position).getShow_id());
    }

    @Override
    public int getItemCount() {
        return showModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        //        CardView cvListenerItemRow;
        TextView tvListenerNumber;
//        ImageButton ivMuteUnmute;
//        ImageButton ivQuestion;
//        ImageView ivReconnection;
//        Chronometer chronometerListenerItem;

        MyViewHolder(View itemView) {
            super(itemView);
//            cvListenerItemRow = (CardView) itemView.findViewById(R.id.cv_listener_item_row);
            tvListenerNumber = (TextView) itemView.findViewById(R.id.tv_show_id);
//            ivMuteUnmute = (ImageButton) itemView.findViewById(R.id.iv_mute_unmute);
//            ivQuestion = (ImageButton) itemView.findViewById(R.id.iv_question);
//            ivReconnection = (ImageView) itemView.findViewById(R.id.iv_reconnection);
//            chronometerListenerItem = (Chronometer) itemView.findViewById(R.id.chronometer_listener_item);
        }
    }
}
