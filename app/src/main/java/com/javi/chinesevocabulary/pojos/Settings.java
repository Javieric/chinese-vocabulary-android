package com.javi.chinesevocabulary.pojos;

import com.javi.chinesevocabulary.helpers.Constants;

/**
 * Created by javi on 23/10/2016.
 */

public class Settings {
    private int mode;
    private String stages;
    private String units;

    public Settings() {
        this.mode = Constants.MODE_CHINESE;
        this.stages = "";
        this.units = "";
    }

    public Settings(int mode, String stages, String units) {
        this.mode = mode;
        this.stages = stages;
        this.units = units;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
