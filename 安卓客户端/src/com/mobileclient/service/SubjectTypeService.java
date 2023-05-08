package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SubjectType;
import com.mobileclient.util.HttpUtil;

/*题目类型管理业务逻辑层*/
public class SubjectTypeService {
	/* 添加题目类型 */
	public String AddSubjectType(SubjectType subjectType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectTypeId", subjectType.getSubjectTypeId() + "");
		params.put("subjectTypeName", subjectType.getSubjectTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询题目类型 */
	public List<SubjectType> QuerySubjectType(SubjectType queryConditionSubjectType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SubjectTypeServlet?action=query";
		if(queryConditionSubjectType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SubjectTypeListHandler subjectTypeListHander = new SubjectTypeListHandler();
		xr.setContentHandler(subjectTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SubjectType> subjectTypeList = subjectTypeListHander.getSubjectTypeList();
		return subjectTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SubjectType> subjectTypeList = new ArrayList<SubjectType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SubjectType subjectType = new SubjectType();
				subjectType.setSubjectTypeId(object.getInt("subjectTypeId"));
				subjectType.setSubjectTypeName(object.getString("subjectTypeName"));
				subjectTypeList.add(subjectType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectTypeList;
	}

	/* 更新题目类型 */
	public String UpdateSubjectType(SubjectType subjectType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectTypeId", subjectType.getSubjectTypeId() + "");
		params.put("subjectTypeName", subjectType.getSubjectTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除题目类型 */
	public String DeleteSubjectType(int subjectTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectTypeId", subjectTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "题目类型信息删除失败!";
		}
	}

	/* 根据类型编号获取题目类型对象 */
	public SubjectType GetSubjectType(int subjectTypeId)  {
		List<SubjectType> subjectTypeList = new ArrayList<SubjectType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("subjectTypeId", subjectTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SubjectTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SubjectType subjectType = new SubjectType();
				subjectType.setSubjectTypeId(object.getInt("subjectTypeId"));
				subjectType.setSubjectTypeName(object.getString("subjectTypeName"));
				subjectTypeList.add(subjectType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = subjectTypeList.size();
		if(size>0) return subjectTypeList.get(0); 
		else return null; 
	}
}
