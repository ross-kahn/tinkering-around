/**ItemManager.java
 * 
 */
package com.goshop.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;



/**
 * This class will be responsible for managing the list of items.
 * 
 * @author Charles Lander (cjl1750@rit.edu)
 *
 */
public class ItemManager implements DataModelInterface{
	private static String FILE_NAME = "goShop.xml";
	private static ItemManager self;
	
	private final int NESTED_CAT_INDEX = 0;				// Categories will always be at index 0 in the nested data
	private final String DEFAULT_CAT_NAME = "Default";
	
	protected List<Category> orderedCategories;
	private LinkedList<ArrayList<ListItem>> nestedData;
	
	/**
	 * Builds the item manager.
	 */
	private ItemManager(Context ctx) {
		orderedCategories = new ArrayList<Category>();
		nestedData = new LinkedList<ArrayList<ListItem>>();
		
		addCategory("Default");
		
		//buildFromXML(ctx);
		makeShoppingList();
		
	}
	
	public static ItemManager getItemManager(Context ctx){
		if(self == null){
			self = new ItemManager(ctx);
		}
		return self;
	}
	
	/**
	 * 
	 * @param item to add
	 * @param addTo the category to add the item to
	 * @return if the item was added successfully 
	 */
	public boolean addItem(String itemName, int categoryIndex) {
		if(itemName != null && !itemName.isEmpty() && categoryIndex >= 0) {
			
			if( categoryIndex < 0 ){ // Category was not found, add to default
				return addToDefault(itemName);
				
			}else{
				
				// Find the category list and add to it
				ArrayList<ListItem> category = nestedData.get(categoryIndex);
				return category.add(new Item(itemName));
			}
			
		} 
		
		return false;	// Either the item name or the category ID was null or empty
	}
	
	public boolean addItem(Item item, String categoryName) {
		for(List<ListItem> list : nestedData) { 
			if(list.get(0).getName().equals(categoryName) && !list.contains(item.getName())) {
				list.add(item);
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * 
	 * @param category to add
	 * @return if the category was added
	 */
	public boolean addCategory(String categoryName) {
		return addCategory(categoryName, Color.BLACK);
	}
	
	public boolean addCategory(String categoryName, int color) {
		
		if(categoryName != null && !categoryName.isEmpty()) {
		
			// If the category already exists, return false
			for( Category c : orderedCategories){
				if( c.getName().equals(categoryName)){
					return false;
				}
			}
			
			// Create the new Category object
			Category newCategory= new Category(categoryName, color);
			
			// Create the nested list, with the new Category as the head
			ArrayList<ListItem> newCategoryList = new ArrayList<ListItem>();
			newCategoryList.add(newCategory);
			
			// add the new nested list to the nested data
			nestedData.add(newCategoryList);
			
			// Rebuild the ordered category list
			buildOrderedCategories();
			return true;
			
		} else {
			return false;		// Category name was null or empty, or the category already exists
		}
		
	}
	
	/**
	 * 
	 * @param category to be removed
	 * @return if the category was removed
	 */
	public boolean removeCategory(int categoryIndex) {
		
		nestedData.remove(categoryIndex);
		buildOrderedCategories();
		
		return true;
	}
	/**
	 * 
	 * @param item to edit
	 * @param newName the new name to give it
	 * @return if the item has been edited successfully. 
	 */
	public boolean editItem(int itemFlatPosition, int categoryIndex, String newItemName) {
		/*item.editName(newName);
		if(item.getName().equals(newName)) {
			return true;
		} else {					IMPLEMENT LATER
			return false;
		}*/
		return false;
	}
	
	/**
	 * 
	 * @param cat the category to edit
	 * @param newName the new name of the category
	 * @return if the category has been edited successfully. 
	 */
	public boolean editCategory(int oldPosition, Category newCat) {
		
		Point nestedPos = flatIndexToNestedIndex(oldPosition);
		int catIndex = nestedPos.x;
		int catPos = nestedPos.y;
		
		ArrayList<ListItem> catList = nestedData.get(catIndex);
		catList.remove(catPos);
		catList.add(catPos, newCat);
		
		buildOrderedCategories();
		return true;
	}
	
	public boolean editItem(int oldPosition, Item newItem){
		Point nestedPos = flatIndexToNestedIndex(oldPosition);
		int catIndex = nestedPos.x;
		int itemPos = nestedPos.y;
		
		ArrayList<ListItem> catList = nestedData.get(catIndex);
		catList.remove(itemPos);
		catList.add(itemPos, newItem);
		return true;
	}
	
	public void save(Context ctx) {
		writeToXML(ctx);
	}
	
	public void load(Context ctx) {
		buildFromXML(ctx);
	}
	
	/**
	 * Writes the current list to the xml file
	 * @return if the file wrote successfully
	 */
	private boolean writeToXML(Context ctx){
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element root = doc.createElement("list");
			doc.appendChild(root);

			// This adds a category to the DOM
			for (int i = 0; i < nestedData.size(); i++) {
				Element catElement = doc.createElement("category");
				catElement.setAttribute("name", nestedData.get(i).get(0)
						.getName());
				catElement.setAttribute("color", "" + nestedData.get(i).get(0).getColor());
				root.appendChild(catElement);
				for (int j = 1; j < nestedData.get(i).size(); j++) {
					Element itemElement = doc.createElement("item");
					itemElement.setAttribute("name", nestedData.get(i).get(j)
							.getName());
					itemElement.setAttribute("checked", "" + ((Item) nestedData.get(i).get(j)).isChecked());
					catElement.appendChild(itemElement);
				}

			}

			// Output the XML

			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();

			// print xml
			System.out.println("Here's the xml:\n\n" + xmlString);

			// Write to Android
			
			FileOutputStream fos = ctx.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			fos.write(xmlString.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Builds the list from the xml file
	 * @return if the list was built successfully
	 */
	private boolean buildFromXML(Context ctx) {
		this.deleteData();
		XMLParser xmlParser = new XMLParser();
		try {
			FileInputStream xml = ctx.openFileInput(FILE_NAME);
			Document doc = xmlParser.getDomElement(xml);
			if(doc == null) {
				return false;
			}
			NodeList categories = doc.getElementsByTagName("category");
			System.out.println("Categories: " + categories.getLength());
			for(int i = 0; i < categories.getLength(); i++ ) { 
				String categoryName = ((Element) categories.item(i)).getAttribute("name");
				String color = ((Element) categories.item(i)).getAttribute("color");
				if(!color.equals("")) {
					int categoryColor = Integer.parseInt(((Element) categories.item(i)).getAttribute("color"));
					addCategory(categoryName, categoryColor);
				} else {
					addCategory(categoryName);
				}
				System.out.println(categoryName);
				if(categories.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) categories.item(i);
					List<Item> items = xmlParser.getValue(e);
					System.out.println(items);
					
					for(Item item : items) { 
						addItem(item, categoryName);
					}
				}else {
					
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ListItem> getShoppingList() {

		ArrayList<ListItem> shoppingList = new ArrayList<ListItem>();
		
		for(ArrayList<ListItem> category : nestedData){
			shoppingList.addAll(category);
		}
		
		return shoppingList;
	}

	public void makeShoppingList(){
		//ArrayList<ListItem> listCache = new ArrayList<ListItem>();
		
		String[] categoryNames = {"Dairy", "Fruit", "Vegetables", "Random"};
		Integer[] colors = {Color.parseColor("#1515da"),  Color.parseColor("#bb0c29"), 
							Color.parseColor("#15da1a"), Color.parseColor("#6415da")};
		
		int i = 0;
		for(String cName : categoryNames){
			this.addCategory(cName, colors[i]);
			this.addItem("foo", i+1);
			this.addItem("bar", i+1);
			this.addItem("fiddly", i+1);
			//System.out.println(nestedData);
			i++;
		}
    	
    }

	public List<Category> getCategories() {
		return this.orderedCategories;
	}
	
	/**
	 * Takes the category from each 'Category' list in the nested data
	 * (the Category object is always the head element in each nested category list,
	 * so retrieval is always O(1)) and puts it into a list that reflects the index
	 * of the category list in the nested data
	 */
	private void buildOrderedCategories(){
		List<Category> categories = new ArrayList<Category>();
		
		for (ArrayList<ListItem> catList : nestedData){
			categories.add((Category) catList.get(NESTED_CAT_INDEX));
		}
		
		orderedCategories = categories;
	}
	
	private boolean addToDefault(String itemName){
		ArrayList<ListItem> defaultList;
		boolean rebuildCategories = false;
		
		// check if the default category already exists. If not, make it
		if ( nestedData.peek().get(NESTED_CAT_INDEX).getName().equals(DEFAULT_CAT_NAME)){
			defaultList = nestedData.peek();
			
		}else{
			
			// Create the default list with head category, and add it to the
			// front of the nested data
			defaultList = new ArrayList<ListItem>();
			Category defaultCategory = new Category(DEFAULT_CAT_NAME, Color.BLACK);
			defaultList.add(defaultCategory);
			nestedData.addFirst(defaultList);
			rebuildCategories = true;
		}
		
		// Add the new item to the default list. If the default list
		// had to be created, rebuild the category list
		defaultList.add(new Item(itemName));
		
		if ( rebuildCategories ){
			buildOrderedCategories();
		}
		
		return true;
	}
	
	/**
	 * This will clear the list of ALL user-entered items and ALL user-entered categories.
	 * The default category will be rebuilt. This should only be a development debugging tool.
	 */
	public void deleteData() {
		orderedCategories = new ArrayList<Category>();
		nestedData = new LinkedList<ArrayList<ListItem>>();
		
		addCategory("Default");
		// Write to xml?
	}

	public boolean removeItem(int positionInShoppingList) {
		
		Point nestedIndex = flatIndexToNestedIndex(positionInShoppingList);
		int categoryIndex = nestedIndex.x;
		int listItemIndex = nestedIndex.y;
		
		if ( listItemIndex == NESTED_CAT_INDEX){
			return removeCategory(categoryIndex);
		}
		
		ArrayList<ListItem> items = nestedData.get(categoryIndex);
		System.out.println("["+categoryIndex+","+listItemIndex+"]");
		
		ListItem removed = items.remove(listItemIndex);
		return removed != null;
	}


	public void deleteCheckedItems() {

		for( ArrayList<ListItem> list : nestedData ){
			List<Integer> toDelete = new LinkedList<Integer>(); 
			for(int i=0; i<list.size(); i++){
				ListItem curItem = list.get(i);
				if (curItem instanceof Item){
					Item item = (Item)  list.get(i);
					if( item.isChecked() ){
						toDelete.add(i);
					}
				}
			}
			int deleted =0;
			for(int deletionIndex : toDelete){
				System.out.print(deletionIndex + " ");
				list.remove(deletionIndex - deleted);
				deleted++;
			}
		}	
	}

	/**
	 * Returns an index of the item WITHIN ITS CATEGORY. The category index
	 * needs to be found for a full nested address.
	 * @param		 flatIndex
	 * @return		 Point object. X value refers to the category index.
	 * 				 Y value refers to the list item index in that category
	 */
	private Point flatIndexToNestedIndex(int flatIndex){
		
		int itemCounter = 0;
		int catIndex = 0;
		int curSize;
		for( ArrayList<ListItem> category : nestedData){
			curSize = category.size();
			
			if( itemCounter + curSize > flatIndex){
				break;
			}
			
			itemCounter = itemCounter + curSize;
			catIndex++;
		}
		
		int subIndex = flatIndex - itemCounter;
		Point nestedIndex = new Point(catIndex, subIndex);
		
		return nestedIndex;
	}
	
	public boolean checkItem(int flatPosition){
		Point nestedIndex = flatIndexToNestedIndex(flatPosition);
		int categoryIndex = nestedIndex.x;
		int listItemIndex = nestedIndex.y;
		
		ListItem listItem = nestedData.get(categoryIndex).get(listItemIndex);
		if ( listItem instanceof Category){
			return false;
		}
		
		Item toToggle = (Item) listItem;
		toToggle.toggleChecked();
		
		return true; 
	}

	
}
