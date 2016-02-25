/**
 * 
 */
package com.goshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.goshop.R;
import com.goshop.adapter.GoShopAdapter;

/**
 * @author Ross
 *
 */
public class CategoryManagerActivity extends Activity{

	GoShopAdapter adapter;
	private static int ADD_CATEGORY_REQUEST_CODE = 98765;
	private static int EDIT_CATEGORY_REQUEST_CODE = 98764;

	
	 public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.category_manager);
		   
		   adapter = new GoShopAdapter(this);
		   
	       ListView categoryList = (ListView) findViewById(R.id.listview_categoryManager);
	              
	       categoryList.setAdapter(adapter.getCategoryManagerAdapter());
	 }
	
	 
	 public void removeQuickItem(View view){
		 ListView catManagerList = (ListView) findViewById(R.id.listview_categoryManager);
		 
		 int categoryPosition = catManagerList.getPositionForView(view);
		 
		 adapter.removeCategory(categoryPosition);
	 }
	 
		
	public void addCategory(View view){
		Intent intent = new Intent(this, ListItemActivity.class);
		intent.putExtra(ListItemActivity.EDIT_INTENTION, ListItemActivity.ADD_CATEGORY_INTENT);
		startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_CATEGORY_REQUEST_CODE) {
        	
        	String newCategoryName = data.getStringExtra(ListItemActivity.ADDED_NAME_ID);
        	int color = data.getIntExtra(ListItemActivity.CATEGORY_COLOR_ID, Color.BLACK);
        	
        	adapter.addCategory(newCategoryName, color);
        	
        	Toast toast = Toast.makeText(this, "Category Added", Toast.LENGTH_SHORT);
        	toast.show();

        } 
    }
	
//	public void onPause() {
//		super.onPause();
//		adapter.save(getApplicationContext());
//	}
//	
//	public void onResume() {
//		super.onResume();
//		adapter.load(getApplicationContext());
//	}
}
