package com.example.suhasamireddy.turtlemap;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Created by Suhas Amireddy on 4/9/2016.
 */
class MapTask extends AsyncTask<URL, Integer, Long> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Long doInBackground(URL... urls) {

        String fbURL = "https://graph.facebook.com/";
        String[] eventID = {"335859529840593", "6114763231", "16972274487", "32211938844",
                "8510956006", "204730552914910"};
        String search = "/events?access_token=";
        String token ="1553620764938416|25a05f3e6210cd7f3ab547c268b956f9";
        ArrayList<MapEvent> tmp  =  new  ArrayList<MapEvent>();
        for(String id:eventID){
            tmp.addAll(search(fbURL+id+search+token));}
        MainActivity.events = tmp;

        return new Long(0);
    }
    @Override
    protected void onPostExecute(Long result) {
        // set the results in Ui }
    }
    public static ArrayList<MapEvent> search(String webPage) {
        ArrayList<MapEvent> newMap = new ArrayList<MapEvent>();
        try{
            URL url = new URL(webPage);
            URLConnection connection = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());
            JSONArray data = json.getJSONArray("data"); //array of event information
            int i = 0;
            JSONObject event = null;
            while (!data.isNull(i)){
                event = data.getJSONObject(i);
                i++;
                String name = event.getString("name");
                String descrip = event.getString("description");
                String startTime = event.getString("start_time");
                double lat = 0;
                double lon = 0;
                try {
                    if (!event.getJSONObject("place").isNull("location")){ //TODO l
                        lat = event.getJSONObject("place").getJSONObject("location").getDouble("latitude");
                        lon = event.getJSONObject("place").getJSONObject("location").getDouble("longitude");
                    }
                    String place = event.getJSONObject("place").getString("name");
                    newMap.add(new MapEvent(name,descrip,lat,lon,place));
                } catch(org.json.JSONException e) {}
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return newMap;
    }


}
