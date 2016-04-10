package com.example.suhasamireddy.turtlemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

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

import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.5f ) );
        /*Testing marker 1*/
        LatLng coords[] = new LatLng[MainActivity.events.size()];
        for(int i = 0; i < coords.length; i++){
            coords[i] = new LatLng(MainActivity.events.get(i).latitude, MainActivity.events.get(i).longitude);
            mMap.addMarker(new MarkerOptions().position(coords[i]).title("Name: " + MainActivity.events.get(i).eventName +
             " " + MainActivity.events.get(i).latitude + " " + MainActivity.events.get(i)));
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
