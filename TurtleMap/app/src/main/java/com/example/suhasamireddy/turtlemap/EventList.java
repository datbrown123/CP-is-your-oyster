package com.example.suhasamireddy.turtlemap;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.GregorianCalendar;


public class EventList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        TextView view[] = new TextView[MainActivity.events.size()];
        for(int i = 0; i< view.length; i++){
            view[i] = new TextView(this);
        }
        for(int i =0; i< view.length;i++){
            String time = MainActivity.events.get(i).time;
            String[] dateTime = time.split(" ");
            String[] date = dateTime[0].split("-");
            String[] clock = dateTime[1].split(":");
            GregorianCalendar currTime = new GregorianCalendar();
            GregorianCalendar eventTime = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]),
                    Integer.parseInt(clock[0]),Integer.parseInt(clock[1]),Integer.parseInt(clock[2]));
            if(eventTime.compareTo(currTime) > 0 && MainActivity.events.get(i).latitude != 0) {
                view[i].append(MainActivity.events.get(i).eventName + "\n" +
                        MainActivity.events.get(i).time + "\n" + MainActivity.events.get(i).description +
                        "\n ________________________________________________\n");
                layout.addView(view[i]);
            }
        }
    }
    public void returnMap(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
}
