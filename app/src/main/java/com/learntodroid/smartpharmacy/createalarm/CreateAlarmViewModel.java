package com.learntodroid.smartpharmacy.createalarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.learntodroid.smartpharmacy.data.Alarm;
import com.learntodroid.smartpharmacy.data.AlarmRepository;

public class CreateAlarmViewModel extends AndroidViewModel {
    private final AlarmRepository alarmRepository;

    public CreateAlarmViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
    }

    public void insert(Alarm alarm) {
        alarmRepository.insert(alarm);
    }
}
