package com.example.suhasamireddy.turtlemap;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    static JSONObject value=new JSONObject();
    static TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        txt=(TextView) findViewById(R.id.textView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        new MapTask().execute(null, null, null);


    }
    public void startMapActivity(View view){
        Intent intent = new Intent(this,MapsActivity.class);//Will have some parameters to start a new activity
        startActivity(intent);
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
