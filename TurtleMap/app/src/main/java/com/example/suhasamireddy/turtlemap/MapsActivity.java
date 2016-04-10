package com.example.suhasamireddy.turtlemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONObject;

import java.util.GregorianCalendar;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;   
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        //setSupportActionBar(toolbar);
    }
    public void showEvents(View view){
        Intent events = new Intent(this, EventList.class);
        startActivity(events);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int confirmPermission=0;
          if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            mMap.setMyLocationEnabled(true);
        }else{ // Permissions case will always run, displays a message asking for location permissions
              ActivityCompat.requestPermissions(this,
                      new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                      confirmPermission);
          }
        mMap.setMyLocationEnabled(true);  //assume they always say yes
        // Marker starts in mckeldin
        LatLng mckeldin = new LatLng(38.985882, -76.944845);
        mMap.addMarker(new MarkerOptions().position(mckeldin).title("Mckeldin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mckeldin));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );
        /*Testing marker 1*/
        LatLng coords[] = new LatLng[MainActivity.events.size()];
        for(int i = 0; i < coords.length; i++){
            String time = MainActivity.events.get(i).time;
            String[] dateTime = time.split(" ");
            String[] date = dateTime[0].split("-");
            String[] clock = dateTime[1].split(":");
            GregorianCalendar currTime = new GregorianCalendar();
            GregorianCalendar eventTime = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]),
                    Integer.parseInt(clock[0]),Integer.parseInt(clock[1]),Integer.parseInt(clock[2]));
            if (eventTime.compareTo(currTime) > 0) {
                coords[i] = new LatLng(MainActivity.events.get(i).latitude, MainActivity.events.get(i).longitude);
                mMap.addMarker(new MarkerOptions().position(coords[i]).title("Name: " + MainActivity.events.get(i).eventName +
                        "Time: " + MainActivity.events.get(i).time));
            }
        }
    }
    
    /*
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMarker = mMap.addMarker(new MarkerOptions().position(loc));
            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }*/

    /*
    //draws a blue line between two locations
    private void drawLineBetweenLocs(LatLng origin, LatLng dest){
    	Polyline line = mMap.addPolyline(new PolylineOptions().add(origin,dest)).width(8).color(Color.BLUE));
    }
    
    
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
    
    private */
}
