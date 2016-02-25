/**
 * 
 */
package com.goshop.data;

import android.graphics.Color;

/**
 * Data holder for Category name, color, and array of 
 * items
 * @author Ross
 *
 */
public class Category extends ListItem{

	public static final Category DEFAULT_CATEGORY = new Category("General", Color.BLACK);
	
	public Category(String name, int color){
		super(name, color);
		this.setCategory(true);
	}
	
	public Category(String name){
		super(name, Color.BLACK);
	}
	
	public String toString(){
		return getName();
	}
	
	public boolean equals(Object o){
		if( o instanceof Category){
			return ((Category) o).getName().equals(this.getName());
		}else if(o instanceof String){
			return o.equals(this.getName());	
		}else{
			return false;
		}
	}
	
}
