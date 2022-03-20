package bse202.sda.healary.createalarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import bse202.sda.healary.data.MedicineAlarm;
import bse202.sda.healary.data.MedicineAlarmRepository;


public class CreateAlarmViewModel extends AndroidViewModel {
    private MedicineAlarmRepository alarmRepository;

    public CreateAlarmViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new MedicineAlarmRepository(application);
    }

    public void insert(MedicineAlarm alarm) {
        alarmRepository.insert(alarm);
    }
}
