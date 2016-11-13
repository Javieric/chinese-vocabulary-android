package com.javi.chinesevocabulary.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.javi.chinesevocabulary.DBManager;
import com.javi.chinesevocabulary.DBManager.DataTable;
import com.javi.chinesevocabulary.DBManager.VocabularyTable;
import com.javi.chinesevocabulary.DBManager.SettingsTable;
import com.javi.chinesevocabulary.pojos.Resource;
import com.javi.chinesevocabulary.pojos.Settings;
import com.javi.chinesevocabulary.pojos.Vocabulary;

import java.util.ArrayList;
import java.util.Arrays;
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
                resourceContentValue.put(VocabularyTable.STAGE, resource.getStage());
                resourceContentValue.put(VocabularyTable.UNIT, resource.getUnit());

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
                VocabularyTable.CHINESE,
                VocabularyTable.STAGE,
                VocabularyTable.UNIT};
        Cursor cursorResource = db.query(VocabularyTable.TABLE, fieldsResource, null, null, null, null, null);
        if (cursorResource.moveToFirst()) {
            do {
                Resource resource = new Resource(
                        cursorResource.getString(Constants.ResourceTableIndexes.ENGLISH.getCode()),
                        cursorResource.getString(Constants.ResourceTableIndexes.PINYIN.getCode()),
                        cursorResource.getString(Constants.ResourceTableIndexes.CHINESE.getCode()),
                        cursorResource.getInt(Constants.ResourceTableIndexes.STAGE.getCode()),
                        cursorResource.getInt(Constants.ResourceTableIndexes.UNIT.getCode()));
                resources.add(resource);
            } while (cursorResource.moveToNext());
        }
        cursorResource.close();
        return resources;
    }

    public List<Resource> filterResources(Settings settings){
        String stages[] = settings.getStages().split("-");
        String units[] = settings.getUnits().split("-");
        List<Resource> resourceList = new ArrayList<>();
        String where = "";
        if(Arrays.asList(stages).contains("1")){

            if(Arrays.asList(units).contains("1.1")){
                where = where + VocabularyTable.UNIT + " = " + 1;
            }
            if(Arrays.asList(units).contains("1.2")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 2;
            }
            if(Arrays.asList(units).contains("1.3")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 3;
            }
            if(Arrays.asList(units).contains("1.4")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 4;
            }
            if(Arrays.asList(units).contains("1.5")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 5;
            }
            if(Arrays.asList(units).contains("1.6")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 6;
            }
        }
        if(Arrays.asList(stages).contains("2")){
            if(Arrays.asList(units).contains("2.7")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 7;
            }
            if(Arrays.asList(units).contains("2.8")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 8;
            }
            if(Arrays.asList(units).contains("2.9")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 9;
            }
            if(Arrays.asList(units).contains("2.10")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 10;
            }
            if(Arrays.asList(units).contains("2.11")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 11;
            }
            if(Arrays.asList(units).contains("2.12")){
                where = addOR(where) + VocabularyTable.UNIT + " = " + 12;
            }
        }

        Cursor cursor = db.query(VocabularyTable.TABLE, null, where, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                Resource resource = new Resource(
                        cursor.getString(Constants.ResourceTableIndexes.ENGLISH.getCode()),
                        cursor.getString(Constants.ResourceTableIndexes.PINYIN.getCode()),
                        cursor.getString(Constants.ResourceTableIndexes.CHINESE.getCode()),
                        cursor.getInt(Constants.ResourceTableIndexes.STAGE.getCode()),
                        cursor.getInt(Constants.ResourceTableIndexes.UNIT.getCode()));
                resourceList.add(resource);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return resourceList;
    }

    private String addOR(String where){
        if(!where.equals("")){
            where = where + " OR ";
        }
        return where;
    }

    public void saveSettings(Settings settings){
        Log.d(this.getClass().getName(), "saveSettings()");

        ContentValues settingsContentValue = new ContentValues();
        settingsContentValue.put(SettingsTable.MODE, settings.getMode());
        settingsContentValue.put(SettingsTable.STAGES, settings.getStages());
        settingsContentValue.put(SettingsTable.UNITS, settings.getUnits());

        clearSettings();
        db.insert(SettingsTable.TABLE, null, settingsContentValue);
        Log.d(this.getClass().getName(), "settingsSaved()");
    }

    public Settings getSettings(){
        Log.d(this.getClass().getName(), "getSettings()");
        String[] fieldsSettings = new String[] {
                SettingsTable.MODE,
                SettingsTable.STAGES,
                SettingsTable.UNITS};
        Cursor cursorSettings = db.query(SettingsTable.TABLE, fieldsSettings, null, null, null, null, null);
        Settings settings = new Settings();
        if (cursorSettings.moveToFirst()) {

//            Date lastUpdate = new Date(cursorData.getInt(Constants.DataTableIndexes.LAST_UPDATE.getCode()));
            settings = new Settings(cursorSettings.getInt(Constants.SettingsTableIndexes.MODE.getCode()),
                    cursorSettings.getString(Constants.SettingsTableIndexes.STAGES.getCode()),
                    cursorSettings.getString(Constants.SettingsTableIndexes.UNITS.getCode()));
        }
        cursorSettings.close();
        return settings;
    }

    public void clearVocabulary(){
        Log.d(this.getClass().getName(), "clearVocabulary()");
        db.delete(VocabularyTable.TABLE, null, null);
    }

    public void clearData(){
        Log.d(this.getClass().getName(), "clearData()");
        db.delete(DataTable.TABLE, null, null);
    }

    public void clearSettings(){
        Log.d(this.getClass().getName(), "clearSettings()");
        db.delete(SettingsTable.TABLE, null, null);
    }

    public void close(){
        db.close();
    }
}
