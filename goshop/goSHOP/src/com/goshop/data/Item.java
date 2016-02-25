/**
 * 
 */
package com.goshop.data;

import android.graphics.Color;


/**
 * Data holder for each individual shopping list item.
 * Contains information on the item name
 * @author Ross
 *
 */
public class Item extends ListItem{

	private boolean checked;
	
	public Item(String name){
		super(name, Color.BLACK);
		checked = false;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Item){
			Item mystery = (Item) o;
			return mystery.getName().equals(getName());
		}else{
			return false;
		}
	}
	
	public boolean isChecked(){
		return checked;
	}
	
	public Item setChecked(boolean isChecked){
		checked = isChecked;
		return this;
	}
	
	public void toggleChecked(){
		checked = checked ? false : true;
	}
	
	public String toString(){
		return getName();
	}
}
