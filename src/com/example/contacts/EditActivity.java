package com.example.contacts;

import java.util.Calendar;

import com.bluecoppertech.database.DBHelper;
import com.example.contacts.R;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {

	EditText name,address,phoneNumber,email;
	RadioGroup rdgCircle;
	RadioButton rdFriend, rdFamily, rdOther;
	Spinner gender;
	Button btn_sb,btn_cn;
		
	DBHelper mydb;
	Contacts contacts;
	
	final Context context=this;
	Bundle extras;
	
	private DatePicker datePicker;
	private Calendar calendar;
	private TextView dateView;
	private int year, month, day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		name=(EditText)findViewById(R.id.editText1);
		phoneNumber=(EditText)findViewById(R.id.editText2);
		address=(EditText)findViewById(R.id.editText3);
		email=(EditText)findViewById(R.id.editText4);
		
		gender=(Spinner)findViewById(R.id.spinner);
		
		rdgCircle=(RadioGroup)findViewById(R.id.radioGroup1);
		rdFriend=(RadioButton)findViewById(R.id.radioButton1);
		rdFamily=(RadioButton)findViewById(R.id.radioButton2);
		rdOther=(RadioButton)findViewById(R.id.radioButton3);
		
		btn_sb=(Button)findViewById(R.id.button1);
		btn_cn=(Button)findViewById(R.id.button2);
		
		extras=getIntent().getExtras();
		final String extra_name=extras.getString("name");
		mydb=new DBHelper(this);
		contacts=mydb.getContactDetails(extra_name);
		
		
		if(extras!=null) {
			//String datas=extras.getString("EXTRA_ID");
			String datas1=contacts.getName();
			String datas2=contacts.getPhno();
			String datas3=contacts.getAddress();
			String datas4=contacts.getEmail();
			String datas5=contacts.getCircle();
			//String datas6=contacts.getGender();
			String datas7=contacts.getBirthdate();
			
			if(datas1!=null) {
				name.setText(datas1);
			}
			if(datas2!=null) {
				phoneNumber.setText(datas2);
			}
			if(datas3!=null) {
				address.setText(datas3);
			}
			if(datas4!=null) {
				email.setText(datas4);
			}
			if(datas5!=null) {
				
				if(datas5.equalsIgnoreCase("Friends")) {
					rdFriend.setChecked(true);
				}
				else if(datas5.equalsIgnoreCase("Family")) {
					rdFamily.setChecked(true);
				}
				else if(datas5.equalsIgnoreCase("Others")) {
					rdOther.setChecked(true);
				}
			}
			
						
			if(datas7!=null) {
				dateView=(TextView)findViewById(R.id.button3);
				year=Integer.parseInt(datas7.substring(6));
				day=Integer.parseInt(datas7.substring(3, 5));
				month=Integer.parseInt(datas7.substring(0, 2));
				showDate(year, month, day);
			}
		}
	
		btn_cn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(EditActivity.this, "Changes not saved", Toast.LENGTH_SHORT).show();
				reset();
				Intent detailintent=new Intent(getBaseContext(), ContactDetails.class);
	    		detailintent.putExtra("name", extra_name);
				startActivity(detailintent);
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
					String circle=null;
					if(rdgCircle.getCheckedRadioButtonId()==R.id.radioButton1) {
						circle="Friends";
					}
					else if(rdgCircle.getCheckedRadioButtonId()==R.id.radioButton2) {
						circle="Family";
					}
					else if(rdgCircle.getCheckedRadioButtonId()==R.id.radioButton3) {
						circle="Others";
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
							mydb=new DBHelper(context);
							boolean update=mydb.updateContact(name.getText().toString(), phoneNumber.getText().toString(), email.getText().toString(), address.getText().toString(), dateView.getText().toString(), circle2, gen);
							if(update)	
								Toast.makeText(getApplicationContext(), "Changes made to "+name.getText().toString()+" is saved successfully", Toast.LENGTH_LONG).show();
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
			return new DatePickerDialog(this, myDateListener, year, month-1, day);
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
		
		getMenuInflater().inflate(R.menu.edit, menu);
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
				
		}
		return super.onOptionsItemSelected(item);
	}

	private void reset() {
		name=phoneNumber=address=email=null;
		startActivity(new Intent(getBaseContext(), CustomListActivity.class));
	}
}
