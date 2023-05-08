package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.GuestBook;
import com.mobileclient.util.HttpUtil;

/*留言交流管理业务逻辑层*/
public class GuestBookService {
	/* 添加留言交流 */
	public String AddGuestBook(GuestBook guestBook) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBook.getGuestBookId() + "");
		params.put("qustion", guestBook.getQustion());
		params.put("student", guestBook.getStudent());
		params.put("questionTime", guestBook.getQuestionTime());
		params.put("reply", guestBook.getReply());
		params.put("teacherObj", guestBook.getTeacherObj());
		params.put("replyTime", guestBook.getReplyTime());
		params.put("replyFlag", guestBook.getReplyFlag() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询留言交流 */
	public List<GuestBook> QueryGuestBook(GuestBook queryConditionGuestBook) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GuestBookServlet?action=query";
		if(queryConditionGuestBook != null) {
			urlString += "&qustion=" + URLEncoder.encode(queryConditionGuestBook.getQustion(), "UTF-8") + "";
			urlString += "&student=" + URLEncoder.encode(queryConditionGuestBook.getStudent(), "UTF-8") + "";
			urlString += "&teacherObj=" + URLEncoder.encode(queryConditionGuestBook.getTeacherObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		GuestBookListHandler guestBookListHander = new GuestBookListHandler();
		xr.setContentHandler(guestBookListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<GuestBook> guestBookList = guestBookListHander.getGuestBookList();
		return guestBookList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GuestBook guestBook = new GuestBook();
				guestBook.setGuestBookId(object.getInt("guestBookId"));
				guestBook.setQustion(object.getString("qustion"));
				guestBook.setStudent(object.getString("student"));
				guestBook.setQuestionTime(object.getString("questionTime"));
				guestBook.setReply(object.getString("reply"));
				guestBook.setTeacherObj(object.getString("teacherObj"));
				guestBook.setReplyTime(object.getString("replyTime"));
				guestBook.setReplyFlag(object.getInt("replyFlag"));
				guestBookList.add(guestBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return guestBookList;
	}

	/* 更新留言交流 */
	public String UpdateGuestBook(GuestBook guestBook) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBook.getGuestBookId() + "");
		params.put("qustion", guestBook.getQustion());
		params.put("student", guestBook.getStudent());
		params.put("questionTime", guestBook.getQuestionTime());
		params.put("reply", guestBook.getReply());
		params.put("teacherObj", guestBook.getTeacherObj());
		params.put("replyTime", guestBook.getReplyTime());
		params.put("replyFlag", guestBook.getReplyFlag() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除留言交流 */
	public String DeleteGuestBook(int guestBookId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBookId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "留言交流信息删除失败!";
		}
	}

	/* 根据记录id获取留言交流对象 */
	public GuestBook GetGuestBook(int guestBookId)  {
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("guestBookId", guestBookId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GuestBookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GuestBook guestBook = new GuestBook();
				guestBook.setGuestBookId(object.getInt("guestBookId"));
				guestBook.setQustion(object.getString("qustion"));
				guestBook.setStudent(object.getString("student"));
				guestBook.setQuestionTime(object.getString("questionTime"));
				guestBook.setReply(object.getString("reply"));
				guestBook.setTeacherObj(object.getString("teacherObj"));
				guestBook.setReplyTime(object.getString("replyTime"));
				guestBook.setReplyFlag(object.getInt("replyFlag"));
				guestBookList.add(guestBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = guestBookList.size();
		if(size>0) return guestBookList.get(0); 
		else return null; 
	}
}
