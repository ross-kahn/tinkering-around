/**
 * 
 */
package com.goshop.data;

import java.util.List;

import android.content.Context;


/**
 * This is the interface that the controller for Go SHOP must adhere too.
 * 
 * @author Charles Lander (cjl1750@rit.edu)
 *
 */
public interface DataModelInterface {
	
	/**
	 * 
	 * @param toAdd the category to add
	 * @return if the category was added
	 */
	public boolean addCategory(String categoryName);
	
	/**
	 * 
	 * @param categoryName
	 * @param color
	 * @return
	 */
	public boolean addCategory(String categoryName, int color);
	
	/**
	 * 
	 * @param toRemove the category to remove
	 * @return if the category was removed
	 */
	public boolean removeCategory(int categoryPosition);
	
	/**
	 * 
	 * @param toEdit the category to edit
	 * @param newName the new name of the category
	 * @return if the category was edited
	 */
	public boolean editCategory(int oldPosition, Category newCat);
	
	/**
	 * 
	 * @param item to add
	 * @param toAdd the category to add it to
	 * @return if it was added
	 */
	public boolean addItem(String itemName, int categoryToAdd);
	
	public boolean removeItem(int positionInShoppingList);
	
	
	/**
	 * 
	 * @param itemFlatPosition
	 * @param categoryIndex
	 * @param newItemName
	 * @return
	 */
	public boolean editItem(int oldPosition, Item newItem);
	
	/**
	 * 
	 * @return An ordered list of a category followed by its items, followed
	 * 		   by the next category, etc.
	 */
	public List<ListItem> getShoppingList();
	
	public List<Category> getCategories();

	public boolean checkItem(int position);
	
	public void deleteCheckedItems();
	
	public void deleteData();
	
	public void save(Context ctx);
	
	public void load(Context ctx);

	
}
