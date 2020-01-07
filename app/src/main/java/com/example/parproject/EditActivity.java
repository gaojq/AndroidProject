package com.example.parproject;

import android.content.ContentValues;
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

public class EditActivity extends AppCompatActivity {

    private TextView tvDate, tvTime, tvMetres, tvAvgSpeed;
    private EditText eTName;

    private String dateString;//date in String format
    private String id;
    private long timer;
    private String metres;
    private String avgSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Review & Save");
        eTName = findViewById(R.id.eTName);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvMetres = findViewById(R.id.tvMetres);
        tvAvgSpeed = findViewById(R.id.tvAvgSpeed);
        Bundle bundle = getIntent().getExtras();
        //get data sent from DetailActivity
        String name = bundle.getString("name");
        metres = bundle.getString("metres");
        avgSpeed = bundle.getString("avgSpeed");
        timer = bundle.getLong("timer");
        dateString = bundle.getString("date");
        id = bundle.getString("itemId");

        tvDate.setText(new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a").format(App.convertStringToDate(dateString)));
        tvTime.setText(App.secondFormatString(timer));
        tvMetres.setText(metres);
        tvAvgSpeed.setText(avgSpeed);
        eTName.setText(name);
        eTName.setHint(name);
    }
    public void dltButton(View view){
        String whereClause = ActivityProviderContract._ID + "=" + id;
        int i = getContentResolver().delete(ActivityProviderContract.ACTIVITY_URI,whereClause,null);
        if(i!=0){
            Toast.makeText(getApplicationContext(),"Activity Deleted", Toast.LENGTH_LONG).show();
        }
    }
    public void saveActivity(View view){
        //create new contentValues
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(ActivityProviderContract.NAME, TextUtils.isEmpty(eTName.getText()) ? eTName.getHint().toString().trim()
                : eTName.getText().toString().trim());
        mNewValues.put(ActivityProviderContract.TIME, timer);
        mNewValues.put(ActivityProviderContract.METRE, metres);
        mNewValues.put(ActivityProviderContract.SPEED, avgSpeed);
        mNewValues.put(ActivityProviderContract.DATE, dateString);
        String whereClause = ActivityProviderContract._ID + "=" + id;
        //call update query
        int i = getContentResolver().update(ActivityProviderContract.ACTIVITY_URI, mNewValues,whereClause,null);
        if(i!=0){
            Toast.makeText(this,"Activity Updated", Toast.LENGTH_LONG).show();
        }
        finish();

    }


}
