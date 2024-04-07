package com.learntodroid.smartpharmacy.alarmslist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.smartpharmacy.R;
import com.learntodroid.smartpharmacy.createalarm.CreateAlarmFragment;
import com.learntodroid.smartpharmacy.data.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarms;
    private final OnToggleAlarmListener listener;
    int pos;

    public AlarmRecyclerViewAdapter(OnToggleAlarmListener listener) {
        this.alarms = new ArrayList<Alarm>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        AlarmViewHolder alarmViewHolder = new AlarmViewHolder(itemView, listener,
                new AlarmViewHolder.IMyViewHolderClicks() {
                    public void onCustomClick(View caller, int test) {
                        /*Intent intent = new Intent(context, CreateAlarmFragment.class);
                        intent.putExtra("teste", "teste");
                        context.startActivity(intent);
                        Log.d("VEGETABLES", String.valueOf(test));*/
                    }
                });

        alarmViewHolder.onClick(itemView);

        return alarmViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmStarted.setOnCheckedChangeListener(null);
    }
}

