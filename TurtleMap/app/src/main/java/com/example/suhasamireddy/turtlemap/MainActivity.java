package com.example.suhasamireddy.turtlemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        
        JSONObject firstPageEvents = getJSONEventsForPage("335859529840593");
        System.out.println(firstPageEvents.getJSONArray());
    }
    public void startMapActivity(View view){
        Intent intent = new Intent(this,MapsActivity.class);//Will have some parameters to start a new activity
        startActivity(intent);
    }
    
    public JSONObject getJSONEventsForPage(String pageID){
    
		/* make the API call */
		new GraphRequest(
			AccessToken.getCurrentAccessToken(),
			pageID,
			null,
			HttpMethod.GET,
			new GraphRequest.Callback() {
				public void onCompleted(GraphResponse response) {
				
					String responseString = response.toString();
					
					JSONObject inside = new JSONObject(responseString);
					return inside;
				}
			}
		).executeAsync();
		
	}
}
