package com.javi.chinesevocabulary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.javi.chinesevocabulary.DBManager.DataTable;
import com.javi.chinesevocabulary.DBManager.VocabularyTable;
import com.javi.chinesevocabulary.DBManager.SettingsTable;
import com.javi.chinesevocabulary.helpers.Constants;
import com.javi.chinesevocabulary.helpers.DBHelper;
import com.javi.chinesevocabulary.helpers.DropboxUrl;
import com.javi.chinesevocabulary.helpers.HttpHelper;
import com.javi.chinesevocabulary.pojos.Resource;
import com.javi.chinesevocabulary.pojos.Settings;
import com.javi.chinesevocabulary.pojos.Vocabulary;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private AlertDialog.Builder alertbox;
    private DBHelper dbData;
    private DBHelper dbResources;
    private DBHelper dbSettings;
    private TextView textViewQuestion;
    private TextView textViewAnswer;
    private TextView textViewTranslation;
    private Button buttonNext;
    private List<Resource> resourceList;
    private Resource resource;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQuestion = (TextView)findViewById(R.id.textViewQuestion);
        textViewAnswer = (TextView)findViewById(R.id.textViewAnswer);
        textViewTranslation = (TextView)findViewById(R.id.textViewTranslation);
        buttonNext = (Button)findViewById(R.id.buttonNext);

        dbData = new DBHelper(this, DataTable.TABLE);
        dbResources = new DBHelper(this, VocabularyTable.TABLE);
        dbSettings = new DBHelper(this, SettingsTable.TABLE);
        dialog = ProgressDialog.show(this, "", this.getString(R.string.please_wait), true);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        Retrofit retrofit = new Retrofit.Builder().baseUrl(DropboxUrl.URL).addConverterFactory(GsonConverterFactory.create()).build();

        HttpHelper httpHelper = retrofit.create(HttpHelper.class);
        Call<Vocabulary> call = httpHelper.getVocabulary();
        call.enqueue(new Callback<Vocabulary>() {
            @Override
            public void onResponse(Call<Vocabulary> call, Response<Vocabulary> response) {
                Log.d(this.getClass().getName(), "onCreate() - dismiss process dialog");
                dialog.dismiss();
                Log.d(this.getClass().getName(), "onCreate() - success = " + response.isSuccessful());
                if (response.isSuccessful()) {
                    Vocabulary remoteVocabulary = response.body();
                    Vocabulary localVocabulary = dbData.getData();
//                    if((localVocabulary != null && localVocabulary.getVersion() < remoteVocabulary.getVersion()) || localVocabulary == null){
                        dbData.saveData(remoteVocabulary);
                        dbResources.saveResources(remoteVocabulary.getResources());
                        Log.d(this.getClass().getName(), "onCreate() - data saved");
//                    }else{
//                        Log.d(this.getClass().getName(), "onCreate() - no need to save, local version = " + localVocabulary.getVersion() + ", remote version = " + remoteVocabulary.getVersion());
//                    }
                    Settings settings = dbSettings.getSettings();
                    resourceList = dbResources.filterResources(settings);
                    next();
                } else {
                    Log.d(this.getClass().getName(), "onCreate() - " + MainActivity.this.getString(R.string.unexpected_status_code) + ": " + response.code());
                    showAlert(MainActivity.this.getString(R.string.error), MainActivity.this.getString(R.string.unexpected_status_code) + ": " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Vocabulary> call, Throwable t) {
                dialog.dismiss();
                Log.d(this.getClass().getName(), "onCreate() - error: " + t.getMessage());
                if(t instanceof SocketTimeoutException){
                    showAlert(MainActivity.this.getString(R.string.error), MainActivity.this.getString(R.string.timeout_error));
                }else{
                    showAlert(MainActivity.this.getString(R.string.error), t.getMessage());
                }
            }
        });
    }

    private void next(){
        Log.d(this.getClass().getName(), "next()");
        Settings settings = dbSettings.getSettings();
        if(status){
            if(settings.getMode() == Constants.MODE_CHINESE){
                textViewAnswer.setText(resource.getPinyin());
            }else{
                textViewAnswer.setText(resource.getChinese());
            }
            textViewTranslation.setText(resource.getEnglish());
            status = false;
        }else{
            textViewTranslation.setText("");
            textViewAnswer.setText("");
            int random = (int)Math.floor((Math.random() * resourceList.size()));
            Log.d(this.getClass().getName(), "next() - random int = " + random + ", total = " + resourceList.size());
            resource = resourceList.get(random);
            if(settings.getMode() == Constants.MODE_CHINESE){
                textViewQuestion.setText(resource.getChinese());
            }else{
                textViewQuestion.setText(resource.getPinyin());
            }
            status = true;
        }
    }

    private void showAlert(String title, String message){
        Log.d(this.getClass().getName(), "showAlert() - title: " + title + ", message: " + message);
        alertbox = new AlertDialog.Builder(MainActivity.this);
        alertbox.setTitle(title);
        alertbox.setMessage(message);
        alertbox.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
        alertbox.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                Log.d(this.getClass().getName(), "onOptionsItemSelected() - calling SettingsActivity.java");
                startActivity(i);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }
}
