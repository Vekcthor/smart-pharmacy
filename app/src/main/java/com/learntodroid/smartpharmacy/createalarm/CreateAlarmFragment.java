package com.learntodroid.smartpharmacy.createalarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.learntodroid.smartpharmacy.R;
import com.learntodroid.smartpharmacy.data.Alarm;

import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAlarmFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.fragment_createalarm_timePicker)
    TimePicker timePicker;
    @BindView(R.id.fragment_createalarm_title)
    EditText title;
    @BindView(R.id.fragment_createalarm_scheduleAlarm)
    Button scheduleAlarm;
    @BindView(R.id.fragment_createalarm_checkMon)
    CheckBox mon;
    @BindView(R.id.fragment_createalarm_checkTue)
    CheckBox tue;
    @BindView(R.id.fragment_createalarm_checkWed)
    CheckBox wed;
    @BindView(R.id.fragment_createalarm_checkThu)
    CheckBox thu;
    @BindView(R.id.fragment_createalarm_checkFri)
    CheckBox fri;
    @BindView(R.id.fragment_createalarm_checkSat)
    CheckBox sat;
    @BindView(R.id.fragment_createalarm_checkSun)
    CheckBox sun;
    @BindView(R.id.fragment_createalarm_recurring_options)
    LinearLayout recurringOptions;
    @BindView(R.id.spinnerDailyFrequency)
    Spinner spinnerDailyFrequency;
    @BindView(R.id.editTextDose)
    EditText editTextDose;
    @BindView(R.id.editTextInventory)
    EditText editTextInventory;

    private CreateAlarmViewModel createAlarmViewModel;
    private int frequency = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createalarm, container, false);

        ButterKnife.bind(this, view);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.daily_frequency, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDailyFrequency.setAdapter(adapter);
        spinnerDailyFrequency.setOnItemSelectedListener(this);

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();
                Navigation.findNavController(v).navigate(R.id.action_createAlarmFragment_to_alarmsListFragment);
            }
        });

        return view;
    }

    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        int dose = 0;
        String doseText = editTextDose.getText().toString();

        if (!doseText.equals("") && !doseText.isEmpty() ){
            dose = Integer.parseInt(doseText);
        }

        int inventory = 0;
        String inventoryText = editTextInventory.getText().toString();
        if (!inventoryText.equals("") && !inventoryText.isEmpty() ){
            inventory = Integer.parseInt(inventoryText);
        }

        Alarm alarm = new Alarm(
                alarmId,
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),
                title.getText().toString(),
                System.currentTimeMillis(),
                true,
                true,
                mon.isChecked(),
                tue.isChecked(),
                wed.isChecked(),
                thu.isChecked(),
                fri.isChecked(),
                sat.isChecked(),
                sun.isChecked(),
                inventory,
                frequency,
                dose
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(Objects.requireNonNull(getContext()));
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (pos) {
            case 0:
                frequency = 1;
                break;
            case 1:
                frequency = 12;
                break;
            case 2:
                frequency = 6;
                break;
            case 3:
                frequency = 4;
                break;
            case 4:
                frequency = 3;
                break;
            case 5:
                frequency = 2;
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        frequency = 1;
    }
}
