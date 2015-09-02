package com.example.contacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluecoppertech.database.DBHelper;

public class ContactDetails extends Activity {

	DBHelper mydb;
	Contacts contacts;
	
	final Context context=this;
	Bundle extras;
	
	TextView name,address,phoneNumber,email,circle,gender,bdayText;
	RadioGroup rdgCircle;
	RadioButton rdFriend, rdFamily, rdOther,select;
	Button edit, delete;
	Button call, text;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details);
		
		name=(TextView)findViewById(R.id.textView12);
		phoneNumber=(TextView)findViewById(R.id.textView22);
		address=(TextView)findViewById(R.id.textView32);
		email=(TextView)findViewById(R.id.textView42);
		gender=(TextView)findViewById(R.id.textView52);
		circle=(TextView)findViewById(R.id.textView62);
		bdayText=(TextView)findViewById(R.id.textView72);
		
		edit=(Button)findViewById(R.id.edit);
		delete=(Button)findViewById(R.id.delete);
		
		call=(Button)findViewById(R.id.call);
		text=(Button)findViewById(R.id.text);
		
		extras=getIntent().getExtras();
		final String extra_name=extras.getString("name");
		mydb=new DBHelper(this);
		contacts=mydb.getContactDetails(extra_name);
		
		if(contacts!=null) {
			name.setText(contacts.getName());
			phoneNumber.setText(contacts.getPhno());
			address.setText(contacts.getAddress());
			email.setText(contacts.getEmail());
			circle.setText(contacts.getCircle());
			gender.setText(contacts.getGender());
			bdayText.setText(contacts.getBirthdate()+"\n(mm/dd/yyyy)");
			
		}
				
		
		call.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				String phnnumber="tel:"+phoneNumber.getText().toString();
				Intent callIntent=new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse(phnnumber));
				startActivity(callIntent);
				
			}
		});
	
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent smsIntent=new Intent(Intent.ACTION_VIEW);
				smsIntent.putExtra("sms_body", "");
				smsIntent.putExtra("address", phoneNumber.getText().toString());
				//smsIntent.setData(Uri.parse("smsto:"+phoneNumber.getText().toString()));
				smsIntent.setType("vnd.android-dir/mms-sms");
				startActivity(smsIntent);
				
			}
		});
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
	    		intent.setClass(getBaseContext(), EditActivity.class);
	    		//intent.putExtra("EXTRA_ID", "SOME_DATAS");
	    		intent.putExtra("name", name.getText().toString());
	    		startActivity(intent);
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog=new Dialog(context);
				dialog.setContentView(R.layout.alert_dialog);
				Button yes=(Button)dialog.findViewById(R.id.yes);
				
								
				yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "Yes button has been clicked", Toast.LENGTH_SHORT).show();
						dialog.cancel();
						mydb=new DBHelper(context);
						mydb.deleteContact(extra_name);
						Toast.makeText(getApplicationContext(), "Contact of "+name.getText().toString()+" is deleted", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getBaseContext(), CustomListActivity.class));
					}
				});
				Button no=(Button)dialog.findViewById(R.id.no);
				no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "No button has been clicked", Toast.LENGTH_SHORT).show();
						dialog.cancel();
						Toast.makeText(getApplicationContext(), "Contact of "+name.getText().toString()+" is not deleted", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getBaseContext(), CustomListActivity.class));
					}
				});
				dialog.show();
				
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
				
		getMenuInflater().inflate(R.menu.contactdetails, menu);
		return true;
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()) {
			case R.id.exit:
			moveTaskToBack(true);
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
					
			case R.id.action_settings:
			return true;
						
			case R.id.custom_list:
			startActivity(new Intent(getBaseContext(), CustomListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}

