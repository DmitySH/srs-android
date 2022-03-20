package bse202.sda.healary.createalarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;


import java.util.Objects;
import java.util.Random;

import bse202.sda.healary.R;
import bse202.sda.healary.data.MedicineAlarm;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAlarmFragment extends Fragment {
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

    @BindView(R.id.edit_count)
    EditText count;
    @BindView(R.id.edit_desc)
    EditText description;


    private CreateAlarmViewModel createAlarmViewModel;

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

        scheduleAlarm.setOnClickListener(v -> {
            scheduleAlarm();
            Navigation.findNavController(v).navigate(R.id.action_createAlarmFragment_to_alarmsListFragment);
        });

        return view;
    }

    private void scheduleAlarm() {
        int countParsed;
        try {
            countParsed = Integer.parseInt(count.getText().toString());
            if (countParsed < 0) {
                countParsed = 0;
            }
            if (countParsed > 1_000_000) {
                countParsed = 1_000_000;
            }
        } catch (NumberFormatException ex) {
            countParsed = 0;
        }

        MedicineAlarm alarm = new MedicineAlarm(
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
                countParsed,
                description.getText().toString()
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(requireContext());
    }
}
