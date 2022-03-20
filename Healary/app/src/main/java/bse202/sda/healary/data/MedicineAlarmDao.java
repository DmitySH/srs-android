package bse202.sda.healary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicineAlarmDao {
    @Insert
    void insert(MedicineAlarm alarm);

    @Query("DELETE FROM MEDICINE_ALARM_TABLE")
    void deleteAll();

    @Query("DELETE FROM MEDICINE_ALARM_TABLE WHERE NOT started")
    void deleteCancelled();

    @Query("SELECT * FROM MEDICINE_ALARM_TABLE WHERE started ORDER BY created ASC")
    LiveData<List<MedicineAlarm>> getAlarms();

    @Update
    void update(MedicineAlarm alarm);
}
