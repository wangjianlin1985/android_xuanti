package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SelectItem;
import com.mobileclient.util.HttpUtil;

/*学生选题管理业务逻辑层*/
public class SelectItemService {
	/* 添加学生选题 */
	public String AddSelectItem(SelectItem selectItem) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectItemId", selectItem.getSelectItemId() + "");
		params.put("subjectObj", selectItem.getSubjectObj() + "");
		params.put("studentObj", selectItem.getStudentObj());
		params.put("selectTime", selectItem.getSelectTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询学生选题 */
	public List<SelectItem> QuerySelectItem(SelectItem queryConditionSelectItem) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SelectItemServlet?action=query";
		if(queryConditionSelectItem != null) {
			urlString += "&subjectObj=" + queryConditionSelectItem.getSubjectObj();
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionSelectItem.getStudentObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SelectItemListHandler selectItemListHander = new SelectItemListHandler();
		xr.setContentHandler(selectItemListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SelectItem> selectItemList = selectItemListHander.getSelectItemList();
		return selectItemList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SelectItem selectItem = new SelectItem();
				selectItem.setSelectItemId(object.getInt("selectItemId"));
				selectItem.setSubjectObj(object.getInt("subjectObj"));
				selectItem.setStudentObj(object.getString("studentObj"));
				selectItem.setSelectTime(object.getString("selectTime"));
				selectItemList.add(selectItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectItemList;
	}

	/* 更新学生选题 */
	public String UpdateSelectItem(SelectItem selectItem) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectItemId", selectItem.getSelectItemId() + "");
		params.put("subjectObj", selectItem.getSubjectObj() + "");
		params.put("studentObj", selectItem.getStudentObj());
		params.put("selectTime", selectItem.getSelectTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除学生选题 */
	public String DeleteSelectItem(int selectItemId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectItemId", selectItemId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "学生选题信息删除失败!";
		}
	}

	/* 根据选题id获取学生选题对象 */
	public SelectItem GetSelectItem(int selectItemId)  {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectItemId", selectItemId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SelectItem selectItem = new SelectItem();
				selectItem.setSelectItemId(object.getInt("selectItemId"));
				selectItem.setSubjectObj(object.getInt("subjectObj"));
				selectItem.setStudentObj(object.getString("studentObj"));
				selectItem.setSelectTime(object.getString("selectTime"));
				selectItemList.add(selectItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = selectItemList.size();
		if(size>0) return selectItemList.get(0); 
		else return null; 
	}
}
