package bse202.sda.healary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import data.HealDbHelper;
import data.MedicamentContract;

public class MainActivity extends AppCompatActivity {
    private Button addBtn;
    private HealDbHelper dbHelper;
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMedicamentActivity.class);
                startActivity(intent);
            }
        });

        createNotificationChannnel();
        dbHelper = new HealDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void createNotificationChannnel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);
    }



    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                MedicamentContract.MedicamentEntry._ID,
                MedicamentContract.MedicamentEntry.COLUMN_NAME,
                MedicamentContract.MedicamentEntry.COLUMN_CITY,
                MedicamentContract.MedicamentEntry.COLUMN_GENDER,
                MedicamentContract.MedicamentEntry.COLUMN_AGE};

        // Делаем запрос
        Cursor cursor = db.query(
                MedicamentContract.MedicamentEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        TextView displayTextView = (TextView) findViewById(R.id.text_view_info);

        try {
            displayTextView.setText("Таблица содержит " + cursor.getCount() + " гостей.\n\n");
            displayTextView.append(MedicamentContract.MedicamentEntry._ID + " - " +
                    MedicamentContract.MedicamentEntry.COLUMN_NAME + " - " +
                    MedicamentContract.MedicamentEntry.COLUMN_CITY + " - " +
                    MedicamentContract.MedicamentEntry.COLUMN_GENDER + " - " +
                    MedicamentContract.MedicamentEntry.COLUMN_AGE + "\n");

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(MedicamentContract.MedicamentEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(MedicamentContract.MedicamentEntry.COLUMN_NAME);
            int cityColumnIndex = cursor.getColumnIndex(MedicamentContract.MedicamentEntry.COLUMN_CITY);
            int genderColumnIndex = cursor.getColumnIndex(MedicamentContract.MedicamentEntry.COLUMN_GENDER);
            int ageColumnIndex = cursor.getColumnIndex(MedicamentContract.MedicamentEntry.COLUMN_AGE);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentCity = cursor.getString(cityColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                // Выводим значения каждого столбца
                displayTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCity + " - " +
                        currentGender + " - " +
                        currentAge));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
}
