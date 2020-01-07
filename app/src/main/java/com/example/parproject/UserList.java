package com.example.parproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    Button button;

    public void getPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode ==1){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                getPhoto();;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.share){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else{
                    getPhoto();
                }
            }else{
                getPhoto();
            }

        }else if(item.getItemId() == R.id.logout){

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode ==RESULT_OK && data !=null){
            Uri selectImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectImage);
                Log.i("Photo", "Recieved");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                ParseFile file = new ParseFile("image.png", bytes);
                ParseObject object = new ParseObject("Image");
                object.put("image", file);
                object.put("username", ParseUser.getCurrentUser().getUsername());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(UserList.this, "Image Shared", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UserList.this, "Image not shared try later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        final ArrayList<String> usernames = new ArrayList<>();
        final ListView userList = (ListView) findViewById(R.id.userList);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserFeed.class);
                intent.putExtra("username", usernames.get(position));
                startActivity(intent);
            }
        });
        Intent time = getIntent();
        if(time.getStringExtra("Time")!=null){
            String timer = time.getStringExtra("Time") ;
            TextView findTime = findViewById(R.id.runTime);
            findTime.setText(timer);
            Log.i("Timeeeeee", timer);
        }
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if( e== null){
                    if(list.size()>0){
                        for(ParseUser user: list){
                            usernames.add(user.getUsername());
                        }
                        userList.setAdapter(arrayAdapter);
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
        button = findViewById(R.id.memoPlace);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent theintent = new Intent(UserList.this, Memo.class);
                startActivity(theintent);
            }
        });

    }
    public void goActivity (View view){
        Intent intent = new Intent (this, Memo.class);
        startActivity(intent);
    }
}
