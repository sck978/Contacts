package com.example.contacts;

import com.bluecoppertech.database.DBHelper;
import com.example.contacts.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.Activity;

public class SplashActivity extends Activity{
	
	private DBHelper mydb;
	private ProgressBar progressBar;
	int progressStatus=0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		progressBar=(ProgressBar)findViewById(R.id.progressBar);
		setupData();
		Thread splashTimer=new Thread() {
			public void run() {
				try {
						while(progressStatus<100) {
							progressStatus+=1;
							progressBar.setProgress(progressStatus);
							Thread.sleep(20);
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					finish();
					startActivity(new Intent(getBaseContext(), CustomListActivity.class));
			    }
			}
			
		};
		splashTimer.start();
	}
	
	private void setupData() {
		mydb=new DBHelper(SplashActivity.this);
		final String myPREFERENCES="myprefs";
		SharedPreferences sharedpreferences=getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
		boolean isInitialized=sharedpreferences.getBoolean("Initialized", false);
		if(!isInitialized) {
			SharedPreferences.Editor editor=sharedpreferences.edit();
			editor.putBoolean("Initialized", true);
			editor.commit();
			Log.d("Insert : ","Inserting...");
			mydb.insertContact("Ravi", "9548762135", "ravi@bluecoppertech.com", "1/2 ABC Road Kolkata", "06/22/2001", "Friends", "Male");
			mydb.insertContact("Hemant", "4589976526", "hemant@bluecoppertech.com", "5 Kalinga Road Kolkata", "08/21/1960", "Family", "Male");
			Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
