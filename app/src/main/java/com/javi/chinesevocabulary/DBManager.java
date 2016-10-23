package com.javi.chinesevocabulary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.javi.chinesevocabulary.helpers.Constants;
import com.javi.chinesevocabulary.pojos.Vocabulary;

/**
 * Created by javi on 23/10/2016.
 */

public class DBManager extends SQLiteOpenHelper {

    private static final int BD_LAST_VERSION = 1;

    public final class DataTable {
        public static final String TABLE = "VERSION_DATA";
//        public static final String LAST_UPDATE = "LAST_UPDATE";
        public static final String VERSION_CODE = "VERSION_CODE";
    }

    public final class VocabularyTable {
        public static final String TABLE = "VOCABULARY";
        public static final String ENGLISH = "ENGLISH";
        public static final String PINYIN = "PINYIN";
        public static final String CHINESE = "CHINESE";
        public static final String STAGE = "STAGE";
        public static final String UNIT = "UNIT";
    }

    private static final StringBuffer SQL_CREATE_DATA_TABLE = new StringBuffer(
            "CREATE TABLE IF NOT EXISTS ").append(DataTable.TABLE).append("(")
//            .append(DataTable.LAST_UPDATE).append(" ").append(Constants.INTEGER).append(", ")
            .append(DataTable.VERSION_CODE).append(" ").append(Constants.INTEGER).append(")");

    private static final StringBuffer SQL_CREATE_VOCABULARY_TABLE = new StringBuffer(
            "CREATE TABLE IF NOT EXISTS ").append(DataTable.TABLE).append("(")
            .append(VocabularyTable.ENGLISH).append(" ").append(Constants.TEXT).append(", ")
            .append(VocabularyTable.PINYIN).append(" ").append(Constants.TEXT).append(", ")
            .append(VocabularyTable.CHINESE).append(" ").append(Constants.TEXT).append(",")
            .append(VocabularyTable.STAGE).append(" ").append(Constants.INTEGER).append(",")
            .append(VocabularyTable.UNIT).append(" ").append(Constants.INTEGER).append(")");

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory,
                     int version) {
        super(context, name, factory, version);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, BD_LAST_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATA_TABLE.toString());
        db.execSQL(SQL_CREATE_VOCABULARY_TABLE.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + VocabularyTable.TABLE);
    }
}

