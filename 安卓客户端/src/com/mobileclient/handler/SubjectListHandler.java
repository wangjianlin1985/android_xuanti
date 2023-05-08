package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Subject;
public class SubjectListHandler extends DefaultHandler {
	private List<Subject> subjectList = null;
	private Subject subject;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (subject != null) { 
            String valueString = new String(ch, start, length); 
            if ("subjectId".equals(tempString)) 
            	subject.setSubjectId(new Integer(valueString).intValue());
            else if ("subjectName".equals(tempString)) 
            	subject.setSubjectName(valueString); 
            else if ("subjectTypeObj".equals(tempString)) 
            	subject.setSubjectTypeObj(new Integer(valueString).intValue());
            else if ("content".equals(tempString)) 
            	subject.setContent(valueString); 
            else if ("studentNumber".equals(tempString)) 
            	subject.setStudentNumber(new Integer(valueString).intValue());
            else if ("teacherObj".equals(tempString)) 
            	subject.setTeacherObj(valueString); 
            else if ("addTime".equals(tempString)) 
            	subject.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Subject".equals(localName)&&subject!=null){
			subjectList.add(subject);
			subject = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		subjectList = new ArrayList<Subject>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Subject".equals(localName)) {
            subject = new Subject(); 
        }
        tempString = localName; 
	}

	public List<Subject> getSubjectList() {
		return this.subjectList;
	}
}
