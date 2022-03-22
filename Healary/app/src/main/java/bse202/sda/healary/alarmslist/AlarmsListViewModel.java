package bse202.sda.healary.alarmslist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import bse202.sda.healary.data.MedicineAlarm;
import bse202.sda.healary.data.MedicineAlarmRepository;

public class AlarmsListViewModel extends AndroidViewModel {
    private final MedicineAlarmRepository alarmRepository;
    private final LiveData<List<MedicineAlarm>> alarmsLiveData;


    public AlarmsListViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new MedicineAlarmRepository(application);
        alarmRepository.deleteCancelled(new Object());
        alarmsLiveData = alarmRepository.getAlarmsLiveData();
    }

    public void update(MedicineAlarm alarm) {
        alarmRepository.update(alarm);
    }

    public LiveData<List<MedicineAlarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
