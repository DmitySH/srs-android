package data;

import android.provider.BaseColumns;

public final class MedicamentContract {

    private MedicamentContract() {
    }

    public static final class MedicamentEntry implements BaseColumns {
        public final static String TABLE_NAME = "medicament";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_CITY = "city";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_AGE = "age";

        public static final int GENDER_FEMALE = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_UNKNOWN = 2;
    }
}