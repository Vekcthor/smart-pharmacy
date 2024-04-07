package com.learntodroid.smartpharmacy.alarmslist;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.smartpharmacy.R;
import com.learntodroid.smartpharmacy.data.Alarm;

public class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView alarmTime;
    private final ImageView alarmRecurring;
    private final TextView alarmRecurringDays;
    private final TextView alarmTitle;

    SwitchCompat alarmStarted;

    public IMyViewHolderClicks mListener;

    private final OnToggleAlarmListener listener;


    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener, IMyViewHolderClicks clicksListener) {
        super(itemView);

        mListener = clicksListener;

        alarmTime = itemView.findViewById(R.id.item_alarm_time);
        alarmTime.setOnClickListener(this);
        alarmStarted = itemView.findViewById(R.id.item_alarm_started);
        alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring);
        alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
        alarmTitle = itemView.findViewById(R.id.item_alarm_title);

        this.listener = listener;
    }

    public void bind(Alarm alarm) {
        String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
        alarmStarted.setChecked(alarm.isStarted());

        if (alarm.isRecurring()) {
            alarmRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
            alarmRecurringDays.setText(alarm.getRecurringDaysText());
        } else {
            alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
            alarmRecurringDays.setText("Once Off");
        }

        if (alarm.getTitle().length() != 0) {
            alarmTitle.setText(String.format("%s | Dose:%d", alarm.getTitle(), alarm.getDose()));
        } else {
            alarmTitle.setText(String.format("%s | Dose:%d", "Alarm", alarm.getDose()));
        }

        alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onToggle(alarm);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        mListener.onCustomClick(v, pos);
    }

    public static interface IMyViewHolderClicks {
        public void onCustomClick(View caller, int test);
    }
}
