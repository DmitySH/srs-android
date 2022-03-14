package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HealDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HealDbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "heal.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link HealDbHelper}.
     *
     * @param context Контекст приложения
     */
    public HealDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + MedicamentContract.MedicamentEntry.TABLE_NAME + " ("
                + MedicamentContract.MedicamentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedicamentContract.MedicamentEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + MedicamentContract.MedicamentEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + MedicamentContract.MedicamentEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 3, "
                + MedicamentContract.MedicamentEntry.COLUMN_AGE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MedicamentContract.MedicamentEntry.TABLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }
}
