package healary.service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import java.util.List;

import healary.data.Alarm;
import healary.data.AlarmRepository;

public class RescheduleAlarmsService extends LifecycleService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        AlarmRepository alarmRepository = new AlarmRepository(getApplication());

        alarmRepository.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                for (Alarm a : alarms) {
                    if (a.isStarted()) {
                        a.schedule(getApplicationContext());
                    }
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
