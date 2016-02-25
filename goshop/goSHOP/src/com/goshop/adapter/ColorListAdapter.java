/**
 * 
 */
package com.goshop.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.goshop.R;

/**
 * @author Ross
 *
 */
public class ColorListAdapter extends ArrayAdapter<String>{

	private Context context;
	private String[] colors;
	
	public ColorListAdapter(Context context){
		super(context, android.R.layout.simple_spinner_item);
		
		this.context = context;
		
		Resources res = context.getResources();
		colors = res.getStringArray(R.array.valid_colors);
		
		addAll(colors);
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    View rowView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
	    TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
	    
		int color = Color.parseColor(getItem(position));
		
		textView.setTextSize(24);
		
		textView.setText("");
		 
		rowView.setBackgroundColor(color);
	    
	    return rowView;
	}
	
	public int findPositionByColor(int color){
		
		for(int i=0; i<colors.length; i++){
			int curColor = Color.parseColor(getItem(i));
			if( curColor == color){
				return i;
			}
			System.out.println("Color: " + curColor);
		}
		return -1;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent){
		return getView(position, convertView, parent);
	}
}
