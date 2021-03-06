package com.example.suhasamireddy.turtlemap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends Activity {
    static ArrayList<MapEvent> events = new ArrayList<MapEvent>();
    static JSONObject value=new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // FacebookSdk.sdkInitialize(getApplicationContext());


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        new MapTask().execute(null, null, null); // lat long


    }
    public void startMapActivity(View view){
        Intent intent = new Intent(this,MapsActivity.class);//Will have some parameters to start a new activity
        startActivity(intent);
    }
    
    //returns a String with Lat and Longitude based on address of location


	//will return a String with all steps concatenated
	String returnDirections(JSONObject steps){
        try {
		String toReturn = "";

		JSONArray allSteps = steps.getJSONArray("html_instructions");
		int size = allSteps.length();

            for (int i = 0; i < size; i++) {
                toReturn += allSteps.getString(i);
            }
            return toReturn;
        }
        catch (Exception e){
            e.printStackTrace();
        }

	    return null;
	}	

    public void getJSONEventsForPage(String pageID){


		/* make the API call */
		new GraphRequest(
			AccessToken.getCurrentAccessToken(),
			pageID+"/events",
			null,HttpMethod.GET,
			new GraphRequest.Callback() {
				public void onCompleted(GraphResponse response) {
				


                    value = response.getJSONObject();
                    Log.w("test",response.getJSONObject().toString());



				}
			}
		).executeAsync();

	}

	

}

class MapTask extends AsyncTask<URL, Integer, Long> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Long doInBackground(URL... urls) {

        String fbURL = "https://graph.facebook.com/";
        String[] eventID = {"335859529840593", "6114763231", "16972274487", "32211938844",
                "8510956006", "204730552914910"}; /*,"170244676346718"*/
        String search = "/events?access_token=";
        String token ="1553620764938416|25a05f3e6210cd7f3ab547c268b956f9";
        ArrayList<MapEvent> tmp  =  new  ArrayList<MapEvent>();
        for(String id:eventID){
            tmp.addAll(search(fbURL+id+search+token));

        }
        MainActivity.events.addAll( tmp);
        String testName1 = MainActivity.events.get(0).eventName;
        double testLat1 = MainActivity.events.get(0).latitude;
        double x = 2;//Everything is retrieved properly  tested with 0 element
        return new Long(0);
    }
    static LatLng getLatLon (String address){

        double latlon[] = new double[2];
        try {
            String toQuery = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address;

            URL url = new URL(toQuery);
            URLConnection connection = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject json = new JSONObject(builder.toString());

            JSONArray loc = json.getJSONObject("results").getJSONObject("geometry").getJSONArray("location");
            latlon[0]=loc.getDouble(0);

            latlon[1]=loc.getDouble(1);
            return new LatLng(latlon[0],latlon[1]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
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
                String date = startTime.split("T")[0] + " "+startTime.split("T")[1].split("-")[0];

                double lat = 0;
                double lon = 0;
                try {
                    if (!event.getJSONObject("place").isNull("location")){ //TODO l
                        lat = event.getJSONObject("place").getJSONObject("location").getDouble("latitude");
                        lon = event.getJSONObject("place").getJSONObject("location").getDouble("longitude");
                    }

                    String place = event.getJSONObject("place").getString("name");
                    newMap.add(new MapEvent(name,descrip,lat,lon,place,date));
                } catch(org.json.JSONException e) {}
            }
        } catch(Exception e){
        }
        return newMap;
    }


}
