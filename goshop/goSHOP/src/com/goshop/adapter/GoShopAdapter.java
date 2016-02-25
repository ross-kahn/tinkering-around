/**
 * 
 */
package com.goshop.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.goshop.data.Category;
import com.goshop.data.DataModelInterface;
import com.goshop.data.Item;
import com.goshop.data.ItemManager;
import com.goshop.data.ListItem;

/**
 * @author Ross
 *
 */
public class GoShopAdapter{
	
	private DataModelInterface data; 
	
	private ShoppingListAdapter shoppingList;
	private CategoryListAdapter categoryList;
	private CategoryManagerAdapter categoryManagerList;
	private ColorListAdapter colorList;
	
	public GoShopAdapter(Context context) {	
		
		data = ItemManager.getItemManager(context);
		
		shoppingList = new ShoppingListAdapter(context, data);
		categoryList = new CategoryListAdapter(context, data);
		categoryManagerList = new CategoryManagerAdapter(context, data);
		colorList = new ColorListAdapter(context);
	}
	
	public ArrayAdapter<ListItem> getShoppingAdapter(){
		return shoppingList;
	}
	
	public ArrayAdapter<ListItem> getCategoryAdapter(){
		return categoryList;
	}
	
	public ArrayAdapter<ListItem> getCategoryManagerAdapter(){
		return categoryManagerList;
	}
	
	public ArrayAdapter<String> getColorListAdapter(){
		return colorList;
	}
	
	public boolean addItem(String itemName, int categoryPosition){
		boolean success = data.addItem(itemName, categoryPosition);
		refreshAdapterData(false);
		return success;
	}
	
	public boolean editItem(String itemName, int editItemIndex){
		Item newItem = new Item(itemName);
		boolean success = data.editItem(editItemIndex, newItem);
		refreshAdapterData(false);
		return success;
	}
	
	public boolean editCategory(String catName, int color, int editCatIndex){
		Category newCat = new Category(catName, color);
		boolean success = data.editCategory(editCatIndex, newCat);
		refreshAdapterData();
		return success;
	}
	
	public ListItem getListItem(int flatIndex){
		return shoppingList.getItem(flatIndex);
	}
	
	/**
	 * Find the category index and then remove first instance of that item?
	 * @param flatIndex
	 * @return
	 */
	public boolean removeItem(int flatIndex){
		boolean success = data.removeItem(flatIndex);
		
		refreshAdapterData();

		return success;
	}
	

	public boolean addCategory(String categoryName, int color){
		boolean success = data.addCategory(categoryName, color);
		
		refreshAdapterData();
		return success;
	}
	
	public int getNumberCategories(){
		return categoryList.getCount();
	}
	
	public int getCategoryIndexFromFlatIndex(int flatIndex){
		
		ListItem listItem = shoppingList.getListItemFromFlatIndex(flatIndex);
		int shoppingIndex = flatIndex;
		
		while(listItem instanceof Item){
			System.out.println("\t"+listItem.getName() + " index: " + shoppingIndex);
			shoppingIndex--; 
			if( shoppingIndex < 0){
				return -1;
			}
			listItem = shoppingList.getListItemFromFlatIndex(shoppingIndex);
		}
		
		int catIndex = categoryList.findCategoryIndexFromName(listItem.getName());
		System.out.println("CATEGORY INDEX: " + catIndex);
		
		return catIndex;
		
	}
	
	/**
	 * 
	 * @param refreshCategories		Will refresh the category list if true. Optional because
	 * 								item addition/removal is irrelevant to categories
	 */
	private void refreshAdapterData(boolean refreshCategories){
		shoppingList.refreshData();
		if( refreshCategories ){
			categoryList.refreshData();
			categoryManagerList.refreshData();
		}
	}
	
	public boolean isSelectedListItemCategory(int index){
		return shoppingList.isSelectedListItemCategory(index);
	}
	
	public void refreshAdapterData(){
		refreshAdapterData(true);
	}
	
	public void clearData(){
		data.deleteData();
		refreshAdapterData();
	}
	
	public void checkItem(int flatIndex){
		boolean success = data.checkItem(flatIndex);
		if(!success){
			System.out.println("******ERROR: CATEGORY WAS CHECKED");
		}
		refreshAdapterData(false);
	}

	public boolean removeCategory(int categoryPosition) {

		
		// Find the index of the category in the nested data and just
		// delete it from the nested data. Then refresh data
		
		boolean result = data.removeCategory(categoryPosition);
		
		if( result ){
			refreshAdapterData();
		}

		return result;
	}
	
	public void clearCheckedItems(){
		data.deleteCheckedItems();
		refreshAdapterData();
	}
	
	public void save(Context ctx) {
		data.save(ctx);
	}
	
	public void load(Context ctx) {
		data.load(ctx);
	}
	
}
