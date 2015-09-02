package com.bluecoppertech.database;

import java.util.ArrayList;

import com.example.contacts.R;
import com.example.contacts.Contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME="ContactDB.db";
	public static final String CONTACTS_TABLE_NAME="contacts";
	public static final String CONTACTS_COLUMN_ID="id";
	public static final String CONTACTS_COLUMN_NAME="name";
	public static final String CONTACTS_COLUMN_EMAIL="email";
	public static final String CONTACTS_COLUMN_ADDRESS="address";
	public static final String CONTACTS_COLUMN_BIRTHDATE="birthdate";
	public static final String CONTACTS_COLUMN_PHONE="phone";
	public static final String CONTACTS_CIRCLE="circle";
	public static final String CONTACTS_COLUMN_GENDER="gender";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table contacts "+"(id integer primary key, name text, phone text, email text, address text, birthdate text, circle text, gender text)");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS contacts");
		onCreate(db);
	}
	
	public boolean insertContact(String name, String phone, String email, String address, String birthdate, String circle, String gender) {
		SQLiteDatabase db=this.getWritableDatabase();
		
		ContentValues contentValues=new ContentValues();
		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("address", address);
		contentValues.put("birthdate", birthdate);
		contentValues.put("circle", circle);
		contentValues.put("gender", gender);
		db.insert("contacts", null, contentValues);
		return true;
	}
	
	public Cursor getData(String name) {
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor res=db.rawQuery("SELECT * FROM contacts WHERE "+CONTACTS_COLUMN_NAME+"='"+name+"'", null);
		return res;
	}
	
	public Contacts getContactDetails(String name) {
		Contacts tmpContact=null;
		Cursor c=getData(name);
		c.moveToFirst();
		if(c!=null) {
			String names=c.getString(c.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
			String phno=c.getString(c.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
			String address=c.getString(c.getColumnIndex(DBHelper.CONTACTS_COLUMN_ADDRESS));
			String email=c.getString(c.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
			String circle=c.getString(c.getColumnIndex(DBHelper.CONTACTS_CIRCLE));
			String birthdate=c.getString(c.getColumnIndex(DBHelper.CONTACTS_COLUMN_BIRTHDATE));
			String gender=c.getString(c.getColumnIndex(DBHelper.CONTACTS_COLUMN_GENDER));
			tmpContact=new Contacts(names, phno, address, email, circle, R.drawable.image, birthdate, gender);
			if(!c.isClosed()) {
				c.close();
			}
		}
		return tmpContact;
	}
	
	public int numberOfRows() {
		SQLiteDatabase db=this.getReadableDatabase();
		int numRows=(int)DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
		return numRows;
	}
	
	public boolean updateContact(String name, String phone, String email, String address, String birthdate, String circle, String gender) {
		SQLiteDatabase db=this.getWritableDatabase();
		
		ContentValues contentValues=new ContentValues();
		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("address", address);
		contentValues.put("birthdate", birthdate);
		contentValues.put("circle", circle);
		contentValues.put("gender", gender);
		db.update("contacts", contentValues, "name=?", new String[] { name });
		return true;
	}
	
	public Integer deleteContact(String name) {
		SQLiteDatabase db=this.getWritableDatabase();
		return db.delete("contacts", "name=?", new String[] { name });
	}
	
	public ArrayList<Contacts> getAllContacts() {
		ArrayList<Contacts> array_list=new ArrayList<Contacts>();
		
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor res=db.rawQuery("SELECT * FROM contacts", null);
		res.moveToFirst();
		
		while(res.isAfterLast()==false) {
			array_list.add(new Contacts(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))));
			res.moveToNext();
		}
		return array_list;
	}
	
	public void deleteAllContacts() {
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS contacts");
		onCreate(db);
	}
	
}
