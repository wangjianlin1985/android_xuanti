package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SubjectType;
public class SubjectTypeListHandler extends DefaultHandler {
	private List<SubjectType> subjectTypeList = null;
	private SubjectType subjectType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (subjectType != null) { 
            String valueString = new String(ch, start, length); 
            if ("subjectTypeId".equals(tempString)) 
            	subjectType.setSubjectTypeId(new Integer(valueString).intValue());
            else if ("subjectTypeName".equals(tempString)) 
            	subjectType.setSubjectTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SubjectType".equals(localName)&&subjectType!=null){
			subjectTypeList.add(subjectType);
			subjectType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		subjectTypeList = new ArrayList<SubjectType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SubjectType".equals(localName)) {
            subjectType = new SubjectType(); 
        }
        tempString = localName; 
	}

	public List<SubjectType> getSubjectTypeList() {
		return this.subjectTypeList;
	}
}
