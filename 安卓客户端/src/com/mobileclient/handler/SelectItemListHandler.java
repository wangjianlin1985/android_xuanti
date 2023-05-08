package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SelectItem;
public class SelectItemListHandler extends DefaultHandler {
	private List<SelectItem> selectItemList = null;
	private SelectItem selectItem;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (selectItem != null) { 
            String valueString = new String(ch, start, length); 
            if ("selectItemId".equals(tempString)) 
            	selectItem.setSelectItemId(new Integer(valueString).intValue());
            else if ("subjectObj".equals(tempString)) 
            	selectItem.setSubjectObj(new Integer(valueString).intValue());
            else if ("studentObj".equals(tempString)) 
            	selectItem.setStudentObj(valueString); 
            else if ("selectTime".equals(tempString)) 
            	selectItem.setSelectTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SelectItem".equals(localName)&&selectItem!=null){
			selectItemList.add(selectItem);
			selectItem = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		selectItemList = new ArrayList<SelectItem>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SelectItem".equals(localName)) {
            selectItem = new SelectItem(); 
        }
        tempString = localName; 
	}

	public List<SelectItem> getSelectItemList() {
		return this.selectItemList;
	}
}
