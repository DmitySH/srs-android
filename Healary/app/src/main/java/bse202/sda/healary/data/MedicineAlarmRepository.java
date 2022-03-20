package bse202.sda.healary.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MedicineAlarmRepository {
    private MedicineAlarmDao alarmDao;
    private LiveData<List<MedicineAlarm>> alarmsLiveData;

    public MedicineAlarmRepository(Application application) {
        MedicineAlarmDatabase db = MedicineAlarmDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public void insert(MedicineAlarm alarm) {
        MedicineAlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.insert(alarm);
        });
    }

    public void edit(MedicineAlarm alarm) {
        MedicineAlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.update(alarm);
        });
    }

    public LiveData<List<MedicineAlarm>> getById(long id) {
        LiveData<List<MedicineAlarm>> d = alarmDao.getById(id);

        return d;
    }

    public void deleteCancelled(Object alarm) {
        new deleteAsyncTask(alarmDao).execute(alarm);
    }

    private static class deleteAsyncTask extends AsyncTask<Object, Void, Void> {
        private MedicineAlarmDao mAsyncTaskDao;

        deleteAsyncTask(MedicineAlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            mAsyncTaskDao.deleteCancelled();
            return null;
        }
    }

    public void update(MedicineAlarm alarm) {
        MedicineAlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.update(alarm);
        });
    }

    public LiveData<List<MedicineAlarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
