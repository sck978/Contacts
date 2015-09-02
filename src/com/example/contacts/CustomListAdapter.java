package com.example.contacts;

import java.util.List;

import com.example.contacts.R;

import android.app.Activity;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	private final Activity context;
	LayoutInflater inflater;
	List<Contacts> customContacts;

	public CustomListAdapter(Activity context, List<Contacts> customcontacts) {
		super();
		this.context=context;
		this.customContacts=customcontacts;		
	}
	
	static class ViewHolder {
		TextView name;
		ImageView imgProfile;
	}
	@Override
	public int getCount() {
		if(customContacts!=null) {
			return customContacts.size();
		}
		return 0;
	}
	@Override
	public Contacts getItem(int position) {
		return null;
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;
		ViewHolder holder=null;
		if(row==null) {
			LayoutInflater inflater=((Activity)context).getLayoutInflater();
			row=inflater.inflate(R.layout.customlistrow, parent, false);
			holder=new ViewHolder();
			holder.name=(TextView)row.findViewById(R.id.name);
			holder.imgProfile=(ImageView)row.findViewById(R.id.image);
			row.setTag(holder);
		}
		else {
			holder=(ViewHolder)row.getTag();
		}
		try {
			holder.name.setText(customContacts.get(position).getName());
			holder.imgProfile.setImageResource(R.drawable.image);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return row;
				
	}
		
}
