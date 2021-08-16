package rstt.timersmokers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dats1";
    public static final String TABLE_CONTACTS = "dats";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "sigars";
    public static final String KEY_MAIL = "lastdate1";
    public static final String KEY_DATE = "datesq";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key AUTOINCREMENT," + KEY_NAME + " INTEGER," + KEY_MAIL + " NUMERIC, " + KEY_DATE + " INTEGER" +  ")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);

        onCreate(db);

    }
    public void insertData(int valueX, int valueY){
        valueX = Integer.parseInt(KEY_NAME);
        valueY = Integer.parseInt(KEY_DATE);

    }

}