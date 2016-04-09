package com.example.suhasamireddy.turtlemap;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
        try{
            URL url = new URL(fbURL+eventID[0]+search+token);
            URLConnection connection = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while((line = reader.readLine()) != null) {
                builder.append(line);
                Log.d("test", line.substring(0,6) );
                // System.out.println(line);
            }

                JSONObject json = new JSONObject(builder.toString());
                JSONArray data = json.getJSONArray("data"); //array of event information
                int i = 0;
                JSONObject event = null;
                //while (!data.isNull(i)){
                    event = data.getJSONObject(i);
                    i++;
                    String name = event.getString("name");
                    String descrip = event.getString("description");
                    try {

                        if (!event.getJSONObject("place").isNull("location")){
                            double lat = event.getJSONObject("place").getJSONObject("location").getLong("latitude");
                            double lon = event.getJSONObject("place").getJSONObject("location").getLong("longitude");
                            try {
                                String x = "" + lat;
                                Log.d("test", x);
                            }
                            catch(Exception e){;
                            }
                            //find (lat,lon); //look through database for similar location
                            //System.out.println(i+":\t(lat,long):\t"+ lat + "\t" + lon);
                            //System.out.println(name);
                            //System.out.println(descrip);
                        } else{
                            String place = event.getJSONObject("place").getString("name");
                            try{
                                String x =place;
                                Log.d("test",x);}
                            catch(Exception e){

                            }
                            //find(place);//look through database for similar location
                            Log.d("test",i+":\t" + place);
                            Log.d("test",name);

                        }
                    } catch(org.json.JSONException e) {}
            // }
            //TODO has lat and long but not in the system
            //has lat and long and close to something close
            //does not have lat and long but has name that's in the database
            // does not have lat and long

        } catch(Exception e){
            // JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return new Long(0);
    }
    @Override
    protected void onPostExecute(Long result) {
        // set the results in Ui }
    }
}
