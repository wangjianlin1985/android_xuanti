package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Subject;
import com.mobileclient.util.HttpUtil;

/*题目信息管理业务逻辑层*/
public class SubjectService {
	/* 添加题目信息 */
	public String AddSubject(Subject subject) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectId", subject.getSubjectId() + "");
		params.put("subjectName", subject.getSubjectName());
		params.put("subjectTypeObj", subject.getSubjectTypeObj() + "");
		params.put("content", subject.getContent());
		params.put("studentNumber", subject.getStudentNumber() + "");
		params.put("teacherObj", subject.getTeacherObj());
		params.put("addTime", subject.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询题目信息 */
	public List<Subject> QuerySubject(Subject queryConditionSubject) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SubjectServlet?action=query";
		if(queryConditionSubject != null) {
			urlString += "&subjectName=" + URLEncoder.encode(queryConditionSubject.getSubjectName(), "UTF-8") + "";
			urlString += "&subjectTypeObj=" + queryConditionSubject.getSubjectTypeObj();
			urlString += "&teacherObj=" + URLEncoder.encode(queryConditionSubject.getTeacherObj(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionSubject.getAddTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SubjectListHandler subjectListHander = new SubjectListHandler();
		xr.setContentHandler(subjectListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Subject> subjectList = subjectListHander.getSubjectList();
		return subjectList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Subject> subjectList = new ArrayList<Subject>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Subject subject = new Subject();
				subject.setSubjectId(object.getInt("subjectId"));
				subject.setSubjectName(object.getString("subjectName"));
				subject.setSubjectTypeObj(object.getInt("subjectTypeObj"));
				subject.setContent(object.getString("content"));
				subject.setStudentNumber(object.getInt("studentNumber"));
				subject.setTeacherObj(object.getString("teacherObj"));
				subject.setAddTime(object.getString("addTime"));
				subjectList.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	/* 更新题目信息 */
	public String UpdateSubject(Subject subject) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectId", subject.getSubjectId() + "");
		params.put("subjectName", subject.getSubjectName());
		params.put("subjectTypeObj", subject.getSubjectTypeObj() + "");
		params.put("content", subject.getContent());
		params.put("studentNumber", subject.getStudentNumber() + "");
		params.put("teacherObj", subject.getTeacherObj());
		params.put("addTime", subject.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除题目信息 */
	public String DeleteSubject(int subjectId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectId", subjectId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "题目信息信息删除失败!";
		}
	}

	/* 根据题目编号获取题目信息对象 */
	public Subject GetSubject(int subjectId)  {
		List<Subject> subjectList = new ArrayList<Subject>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectId", subjectId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Subject subject = new Subject();
				subject.setSubjectId(object.getInt("subjectId"));
				subject.setSubjectName(object.getString("subjectName"));
				subject.setSubjectTypeObj(object.getInt("subjectTypeObj"));
				subject.setContent(object.getString("content"));
				subject.setStudentNumber(object.getInt("studentNumber"));
				subject.setTeacherObj(object.getString("teacherObj"));
				subject.setAddTime(object.getString("addTime"));
				subjectList.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = subjectList.size();
		if(size>0) return subjectList.get(0); 
		else return null; 
	}
}
