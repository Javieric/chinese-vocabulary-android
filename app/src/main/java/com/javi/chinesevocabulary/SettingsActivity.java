package com.javi.chinesevocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.javi.chinesevocabulary.helpers.DBHelper;
import com.javi.chinesevocabulary.DBManager.SettingsTable;
import com.javi.chinesevocabulary.pojos.Settings;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkBoxStage1;
    private CheckBox checkBoxStage2;
    private CheckBox checkBoxUnit1;
    private CheckBox checkBoxUnit2;
    private CheckBox checkBoxUnit3;
    private CheckBox checkBoxUnit4;
    private CheckBox checkBoxUnit5;
    private CheckBox checkBoxUnit6;
    private CheckBox checkBoxUnit7;
    private CheckBox checkBoxUnit8;
    private CheckBox checkBoxUnit9;
    private CheckBox checkBoxUnit10;
    private CheckBox checkBoxUnit11;
    private CheckBox checkBoxUnit12;
    private Spinner spinnerMode;
    private Button buttonSave;
    private DBHelper dbSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkBoxStage1 = (CheckBox)findViewById(R.id.checkBoxStage1);
        checkBoxStage2 = (CheckBox)findViewById(R.id.checkBoxStage2);
        checkBoxUnit1 = (CheckBox)findViewById(R.id.checkBoxUnit1);
        checkBoxUnit2 = (CheckBox)findViewById(R.id.checkBoxUnit2);
        checkBoxUnit3 = (CheckBox)findViewById(R.id.checkBoxUnit3);
        checkBoxUnit4 = (CheckBox)findViewById(R.id.checkBoxUnit4);
        checkBoxUnit5 = (CheckBox)findViewById(R.id.checkBoxUnit5);
        checkBoxUnit6 = (CheckBox)findViewById(R.id.checkBoxUnit6);
        checkBoxUnit7 = (CheckBox)findViewById(R.id.checkBoxUnit7);
        checkBoxUnit8 = (CheckBox)findViewById(R.id.checkBoxUnit8);
        checkBoxUnit9 = (CheckBox)findViewById(R.id.checkBoxUnit9);
        checkBoxUnit10 = (CheckBox)findViewById(R.id.checkBoxUnit10);
        checkBoxUnit11 = (CheckBox)findViewById(R.id.checkBoxUnit11);
        checkBoxUnit12 = (CheckBox)findViewById(R.id.checkBoxUnit12);

        buttonSave = (Button)findViewById(R.id.buttonSave);

        spinnerMode = (Spinner) findViewById(R.id.spinnerMode);

        dbSettings = new DBHelper(this, SettingsTable.TABLE);

        ArrayAdapter<CharSequence> adapterMode = ArrayAdapter.createFromResource(this, R.array.mode_spinner, android.R.layout.simple_spinner_item);
        adapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(adapterMode);

        Settings settings = dbSettings.getSettings();
        if(settings.getMode() == 0){
            spinnerMode.setSelection(0);
        }else{
            spinnerMode.setSelection(1);
        }

        String stages[] = settings.getStages().split("-");
        String units[] = settings.getUnits().split("-");

        if(Arrays.asList(stages).contains("1")){
            checkBoxStage1.setChecked(true);
        }else{
            checkBoxStage1.setChecked(false);
        }
        if(Arrays.asList(stages).contains("2")){
            checkBoxStage2.setChecked(true);
        }else{
            checkBoxStage2.setChecked(false);
        }

        if(Arrays.asList(units).contains("1.1")){
            checkBoxUnit1.setChecked(true);
        }else{
            checkBoxUnit1.setChecked(false);
        }
        if(Arrays.asList(units).contains("1.2")){
            checkBoxUnit2.setChecked(true);
        }else{
            checkBoxUnit2.setChecked(false);
        }
        if(Arrays.asList(units).contains("1.3")){
            checkBoxUnit3.setChecked(true);
        }else{
            checkBoxUnit3.setChecked(false);
        }
        if(Arrays.asList(units).contains("1.4")){
            checkBoxUnit4.setChecked(true);
        }else{
            checkBoxUnit4.setChecked(false);
        }
        if(Arrays.asList(units).contains("1.5")){
            checkBoxUnit5.setChecked(true);
        }else{
            checkBoxUnit5.setChecked(false);
        }
        if(Arrays.asList(units).contains("1.6")){
            checkBoxUnit6.setChecked(true);
        }else{
            checkBoxUnit6.setChecked(false);
        }
        if(Arrays.asList(units).contains("2.7")){
            checkBoxUnit7.setChecked(true);
        }else{
            checkBoxUnit7.setChecked(false);
        }
        if(Arrays.asList(units).contains("2.8")){
            checkBoxUnit8.setChecked(true);
        }else{
            checkBoxUnit8.setChecked(false);
        }
        if(Arrays.asList(units).contains("2.9")){
            checkBoxUnit9.setChecked(true);
        }else{
            checkBoxUnit9.setChecked(false);
        }
        if(Arrays.asList(units).contains("2.10")){
            checkBoxUnit10.setChecked(true);
        }else{
            checkBoxUnit10.setChecked(false);
        }
        if(Arrays.asList(units).contains("2.11")){
            checkBoxUnit11.setChecked(true);
        }else{
            checkBoxUnit11.setChecked(false);
        }
        if(Arrays.asList(units).contains("2.12")){
            checkBoxUnit12.setChecked(true);
        }else{
            checkBoxUnit12.setChecked(false);
        }

        if(!checkBoxStage1.isChecked()){
            checkBoxUnit1.setEnabled(false);
            checkBoxUnit2.setEnabled(false);
            checkBoxUnit3.setEnabled(false);
            checkBoxUnit4.setEnabled(false);
            checkBoxUnit5.setEnabled(false);
            checkBoxUnit6.setEnabled(false);
        }
        if(!checkBoxStage2.isChecked()){
            checkBoxUnit7.setEnabled(false);
            checkBoxUnit8.setEnabled(false);
            checkBoxUnit9.setEnabled(false);
            checkBoxUnit10.setEnabled(false);
            checkBoxUnit11.setEnabled(false);
            checkBoxUnit12.setEnabled(false);
        }

        checkBoxStage1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!checkBoxStage1.isChecked()){
                    checkBoxUnit1.setEnabled(false);
                    checkBoxUnit2.setEnabled(false);
                    checkBoxUnit3.setEnabled(false);
                    checkBoxUnit4.setEnabled(false);
                    checkBoxUnit5.setEnabled(false);
                    checkBoxUnit6.setEnabled(false);
                }else{
                    checkBoxUnit1.setEnabled(true);
                    checkBoxUnit2.setEnabled(true);
                    checkBoxUnit3.setEnabled(true);
                    checkBoxUnit4.setEnabled(true);
                    checkBoxUnit5.setEnabled(true);
                    checkBoxUnit6.setEnabled(true);
                }
            }
        });

        checkBoxStage2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!checkBoxStage2.isChecked()){
                    checkBoxUnit7.setEnabled(false);
                    checkBoxUnit8.setEnabled(false);
                    checkBoxUnit9.setEnabled(false);
                    checkBoxUnit10.setEnabled(false);
                    checkBoxUnit11.setEnabled(false);
                    checkBoxUnit12.setEnabled(false);
                }else{
                    checkBoxUnit7.setEnabled(true);
                    checkBoxUnit8.setEnabled(true);
                    checkBoxUnit9.setEnabled(true);
                    checkBoxUnit10.setEnabled(true);
                    checkBoxUnit11.setEnabled(true);
                    checkBoxUnit12.setEnabled(true);
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stages = "";
                String units = "";
                int mode;
                if(spinnerMode.getSelectedItem().toString().equals(getResources().getString(R.string.pinyin))){
                    mode = 0;
                }else{
                    mode = 1;
                }
                if(checkBoxStage1.isChecked()){
                    stages = stages + "1";
                }
                if(checkBoxStage2.isChecked()){
                    if(!"".equals(stages)){
                        stages = stages + "-";
                    }
                    stages = stages + "2";
                }
                if(checkBoxUnit1.isChecked()){
                    units = units + "1.1";
                }
                if(checkBoxUnit2.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "1.2";
                }
                if(checkBoxUnit3.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "1.3";
                }
                if(checkBoxUnit4.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "1.4";
                }
                if(checkBoxUnit5.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "1.5";
                }
                if(checkBoxUnit6.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "1.6";
                }
                if(checkBoxUnit7.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "2.7";
                }
                if(checkBoxUnit8.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "2.8";
                }
                if(checkBoxUnit9.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "2.9";
                }
                if(checkBoxUnit10.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "2.10";
                }
                if(checkBoxUnit11.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "2.11";
                }
                if(checkBoxUnit12.isChecked()){
                    if(!"".equals(units)){
                        units = units + "-";
                    }
                    units = units + "2.12";
                }

                Settings settings = new Settings(mode, stages, units);
                dbSettings.saveSettings(settings);
            }
        });
    }
}
