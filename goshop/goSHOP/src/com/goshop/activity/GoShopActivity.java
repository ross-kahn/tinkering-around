package com.goshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.goshop.R;
import com.goshop.adapter.GoShopAdapter;
import com.goshop.data.Category;
import com.goshop.data.Item;
import com.goshop.data.ListItem;

public class GoShopActivity extends Activity {
	private static int ADD_CATEGORY_REQUEST_CODE = 12354;
	private static int EDIT_ITEM_REQUEST_CODE = 54321;
	private static int EDIT_CATEGORY_REQUEST_CODE = 34251;
	public static String DATA_MODEL = "datamodeler";

	private GoShopAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_go_shop);
	   
       ListView shoppingList = (ListView) findViewById(R.id.shopping_list_view);
       Spinner categoryList = (Spinner) findViewById(R.id.category_list);
              
       adapter = new GoShopAdapter(this);
       
       // For changing the current category with a click
       shoppingList.setOnItemClickListener(new OnItemClickListener() {
    	      public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
  
			   	  // On Category selection
			   	  if( adapter.isSelectedListItemCategory(position)){
				   
			   		  Spinner categoryList = (Spinner) findViewById(R.id.category_list);
				   
			   		  int catIndex = adapter.getCategoryIndexFromFlatIndex(position);
				   
			   		  categoryList.setSelection(catIndex, true);
				   
				   }else{
					  return;  // On item selection
				   }
    	      }                 
    	});
       
       shoppingList.setAdapter(adapter.getShoppingAdapter());
       categoryList.setAdapter(adapter.getCategoryAdapter());
       
       registerForContextMenu(shoppingList);
      
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_go_shop, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selectio
    	Intent intent;
        switch (item.getItemId()) {
            case R.id.addCategory1:
            	intent = new Intent(this, ListItemActivity.class);
            	intent.putExtra(ListItemActivity.EDIT_INTENTION, ListItemActivity.ADD_CATEGORY_INTENT);
            	startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE);
                return true;
            case R.id.clearList:
            	adapter.clearData();
            	return true;
            case R.id.categoryManager:
            	intent = new Intent(this, CategoryManagerActivity.class);
            	startActivity(intent);
            	return true;
            case R.id.clearChecked:
            	clearCheckedItems();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        
        // Add a completely new Category
        if (resultCode == RESULT_OK && requestCode == ADD_CATEGORY_REQUEST_CODE) {
        	
        	addQuickCategory(data);       	
        	Toast toast = Toast.makeText(this, "Category Added", Toast.LENGTH_SHORT);
        	toast.show();        	
        	
        // Edit a previously existing Category
        }else if(resultCode == RESULT_OK && requestCode == EDIT_CATEGORY_REQUEST_CODE){
        	
        	addQuickCategory(data); 
        	Toast toast = Toast.makeText(this, "Category Edited", Toast.LENGTH_SHORT);
        	toast.show();
       
        // Edit a previously existing Item
        }else if(resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE){
        	
        	addQuickItem(data);
        	Toast toast = Toast.makeText(this, "Item Edited", Toast.LENGTH_SHORT);
        	toast.show();
        	
        // The request code is invalid
        }else if(resultCode == RESULT_CANCELED){
        	Toast toasty = Toast.makeText(this, "Addition Canceled", Toast.LENGTH_SHORT);
    		toasty.show();
        }else{
        	Toast toasty = Toast.makeText(this, "Request Code Invalid: " + requestCode, Toast.LENGTH_LONG);
    		toasty.show();
        }
    }
    
    private void addQuickItem(Intent data){
    	int oldPosition = data.getIntExtra(ListItemActivity.CURRENT_POSITION_ID, -1);
    	if(oldPosition < 0){
    		Toast toasty = Toast.makeText(this, "Edited Item Position Invalid", Toast.LENGTH_LONG);
    		toasty.show();
    		return;
    	}
    	
    	String newName = data.getStringExtra(ListItemActivity.ADDED_NAME_ID);
    	adapter.editItem(newName, oldPosition);
    }
    
    private void addQuickCategory(Intent data){
    	
    	String newCategoryName = data.getStringExtra(ListItemActivity.ADDED_NAME_ID);
    	int color = data.getIntExtra(ListItemActivity.CATEGORY_COLOR_ID, Color.BLACK);
    	int oldPosition = data.getIntExtra(ListItemActivity.CURRENT_POSITION_ID, -1);
    	    	
    	if( oldPosition < 0){
    		adapter.addCategory(newCategoryName, color);
    	}else{
    		adapter.editCategory(newCategoryName, color, oldPosition);
    	}
    	
    	Spinner catList = (Spinner) findViewById(R.id.category_list);
    	catList.setSelection(adapter.getNumberCategories() - 1, true);
    }
    
    private void clearCheckedItems(){
    	adapter.clearCheckedItems();
    }
    
   public void addQuickItem(View view){
    	EditText editText = (EditText) findViewById(R.id.quick_add_text);
    	Spinner categoryList = (Spinner) findViewById(R.id.category_list);
    	
    	String itemName = editText.getText().toString();
    	int categoryPosition = categoryList.getSelectedItemPosition();
    	
    	//TODO It's technically fine if the category name is null, just add it to default
    	if(categoryPosition < 0) {
	    	Toast butter = Toast.makeText(this, "Could not add Item, no category found.", Toast.LENGTH_LONG);
	    	butter.show();
    	} else {
    		
    		if( itemName.isEmpty() ){
    			Toast toast = Toast.makeText(this, "Enter Item Name Before Adding", Toast.LENGTH_SHORT);
    			toast.show();
    			return;
    		}
	    	
    		adapter.addItem(itemName, categoryPosition);
	    	
	    	editText.setText("");
	    	int duration = Toast.LENGTH_SHORT;
	
	    	Toast toast = Toast.makeText(this, "Item Added", duration);
	    	toast.show();
    	}
    }   
   
   public void removeQuickItem(View view) {
	   //TextView itemName = (TextView) findViewById(R.id.item_name);
	   ListView listView = (ListView) findViewById(R.id.shopping_list_view);
	   
	   int pos = listView.getPositionForView(view);
	  
	   adapter.removeItem(pos);
   }
   
   public void toggleCheckbox(View view){
	   
	   ListView listView = (ListView) findViewById(R.id.shopping_list_view);
	   int pos = listView.getPositionForView(view);
	   
	   adapter.checkItem(pos);
   }
   
//  public void onPause() {
//	   super.onPause();
//	   
//	   adapter.save(getApplicationContext());
//   }
   public void onResume(){
	   super.onResume();
	   adapter.refreshAdapterData();
   }
   
   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	   super.onCreateContextMenu(menu, v, menuInfo);
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.list_menu, menu);
   }
   
   @Override
   public boolean onContextItemSelected(MenuItem item) {
       AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
       switch (item.getItemId()) {
           case R.id.menuitem_edit:
               return editSelected((int) info.id);
           default:
               return super.onContextItemSelected(item);
       }
   }
    
   private boolean editSelected(int pos){
	   ListItem toEdit = adapter.getListItem(pos);
	   
	   if(toEdit == null){
		   return false;
	   }
	   
	   Intent intent;
	   
	   if(toEdit instanceof Category){
		   Category catToEdit = (Category) toEdit;
		   intent = new Intent(this, ListItemActivity.class);
		   intent.putExtra(ListItemActivity.EDIT_INTENTION, ListItemActivity.EDIT_CATEGORY_INTENT);
		   intent.putExtra(ListItemActivity.CURRENT_NAME_ID, toEdit.getName());
		   intent.putExtra(ListItemActivity.CURRENT_COLOR_ID, catToEdit.getColor());
		   intent.putExtra(ListItemActivity.CURRENT_POSITION_ID, pos);
		   startActivityForResult(intent, EDIT_CATEGORY_REQUEST_CODE);
	   }else{
		   intent = new Intent(this, ListItemActivity.class);
		   intent.putExtra(ListItemActivity.EDIT_INTENTION, ListItemActivity.EDIT_ITEM_INTENT);
		   intent.putExtra(ListItemActivity.CURRENT_NAME_ID, toEdit.getName());
		   intent.putExtra(ListItemActivity.CURRENT_POSITION_ID, pos);
		   startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
	   }
	   return true;
   }
   
}
