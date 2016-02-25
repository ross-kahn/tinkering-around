package com.goshop.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.goshop.R;
import com.goshop.data.Category;
import com.goshop.data.DataModelInterface;
import com.goshop.data.Item;
import com.goshop.data.ListItem;

public class ShoppingListAdapter extends ArrayAdapter<ListItem>{

	private DataModelInterface data;
	private Context context;
	private List<ListItem> shoppingList;
	
	public ShoppingListAdapter(Context context, DataModelInterface data){
		super(context, R.layout.goshop_item);
		this.data = data;
		this.context = context;
		shoppingList = data.getShoppingList();
		super.addAll(shoppingList);
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent){
		

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    View rowView = inflater.inflate(R.layout.goshop_item, parent, false);
	    
	    ListItem listItem = shoppingList.get(position);
	    
	    TextView textView = (TextView) rowView.findViewById(R.id.item_name);
	    textView.setText(listItem.getName());
	    Button deleteButton = (Button) rowView.findViewById(R.id.item_delete_button);
	    
	    View divider = rowView.findViewById(R.id.category_border);
	    
	    CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.item_checkbox);
	    
	    if(listItem instanceof Category){
	    	Category curCategory = (Category) listItem;
	    	
	    	divider.setBackgroundColor(curCategory.getColor());
	    	
	    	checkbox.setVisibility(View.GONE);
	    	deleteButton.setVisibility(View.GONE);
	    	
	    	textView.setTextSize(32);
	    	textView.setTextColor(curCategory.getColor());
	    }else{
	    	Item item = (Item) listItem;
	    	checkbox.setChecked(item.isChecked());
	    	
	    	divider.setVisibility(View.GONE);
	    }
	    
	    return rowView;
	}
	
	/**
	 * Takes an index from the flat shopping list, and looks up the ListItem associated
	 * with it.
	 * @param index		Index in flat list
	 * @return			whether ListItem associated with index is of type Category
	 */
	public boolean isSelectedListItemCategory(int index){
		if(index > shoppingList.size() || index < 0) {
			return false;
		}else {
			ListItem selected = shoppingList.get(index);
			return (selected instanceof Category);
		}
	}
	
	public void refreshData(){
		shoppingList = data.getShoppingList();
		super.clear();
		super.addAll(shoppingList);
		super.notifyDataSetChanged();
	}
	
	public ListItem getListItemFromFlatIndex(int index){
		return shoppingList.get(index);
	}
	
}
