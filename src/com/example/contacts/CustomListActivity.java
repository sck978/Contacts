package com.example.contacts;

import java.util.ArrayList;

import com.bluecoppertech.database.DBHelper;
import com.example.contacts.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CustomListActivity extends Activity {
	
	ListView listview;
	ListView customlist;
	
	Integer[] image;
	Contacts[] contacts;
	DBHelper mydb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_customlist);
	    mydb=new DBHelper(this);
	    
	    ArrayList<Contacts> tmpContact=mydb.getAllContacts();
	    if(tmpContact.size()==0)
	    	Toast.makeText(getApplicationContext(), "No Contacts to display", Toast.LENGTH_LONG).show();
	    else {
	    	
	    	tmpContact=mydb.getAllContacts();
		    contacts=tmpContact.toArray(new Contacts[tmpContact.size()]);
		    
		    if(contacts!=null)
		    	image=new Integer[contacts.length];
		    for(int i=0;contacts!=null && i<contacts.length;i++) {
		    	image[i]=new Integer(R.drawable.image);
		    	CustomListAdapter adapter=new CustomListAdapter(this, tmpContact);
		    	customlist=(ListView) findViewById(R.id.custom_list);
		    	customlist.setAdapter(adapter);
		    }
		    
		    customlist.setOnItemClickListener(new OnItemClickListener(){
		    	@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						    		
		    		Intent detailintent=new Intent(getBaseContext(), ContactDetails.class);
		    		detailintent.putExtra("name", contacts[position].getName());
					startActivity(detailintent);
		    		
		    	}
		    });
	    }
	    
	    
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.customlist, menu);
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
				
			case R.id.add_contact:
				startActivity(new Intent(getBaseContext(), MainActivity.class));
				return true;
				
			case R.id.del_all_contacts:
				final Context context=this;
				final Dialog dialog=new Dialog(context);
				dialog.setContentView(R.layout.alert_dialog);
				Button yes=(Button)dialog.findViewById(R.id.yes);
				
								
				yes.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "Yes button has been clicked", Toast.LENGTH_SHORT).show();
						dialog.cancel();
						mydb=new DBHelper(context);
						mydb.deleteAllContacts();
						Toast.makeText(getApplicationContext(), "All contacts deleted", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getBaseContext(), CustomListActivity.class));
						
					}
					
				});
				Button no=(Button)dialog.findViewById(R.id.no);
				no.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "No button has been clicked", Toast.LENGTH_SHORT).show();
						dialog.cancel();
						
						startActivity(new Intent(getBaseContext(), CustomListActivity.class));
						
					}
				});
				dialog.show();
				
				return true;
		
		}
		return super.onOptionsItemSelected(item);
	}

}
