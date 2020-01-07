package com.example.parproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.parproject.tool.ObjectSerializer;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class Memo extends AppCompatActivity {

    static ArrayList<String> places;
    static ArrayList<LatLng> locations;
    static ArrayAdapter arrayAdapter;
    //ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        Button button = (Button) findViewById(R.id.memoPlace);

        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> lati = new ArrayList<>();
        ArrayList<String> longti = new ArrayList<>();


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memoplace", Context.MODE_PRIVATE);
        try {
            places =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            lati =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitude",ObjectSerializer.serialize(new ArrayList<String>())));
            longti =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longtitude",ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(places.size()>0&& lati.size()>0 && longti.size()>0){
            if(places.size()== lati.size() && lati.size()==longti.size()){
                for(int i=0; i< lati.size(); i++){
                    locations.add(new LatLng(Double.parseDouble(lati.get(i)), Double.parseDouble(longti.get(i))) );
                }
            }
        }
        //places = new ArrayList<>();
        places.add("Add a new place");
        locations = new ArrayList<>();
        locations.add(new LatLng(0,0));

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Memo.this, MapsActivity.class);
                intent.putExtra("placeNumber", i);

                startActivity(intent);

            }
        });




    }
    public void startTracking(View view) {
        startActivity(new Intent(Memo.this, CountTime.class));
    }

}
