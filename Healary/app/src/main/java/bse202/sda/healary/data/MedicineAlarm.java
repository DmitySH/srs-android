package bse202.sda.healary.data;


import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.FRIDAY;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.MONDAY;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.RECURRING;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.SATURDAY;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.SUNDAY;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.THURSDAY;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.TUESDAY;
import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.WEDNESDAY;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.Calendar;

import bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver;
import bse202.sda.healary.createalarm.DayUtil;

@Entity(tableName = "medicine_alarm_table")
public class MedicineAlarm {
    @PrimaryKey(autoGenerate = true)
    private int alarmId;

    private int hour, minute;
    private boolean started, recurring;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private String title;

    private String description;
    private int count, minCount, dosage;


    private long created;

    public MedicineAlarm(int hour, int minute, String title,
                         long created, boolean started, boolean recurring,
                         boolean monday, boolean tuesday, boolean wednesday,
                         boolean thursday, boolean friday, boolean saturday,
                         boolean sunday, int count, String description,
                         int minCount, int dosage) {
        this.hour = hour;
        this.minute = minute;
        this.started = started;

        this.recurring = recurring;

        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;

        this.title = title;
        this.count = count;
        this.description = description;
        this.minCount = minCount;
        this.dosage = dosage;

        this.created = created;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isStarted() {
        return started;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isMonday() {
        return monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);

        intent.putExtra(TITLE, title);


        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }

    public String getRecurringDaysText() {

        StringBuilder days = new StringBuilder();
        if (monday) {
            days.append("Пн ");
        }
        if (tuesday) {
            days.append("Вт ");
        }
        if (wednesday) {
            days.append("Ср ");
        }
        if (thursday) {
            days.append("Чт ");
        }
        if (friday) {
            days.append("Пт ");
        }
        if (saturday) {
            days.append("Сб ");
        }
        if (sunday) {
            days.append("Вс ");
        }

        return days.toString();
    }

    public String getTitle() {
        return title;
    }

    public long getCreated() {
        return created;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getDosage() {
        return dosage;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }
}
