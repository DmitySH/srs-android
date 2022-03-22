package bse202.sda.healary.alarmslist;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import bse202.sda.healary.R;
import bse202.sda.healary.data.MedicineAlarm;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private final TextView alarmTime;
    private final TextView alarmRecurringDays;
    private final TextView alarmTitle;
    private final LinearLayout layout;
    private final OnToggleAlarmListener listener;
    SwitchCompat alarmStarted;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener) {
        super(itemView);
        layout = itemView.findViewById(R.id.item_layout);
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

        alarmTitle.setText(String.format(Locale.getDefault(), "%s | осталось %d", alarm.getTitle(), alarm.getCount()));

        if (alarm.getCount() < alarm.getMinCount()) {
            layout.setBackgroundColor(Color.parseColor("#A2FEA4C2"));
        }

        Bundle bundle = new Bundle();
        bundle.putInt("alarmId", alarm.getAlarmId());
        layout.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_alarmsListFragment_to_editAlarmFragment, bundle));

        alarmStarted.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onToggle(alarm));
    }
}
