package com.example.parproject;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.parproject.database.ActivityProviderContract;
import com.example.parproject.tool.App;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveActivity extends AppCompatActivity {

    private TextView tvDate, tvTime, tvMetres, tvAvgSpeed;
    private EditText eTName;
    //these variable keeps data that are passed from LocationService
    private long timer;
    private String metres;
    private String avgSpeed;

    java.util.Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        setTitle("Review & Save");

        eTName = findViewById(R.id.eTName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvMetres = findViewById(R.id.tvMetres);
        tvAvgSpeed = findViewById(R.id.tvAvgSpeed);

        //data that are sent from LocationService
        Bundle bundle = getIntent().getExtras();
        timer = bundle.getLong("timer");
        metres = bundle.getString("metre");
        avgSpeed = bundle.getString("avgSpeed");


        //current date and time
        currentTime = Calendar.getInstance().getTime();


        tvDate.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a").format(currentTime));
        tvTime.setText(App.secondFormatString(timer));
        tvMetres.setText(metres);
        tvAvgSpeed.setText(avgSpeed);
    }

    public void dltButtonSave(View view){
        startNewMainActivity();
    }


    public void saveActivity(View view){
        Uri mNewUri;
        //create new contentValues
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(ActivityProviderContract.NAME, TextUtils.isEmpty(eTName.getText()) ? "Testing Activity" :
                eTName.getText().toString().trim());
        mNewValues.put(ActivityProviderContract.TIME, timer);
        mNewValues.put(ActivityProviderContract.METRE, metres);
        mNewValues.put(ActivityProviderContract.SPEED, avgSpeed);
        mNewValues.put(ActivityProviderContract.DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime));
        //call insert query
        mNewUri = getContentResolver().insert(ActivityProviderContract.ACTIVITY_URI, mNewValues);
        long id = ContentUris.parseId(mNewUri);
        if(id!=0){
            Toast.makeText(this,"New Activity Added", Toast.LENGTH_LONG).show();
        }
        startNewMainActivity();

    }


    // newly create Counttime
    public void startNewMainActivity(){
        Intent intent = new Intent(this, CountTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NO_ANIMATION| Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        startActivity(intent);
    }

    //do nothing when "back" button is pressed
    @Override
    public void onBackPressed() {
        //disabled
    }
}
