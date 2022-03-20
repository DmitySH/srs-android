package bse202.sda.healary.service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;


import java.util.List;

import bse202.sda.healary.data.MedicineAlarm;
import bse202.sda.healary.data.MedicineAlarmRepository;

public class RescheduleAlarmsService extends LifecycleService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        MedicineAlarmRepository alarmRepository = new MedicineAlarmRepository(getApplication());

        alarmRepository.getAlarmsLiveData().observe(this, alarms -> {
            for (MedicineAlarm a : alarms) {
                if (a.isStarted()) {
                    a.schedule(getApplicationContext());
                }
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
