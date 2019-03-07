package io.github.varunj.sangoshthi_ivr.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import io.github.varunj.sangoshthi_ivr.R;
import io.github.varunj.sangoshthi_ivr.activities.HostShowActivity;
import io.github.varunj.sangoshthi_ivr.models.ShowModel;
import io.github.varunj.sangoshthi_ivr.network.RequestMessageHelper;
import io.github.varunj.sangoshthi_ivr.utilities.LoadingUtil;

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
        holder.tvLocalizedShowName.setText(showModelList.get(position).getLocal_name());

        holder.btnStartShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HostShowActivity.class);
                context.startActivity(intent);
            }
        });

        holder.btnShowFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_box_feedback_show_status_title)
                        .setCancelable(false);

                builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoadingUtil.getInstance().showLoading(context.getString(R.string.progress_dialog_please_wait), (Activity) context);
                        RequestMessageHelper.getInstance().getFinalFeedbackForShow();
                    }
                });

                builder.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return showModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocalizedShowName;
        Button btnStartShow;
        Button btnShowFeedback;

        MyViewHolder(View itemView) {
            super(itemView);
            tvLocalizedShowName = itemView.findViewById(R.id.tv_localized_show_name);
            btnStartShow = itemView.findViewById(R.id.btn_start_show);
            btnShowFeedback = itemView.findViewById(R.id.btn_show_feedback);
        }
    }
}
