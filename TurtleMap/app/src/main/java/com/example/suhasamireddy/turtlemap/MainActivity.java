package com.example.suhasamireddy.turtlemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.facebook.FacebookSdk;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
    public void startMapActivity(View view){
        Intent intent = new Intent(this,MapsActivity.class);//Will have some parameters to start a new activity
        startActivity(intent);
    }
}
