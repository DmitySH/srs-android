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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.List;
import java.util.Objects;

import bse202.sda.healary.R;
import bse202.sda.healary.alarmslist.AlarmsListViewModel;
import bse202.sda.healary.data.MedicineAlarm;
import bse202.sda.healary.data.MedicineAlarmDao;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditAlarmFragment extends Fragment {
    @BindView(R.id.fragment_editalarm_title)
    EditText title;
    @BindView(R.id.fragment_editalarm_count)
    EditText count;
    @BindView(R.id.fragment_editalarm_min_count)
    EditText minCount;
    @BindView(R.id.fragment_editalarm_dosage)
    EditText dosage;
    @BindView(R.id.fragment_editalarm_desc)
    EditText description;

    @BindView(R.id.fragment_editalarm_editAlarm)
    Button editAlarm;

    private long alarmId;
    private EditAlarmViewModel editAlarmViewModel;
    private MedicineAlarm alarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editAlarmViewModel = ViewModelProviders.of(this).get(EditAlarmViewModel.class);
        alarmId = requireArguments().getInt("alarmId");

        editAlarmViewModel.getById(alarmId).observe(this, alarms -> {
            if (alarms != null) {
                alarm = alarms.get(0);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editalarm, container, false);

        ButterKnife.bind(this, view);

        editAlarm.setOnClickListener(v -> {
            editAlarm();
            Navigation.findNavController(v).navigate(R.id.action_editAlarmFragment_to_alarmsListFragment);
        });

        return view;
    }

    private void editAlarm() {
        alarm.setCount(123123);
        editAlarmViewModel.edit(alarm);


    }

//    private void scheduleAlarm() {
//        int countParsed, minCountParsed, dosageParsed;
//        countParsed = parseInt(count);
//        minCountParsed = parseInt(minCount);
//        dosageParsed = parseInt(dosage);
//
//        MedicineAlarm alarm = new MedicineAlarm(
//                TimePickerUtil.getTimePickerHour(timePicker),
//                TimePickerUtil.getTimePickerMinute(timePicker),
//                title.getText().toString(),
//                System.currentTimeMillis(),
//                true,
//                true,
//                mon.isChecked(),
//                tue.isChecked(),
//                wed.isChecked(),
//                thu.isChecked(),
//                fri.isChecked(),
//                sat.isChecked(),
//                sun.isChecked(),
//                countParsed,
//                description.getText().toString(),
//                minCountParsed,
//                dosageParsed
//
//        );
//
//        createAlarmViewModel.insert(alarm);
//
//        alarm.schedule(requireContext());
//    }

    private int parseInt(EditText edit) {
        int countParsed;
        try {
            countParsed = Integer.parseInt(edit.getText().toString());
            if (countParsed < 0) {
                countParsed = 0;
            }
            if (countParsed > 1_000_000) {
                countParsed = 1_000_000;
            }
        } catch (NumberFormatException ex) {
            countParsed = 0;
        }
        return countParsed;
    }
}
