package bse202.sda.healary.editalarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import bse202.sda.healary.R;
import bse202.sda.healary.data.MedicineAlarm;
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
    @BindView(R.id.fragment_editalarm_take)
    Button take;

    private EditAlarmViewModel editAlarmViewModel;
    private MedicineAlarm alarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editAlarmViewModel = ViewModelProviders.of(this).get(EditAlarmViewModel.class);
        long alarmId = requireArguments().getInt("alarmId");

        editAlarmViewModel.getById(alarmId).observe(this, alarms -> {
            if (alarms != null) {
                alarm = alarms.get(0);
                initView();
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

        take.setOnClickListener(v -> {
                    alarm.setCount(alarm.getCount() - alarm.getDosage());
                    editAlarmViewModel.edit(alarm);
                }
        );

        return view;
    }

    private void initView() {
        title.setText(alarm.getTitle());
        description.setText(alarm.getDescription());

        minCount.setText(String.valueOf(alarm.getMinCount()));
        count.setText(String.valueOf(alarm.getCount()));
        dosage.setText(String.valueOf(alarm.getDosage()));
    }

    private void editAlarm() {
        alarm.setCount(parseInt(count));
        alarm.setTitle(title.getText().toString());
        alarm.setMinCount(parseInt(minCount));
        alarm.setDosage(parseInt(dosage));
        alarm.setDescription(description.getText().toString());

        editAlarmViewModel.edit(alarm);
    }

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
