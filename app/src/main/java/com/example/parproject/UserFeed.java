package com.example.parproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeed extends AppCompatActivity {

    Location currentLoaction = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        Intent intent = getIntent();

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        String activeUsername = intent.getStringExtra("username");

        setTitle(activeUsername + "'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username",activeUsername);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e== null){
                    if(list.size()>0){
                        for(ParseObject object: list){

                            ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    if(e==null && bytes!=null){
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        imageView.setImageBitmap(bitmap);

                                        linearLayout.addView(imageView);

                                    }
                                }
                            });

                        }
                    }
                }
            }
        });
//        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                currentLoaction = location;
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        try {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
//        } catch(SecurityException e) {
//            Log.d("Error", e.toString());
//        }
//        TextView text = new TextView(this);
//        text.setText(currentLoaction.toString());
//
//        linearLayout.addView(text);


    }
}
