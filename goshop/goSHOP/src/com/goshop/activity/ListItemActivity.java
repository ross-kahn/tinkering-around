package com.goshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goshop.R;
import com.goshop.adapter.ColorListAdapter;

public class ListItemActivity extends Activity{
	public static final String ADDED_NAME_ID = "addedname";
	public static final String CATEGORY_COLOR_ID = "categorycolor";
	
	public static final String CURRENT_NAME_ID = "curname";
	public static final String CURRENT_COLOR_ID = "curcolor";
	
	public static final String CURRENT_POSITION_ID = "curpos";
	
	public static final String EDIT_INTENTION = "editlistitem";
	
	public static final int EDIT_CATEGORY_INTENT = 0;
	public static final int ADD_CATEGORY_INTENT = 1;
	public static final int EDIT_ITEM_INTENT = 2;
	
	private ColorListAdapter adapter;
	private int intention;
	
	private String curName = "";
	private int curColor = Color.BLACK;
	private int curPos = -1;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.edit_listitem);
		 
		 Intent intent = getIntent();
		 
		 intention = intent.getIntExtra(EDIT_INTENTION, 1);
		 
		 
	
		 switch (intention){
			 case EDIT_CATEGORY_INTENT:
				 curName = intent.getStringExtra(CURRENT_NAME_ID);
				 curColor = intent.getIntExtra(CURRENT_COLOR_ID, Color.BLACK);
				 curPos = intent.getIntExtra(CURRENT_POSITION_ID, -1);
				 if(curPos >= 0){
					 createAddCategory(true);
					 break; 
				 }				 
			 case ADD_CATEGORY_INTENT:
				 createAddCategory(false);
				 break;
			 case EDIT_ITEM_INTENT:
				 curName = intent.getStringExtra(CURRENT_NAME_ID);
				 curPos = intent.getIntExtra(CURRENT_POSITION_ID, -1);
				 if(curPos >= 0){
					 createEditItem();;
					 break; 
				 }	
			 default: 
				 Toast errorToast = Toast.makeText(this, "Error: Invalid Intention for ListItem Creation", Toast.LENGTH_LONG);
				 errorToast.show();
				 finish();
		 } 
		 
		 
	 }
	 
	 private void createAddCategory(boolean edit){
		 
		 
		 adapter = new ColorListAdapter(this);
		 Spinner spinner = (Spinner) findViewById(R.id.color_selector);
		 spinner.setAdapter(adapter);
		 
		 TextView title = (TextView) findViewById(R.id.edit_listitem_title);
		 TextView editName = (TextView) findViewById(R.id.label_listItemName);

		 editName.setText(getResources().getString(R.string.text_categoryName));
		 
		 if(edit){
			 title.setText(getResources().getString(R.string.btext_editCategory));
			 
			 EditText catName = (EditText) findViewById(R.id.editText_listItemName);

			 catName.setText(curName);
			 catName.setTextColor(curColor);
			 
			 int colorIndex = adapter.findPositionByColor(curColor);
			 
			 if( colorIndex < 0 ){
				 colorIndex = 0;
			 }

			 spinner.setSelection(colorIndex, true);
			 
		 }else{
			 title.setText(getResources().getString(R.string.btext_addCategory));
		 }
		 
		 spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	EditText catName = (EditText) findViewById(R.id.editText_listItemName);
			        catName.setTextColor(Color.parseColor(adapter.getItem(position)));
			    }

			    public void onNothingSelected(AdapterView<?> parentView) {
			        // your code here
			    }

			});
		 
	 }
	 
	 private void createEditItem(){
		 TextView title = (TextView) findViewById(R.id.edit_listitem_title);
		 title.setText(getResources().getString(R.string.btext_editItem));
		 
		 TextView editName = (TextView) findViewById(R.id.label_listItemName);
		 EditText editText = (EditText) findViewById(R.id.editText_listItemName);
		 
		 editName.setText(getResources().getString(R.string.text_itemName));
		 editText.setText(curName);
		 
		 findViewById(R.id.label_chooseColor).setVisibility(View.GONE);
		 findViewById(R.id.color_selector).setVisibility(View.GONE);
	 }

	 private void finishAddCategory(){
		 EditText editText = (EditText) findViewById(R.id.editText_listItemName);
		 Spinner spinner = (Spinner) findViewById(R.id.color_selector);
		 
		 String colorStr = spinner.getSelectedItem().toString();
		 
		 int color = Color.parseColor(colorStr);
		 
		 Intent intent = getIntent();
		 intent.putExtra(ADDED_NAME_ID, editText.getText().toString());
		 intent.putExtra(CATEGORY_COLOR_ID, color);
		 intent.putExtra(CURRENT_POSITION_ID, curPos);
		 setResult(RESULT_OK, intent);
		 finish();
	 }
	 
	 private void finishEditItem(){
		 EditText editText = (EditText) findViewById(R.id.editText_listItemName);
 
		 Intent intent = getIntent();
		 intent.putExtra(ADDED_NAME_ID, editText.getText().toString());
		 intent.putExtra(CURRENT_POSITION_ID, curPos);
		 setResult(RESULT_OK, intent);
		 finish();
	 }
	 
	 public void addListItem(View view){
		 EditText editText = (EditText) findViewById(R.id.editText_listItemName);
		 
		 if( editText.getText().toString().trim().isEmpty()){
			 Toast toasty = Toast.makeText(this, "Please Add a Category Name", Toast.LENGTH_SHORT);
			 toasty.show();
			 return;
		 }
		 
		 switch (intention){
			 case EDIT_CATEGORY_INTENT:
				 finishAddCategory();
				 break;
			 case ADD_CATEGORY_INTENT:
				 finishAddCategory();
				 break;
			 case EDIT_ITEM_INTENT:
				 finishEditItem();
				 break;
			 default: 
				 Toast errorToast = Toast.makeText(this, "Error: Invalid Intention for ListItem Creation", Toast.LENGTH_LONG);
				 errorToast.show();
				 setResult(RESULT_CANCELED, getIntent());
				 finish();
				 break;
		 }
	 }
	 
	 
}

