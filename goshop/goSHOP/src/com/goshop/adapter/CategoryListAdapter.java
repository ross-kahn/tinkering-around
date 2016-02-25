/**
 * 
 */
package com.goshop.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goshop.R;
import com.goshop.data.Category;
import com.goshop.data.DataModelInterface;
import com.goshop.data.ListItem;

/**
 * @author Ross
 *
 */
public class CategoryListAdapter extends ArrayAdapter<ListItem>{

	protected DataModelInterface data;
	protected Context context;
	protected List<Category> categories;
	
	public CategoryListAdapter(Context context, DataModelInterface data) {
		super(context, R.layout.category_spinner);
		this.data = data;
		this.context = context;
		refreshData();
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent){

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    View rowView = inflater.inflate(R.layout.category_spinner, parent, false);
	    
	    TextView textView = (TextView) rowView.findViewById(R.id.cat_spinner_text);
	    
	    // Find a background color to distinguish it as a dropdown
	    //textView.setBackgroundColor(Color.GRAY);
	    
	    Category curCategory = categories.get(position);
	    textView.setText(curCategory.getName());
	    textView.setTextColor(curCategory.getColor());
	    textView.setTextSize(36);
	    
	    return rowView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    View rowView = inflater.inflate(R.layout.category_spinner, parent, false);
	    
	    ImageView catImage = (ImageView) rowView.findViewById(R.id.cat_spinner_image);
	    ImageView dropImage = (ImageView) rowView.findViewById(R.id.cat_spinner_dropImage);
	    TextView textView = (TextView) rowView.findViewById(R.id.cat_spinner_text);
	    
	    Category curCategory = categories.get(position);
	    textView.setText(curCategory.getName());
	    textView.setTextColor(curCategory.getColor());
	    textView.setTextSize(36);
	    
	    catImage.setVisibility(View.GONE);
	    dropImage.setVisibility(View.GONE);
		return rowView;
	}
	
	public void refreshData(){
		categories = data.getCategories();
		
		super.clear();
		super.addAll(categories);
		super.notifyDataSetChanged();
	}
	
	public void removeCategory(String name) {
		for(int i = 0; i < categories.size(); i++ ) {
			if(categories.get(i).getName().equals(name)) {
				Category cat = categories.remove(i);
				this.remove(cat);
				return;
			}
		}
	}
	
	public int findCategoryIndexFromName(String categoryName){
		
		for( int i=0; i<categories.size(); i++){
			if( categoryName.equals(categories.get(i).getName())){
				return i;
			}
		}
		return -1;
	}

}
