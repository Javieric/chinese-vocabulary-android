package com.javi.chinesevocabulary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.javi.chinesevocabulary.helpers.DBHelper;
import com.javi.chinesevocabulary.helpers.DropboxUrl;
import com.javi.chinesevocabulary.helpers.HttpHelper;
import com.javi.chinesevocabulary.pojos.Vocabulary;
import com.javi.chinesevocabulary.DBManager.DataTable;
import com.javi.chinesevocabulary.DBManager.VocabularyTable;

import java.net.SocketTimeoutException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbData = new DBHelper(this, DataTable.TABLE);
        dbResources = new DBHelper(this, VocabularyTable.TABLE);
        dialog = ProgressDialog.show(this, "", this.getString(R.string.please_wait), true);
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
                    if((localVocabulary != null && localVocabulary.getVersion() < remoteVocabulary.getVersion())
                            || localVocabulary == null){
                        dbData.saveData(remoteVocabulary);
                        dbResources.saveResources(remoteVocabulary.getResources());
                        Log.d(this.getClass().getName(), "onCreate() - data saved");
                    }else{
                        Log.d(this.getClass().getName(), "onCreate() - no need to save, local version = " + localVocabulary.getVersion() + ", remote version = " + remoteVocabulary.getVersion());
                    }
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
}
