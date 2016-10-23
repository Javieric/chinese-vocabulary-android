package com.javi.chinesevocabulary.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.javi.chinesevocabulary.DBManager;
import com.javi.chinesevocabulary.DBManager.VocabularyTable;
import com.javi.chinesevocabulary.DBManager.DataTable;
import com.javi.chinesevocabulary.pojos.Resource;
import com.javi.chinesevocabulary.pojos.Vocabulary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by javi on 23/10/2016.
 */

public class DBHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context, String name){
        DBManager dbManager = new DBManager(context, name, null);
        db = dbManager.getWritableDatabase();
    }

    public void saveData(Vocabulary vocabulary){
        Log.d(this.getClass().getName(), "saveData()");

        ContentValues dataContentValue = new ContentValues();
        dataContentValue.put(DataTable.VERSION_CODE, vocabulary.getVersion());
//        dataContentValue.put(DataTable.LAST_UPDATE, (new Date()).getTime());

//        clearVocabulary();
//        saveResources(vocabulary.getResources());

        clearData();
        db.insert(DataTable.TABLE, null, dataContentValue);
    }

    public Vocabulary getData(){
        Log.d(this.getClass().getName(), "getData()");
        String[] fieldsData = new String[] {
                DataTable.VERSION_CODE/*,
                DataTable.LAST_UPDATE*/};
        Cursor cursorData = db.query(DataTable.TABLE, fieldsData, null, null, null, null, null);
        Vocabulary vocabulary = null;
        if (cursorData.moveToFirst()) {

//            Date lastUpdate = new Date(cursorData.getInt(Constants.DataTableIndexes.LAST_UPDATE.getCode()));
            vocabulary = new Vocabulary(new ArrayList<Resource>(),
                    cursorData.getInt(Constants.DataTableIndexes.VERSION_CODE.getCode()));
        }
        cursorData.close();
        return vocabulary;
    }

    public void saveResources(List<Resource> resources){
        Log.d(this.getClass().getName(), "saveResources()");
        clearVocabulary();
        db.beginTransaction();
        try {
            for(Resource resource: resources){
                ContentValues resourceContentValue = new ContentValues();
                resourceContentValue.put(VocabularyTable.ENGLISH, resource.getEnglish());
                resourceContentValue.put(VocabularyTable.PINYIN, resource.getPinyin());
                resourceContentValue.put(VocabularyTable.CHINESE, resource.getChinese());

                db.insert(VocabularyTable.TABLE, null, resourceContentValue);
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<Resource> getResources(){
        Log.d(this.getClass().getName(), "getResources()");
        List<Resource> resources = new ArrayList<>();
        String[] fieldsResource = new String[] {
                VocabularyTable.ENGLISH,
                VocabularyTable.PINYIN,
                VocabularyTable.CHINESE};
        Cursor cursorResource = db.query(VocabularyTable.TABLE, fieldsResource, null, null, null, null, null);
        if (cursorResource.moveToFirst()) {
            do {
                Resource resource = new Resource(
                        cursorResource.getString(Constants.ResourceTableIndexes.ENGLISH.getCode()),
                        cursorResource.getString(Constants.ResourceTableIndexes.PINYIN.getCode()),
                        cursorResource.getString(Constants.ResourceTableIndexes.CHINESE.getCode()));
                resources.add(resource);
            } while (cursorResource.moveToNext());
        }
        cursorResource.close();
        return resources;
    }

    public void clearVocabulary(){
        Log.d(this.getClass().getName(), "clearVocabulary()");
        db.delete(VocabularyTable.TABLE, null, null);
    }

    public void clearData(){
        Log.d(this.getClass().getName(), "clearData()");
        db.delete(DataTable.TABLE, null, null);
    }

    public void close(){
        db.close();
    }
}
