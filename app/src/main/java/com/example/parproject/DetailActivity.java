package com.example.parproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.parproject.database.ActivityProviderContract;


public class DetailActivity extends AppCompatActivity {

    CustomAdapter dataAdapter;
    ListView listView;
    String[] projection;
    int[] colResIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Activities");
        listView = findViewById(R.id.listView);


        projection = new String[] {
                ActivityProviderContract.NAME,
                ActivityProviderContract.TIME,
                ActivityProviderContract.METRE,
                ActivityProviderContract.SPEED,
                ActivityProviderContract.DATE,
                ActivityProviderContract._ID
        };

        colResIds = new int[] {
                R.id.tvName,
                R.id.tvDuration,
                R.id.tvMetres,
                R.id.tvAvgSpeed,
                R.id.tvDate
        };

        Cursor cursor = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI, projection,
                null, null, "_ID desc");
        dataAdapter = new CustomAdapter(
                this,
                R.layout.item_layout,
                cursor,
                projection,
                colResIds,
                0);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                //get running details
                String itemId = cursor.getString(cursor.getColumnIndex(ActivityProviderContract._ID));
                long time = cursor.getLong(cursor.getColumnIndex(ActivityProviderContract.TIME));
                String metres = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.METRE));
                String avgSpeed = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.SPEED));
                String date = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.DATE));
                String name = cursor.getString(cursor.getColumnIndex(ActivityProviderContract.NAME));


                //items to be passed to EditActivity
                Bundle bundle = new Bundle();
                bundle.putString("itemId",itemId);
                bundle.putLong("timer",time);
                bundle.putString("metres",metres);
                bundle.putString("avgSpeed",avgSpeed);
                bundle.putString("date",date);
                bundle.putString("name",name);


                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        //refresh ListView
        Cursor cursor = getContentResolver().query(ActivityProviderContract.ACTIVITY_URI, projection, null, null, "aDate desc");
        dataAdapter = new CustomAdapter(
                this,
                R.layout.item_layout,
                cursor,
                projection,
                colResIds,
                0);

        listView.setAdapter(dataAdapter);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //newly created CountTIme
        Intent intent = new Intent(this, CountTime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NO_ANIMATION| Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        startActivity(intent);
    }
}
