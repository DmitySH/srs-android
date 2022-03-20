package bse202.sda.healary.createalarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import bse202.sda.healary.data.MedicineAlarm;
import bse202.sda.healary.data.MedicineAlarmRepository;

public class EditAlarmViewModel extends AndroidViewModel {
    private MedicineAlarmRepository alarmRepository;

    public EditAlarmViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new MedicineAlarmRepository(application);
    }

    public void edit(MedicineAlarm alarm) {
        alarmRepository.edit(alarm);
    }
    public LiveData<List<MedicineAlarm>> getById(long id) {
        return alarmRepository.getById(id);
    }

}
