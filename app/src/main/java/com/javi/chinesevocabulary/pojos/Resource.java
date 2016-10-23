package com.javi.chinesevocabulary.pojos;

/**
 * Created by javi on 22/10/2016.
 */

public class Resource {

    private String english;
    private String pinyin;
    private String chinese;
    private int stage;
    private int unit;

    public Resource() {
    }

    public Resource(String english, String pinyin, String chinese, int stage, int unit) {
        this.english = english;
        this.pinyin = pinyin;
        this.chinese = chinese;
        this.stage = stage;
        this.unit = unit;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
