package healary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bse202.sda.healary.R;
import data.HealDbHelper;
import data.MedicamentContract;

public class AddMedicamentActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mCityEditText;
    private EditText mAgeEditText;
    private Button addBtn;

    private Spinner mGenderSpinner;

    private int mGender = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);

        // Todo
//        mNameEditText = findViewById(R.id.edit_guest_name);
//        mCityEditText = findViewById(R.id.edit_guest_city);
//        mAgeEditText = findViewById(R.id.edit_guest_age);
//        mGenderSpinner = findViewById(R.id.spinner_gender);

        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                insertGuest();
            }
        });

//        setupSpinner();
    }

    private void insertGuest() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String city = mCityEditText.getText().toString().trim();
        String ageString = mAgeEditText.getText().toString().trim();
        int age = Integer.parseInt(ageString);

        HealDbHelper dbHelper = new HealDbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MedicamentContract.MedicamentEntry.COLUMN_NAME, name);
        values.put(MedicamentContract.MedicamentEntry.COLUMN_CITY, city);
        values.put(MedicamentContract.MedicamentEntry.COLUMN_GENDER, mGender);
        values.put(MedicamentContract.MedicamentEntry.COLUMN_AGE, age);

        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(MedicamentContract.MedicamentEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Гость заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setSelection(2);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_female))) {
                        mGender = 0;
                    } else if (selection.equals(getString(R.string.gender_male))) {
                        mGender = 1;
                    } else {
                        mGender = 2;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 2;
            }
        });
    }
}