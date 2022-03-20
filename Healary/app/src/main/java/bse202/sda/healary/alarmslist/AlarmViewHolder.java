package bse202.sda.healary.alarmslist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Locale;

import bse202.sda.healary.R;
import bse202.sda.healary.data.MedicineAlarm;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private final TextView alarmTime;
    private final TextView alarmRecurringDays;
    private final TextView alarmTitle;

    SwitchCompat alarmStarted;

    private final OnToggleAlarmListener listener;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.item_alarm_time);
        alarmStarted = itemView.findViewById(R.id.item_alarm_started);
        alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
        alarmTitle = itemView.findViewById(R.id.item_alarm_title);

        this.listener = listener;
    }

    public void bind(MedicineAlarm alarm) {
        String alarmText = String.format(Locale.getDefault(), "%02d:%02d", alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
        alarmStarted.setChecked(alarm.isStarted());
        alarmRecurringDays.setText(alarm.getRecurringDaysText());

        alarmTitle.setText(String.format(Locale.getDefault(), "%s | %d", alarm.getTitle(), alarm.getCount()));


        alarmStarted.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onToggle(alarm));
    }
}
