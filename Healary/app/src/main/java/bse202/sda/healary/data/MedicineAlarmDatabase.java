package bse202.sda.healary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MedicineAlarm.class}, version = 1, exportSchema = false)
public abstract class MedicineAlarmDatabase extends RoomDatabase {
    public abstract MedicineAlarmDao alarmDao();

    private static volatile MedicineAlarmDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MedicineAlarmDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedicineAlarmDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MedicineAlarmDatabase.class,
                            "medicine_alarm_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
