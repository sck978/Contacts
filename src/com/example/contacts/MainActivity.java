package com.example.contacts;

import java.util.Calendar;

import com.bluecoppertech.database.DBHelper;
import com.example.contacts.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	final Context context=this;
	EditText name,address,phoneNumber,email;
	Spinner gender;
	RadioGroup rdgCircle;
	RadioButton rdFriend, rdFamily, rdOther;
	Button btn_sb,btn_cn;
		
	private Calendar calendar;
	private TextView dateView;
	private int year, month, day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		name=(EditText)findViewById(R.id.editText1);
		phoneNumber=(EditText)findViewById(R.id.editText2);
		address=(EditText)findViewById(R.id.editText3);
		email=(EditText)findViewById(R.id.editText4);
		
		gender=(Spinner)findViewById(R.id.spinner);
		
		rdgCircle=(RadioGroup)findViewById(R.id.radioGroup1);
		rdFamily=(RadioButton)findViewById(R.id.radioButton1);
		rdFriend=(RadioButton)findViewById(R.id.radioButton2);
		rdOther=(RadioButton)findViewById(R.id.radioButton3);
		
		btn_sb=(Button)findViewById(R.id.button1);
		btn_cn=(Button)findViewById(R.id.button2);
		
		dateView=(TextView)findViewById(R.id.button3);
		calendar=Calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		
		month=calendar.get(Calendar.MONTH);
		day=calendar.get(calendar.DAY_OF_MONTH);
		showDate(year, month+1, day);
		
	
		rdOther.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked){
					Toast.makeText(getApplicationContext(), "You selected Others group", Toast.LENGTH_SHORT).show();
				}
			}
		});
	
		btn_cn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Contact not saved", Toast.LENGTH_SHORT).show();
				reset();		
				startActivity(new Intent(getBaseContext(), MainActivity.class));
			}
		});
		btn_sb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((name.getText().toString()!=null && !name.getText().toString().equals(""))
						&&(phoneNumber.getText().toString()!=null && !phoneNumber.getText().toString().equals(""))
						&&(address.getText().toString()!=null && !address.getText().toString().equals(""))
						&&(email.getText().toString()!=null && !email.getText().toString().equals(""))){
					
					if((phoneNumber.getText().toString()).length()!=10) {
						Toast.makeText(getApplicationContext(), "Invalid phone no.", Toast.LENGTH_SHORT).show();
						return ;
					}
					String circle = null;
					if(rdgCircle.getCheckedRadioButtonId()==R.id.radioButton1){
						circle = "Friends";
					}
					else if(rdgCircle.getCheckedRadioButtonId()==R.id.radioButton2){
						circle = "Family";
					}
					else if(rdgCircle.getCheckedRadioButtonId()==R.id.radioButton3){
						circle = "Others";
					}
					else {
						Toast.makeText(getApplicationContext(), "All the fields not filled up", Toast.LENGTH_SHORT).show();
						return ;
					}
					
					final String circle2=circle;
					
					final String gen=gender.getSelectedItem().toString();
					
					final Dialog dialog=new Dialog(context);
					dialog.setContentView(R.layout.custom_dialog);
					Button yes=(Button)dialog.findViewById(R.id.yes);
					
					
					yes.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(), "Yes button has been clicked", Toast.LENGTH_SHORT).show();
							dialog.cancel();
							DBHelper mydb=new DBHelper(context);
							mydb.insertContact(name.getText().toString(), phoneNumber.getText().toString(), email.getText().toString(), address.getText().toString(), dateView.getText().toString(), circle2, gen);
							Toast.makeText(getApplicationContext(), "Congratulations! Contact of "+name.getText().toString()+" is saved successfully in "+circle2+" group.", Toast.LENGTH_LONG).show();
							startActivity(new Intent(getBaseContext(), CustomListActivity.class));
						}
					});
					Button no=(Button)dialog.findViewById(R.id.no);
					no.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(), "No button has been clicked", Toast.LENGTH_SHORT).show();
							dialog.cancel();
								
						}
					});
					dialog.show();
				}
				else {
					Toast.makeText(getApplicationContext(), "All the fields not filled up", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public void setDate(View view) {
		showDialog(999);
		Toast.makeText(getApplicationContext(), "Change the date", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		if(id==999) {
			return new DatePickerDialog(this, myDateListener, year, month, day);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener myDateListener=new DatePickerDialog.OnDateSetListener() {
			
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2,
				int arg3) {
			//arg1=year;
			//arg2=month;
			//arg3=day;
			showDate(arg1, arg2+1, arg3);
			
		}
	};
	
	private void showDate(int year, int month, int day) {
		String mm=String.format("%02d",month);
		String dd=String.format("%02d",day);
		String yyyy=String.format("%04d",year);
		dateView.setText(new StringBuilder().append(mm).append("/").append(dd).append("/").append(yyyy));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
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

	private void reset() {
		name=phoneNumber=address=email=null;
		startActivity(new Intent(getBaseContext(), CustomListActivity.class));
	}
}
