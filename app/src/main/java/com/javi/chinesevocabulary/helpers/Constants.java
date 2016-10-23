package com.javi.chinesevocabulary.helpers;

/**
 * Created by javi on 23/10/2016.
 */

public class Constants {

    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";

    public enum ResourceTableIndexes {

        ENGLISH(0), PINYIN(1), CHINESE(2), STAGE(3), UNIT(4);
        private int resourceTableIndex;
        ResourceTableIndexes(int resourceTableIndex) {
            this.resourceTableIndex = resourceTableIndex;
        }
        public int getCode() {
            return this.resourceTableIndex;
        }
    }

    public enum DataTableIndexes {

        /*LAST_UPDATE(0), */VERSION_CODE(0);
        private int dataTableIndex;
        DataTableIndexes(int dataTableIndex) {
            this.dataTableIndex = dataTableIndex;
        }
        public int getCode() {
            return this.dataTableIndex;
        }
    }

    public enum SettingsTableIndexes {

        MODE(0), STAGES(1), UNITS(2);
        private int settingsTableIndex;
        SettingsTableIndexes(int settingsTableIndex) {
            this.settingsTableIndex = settingsTableIndex;
        }
        public int getCode() {
            return this.settingsTableIndex;
        }
    }
}
