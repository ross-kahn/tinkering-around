package com.goshop.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	
	public Document getDomElement(InputStream is){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            doc = db.parse(is); 
 
            } catch (ParserConfigurationException e) {
                return null;
            } catch (SAXException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
                // return DOM
            return doc;
    }
	
	public List<Item> getValue(Element item) {
	    NodeList items = item.getChildNodes();
	    List<Item> itemsNames = new ArrayList<Item>();
	    for(int i = 0; i < items.getLength(); i++) {
	    	if(items.item(i).getNodeType() == Node.ELEMENT_NODE) {
	    		Element e = ((Element) items.item(i));
		    	String name = e.getAttribute("name");
		    	String checked = e.getAttribute("checked");
		    	itemsNames.add(new Item(name).setChecked(Boolean.parseBoolean(checked)));
	    	}
	    }
	    return itemsNames;
	}
	 
	public final String getElementValue( Node elem ) {
	         Node child;
	         if( elem != null){
	             if (elem.hasChildNodes()){
	                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                     if( child.getNodeType() == Node.TEXT_NODE  ){
	                         return child.getNodeValue();
	                     }
	                 }
	             }
	         }
	         return "";
	  } 
}
