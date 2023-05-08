package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ClassInfoDAO;
import com.mobileserver.domain.ClassInfo;

import org.json.JSONStringer;

public class ClassInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����༶��Ϣҵ������*/
	private ClassInfoDAO classInfoDAO = new ClassInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public ClassInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ�༶��Ϣ�Ĳ�����Ϣ*/
			String classNumber = request.getParameter("classNumber");
			classNumber = classNumber == null ? "" : new String(request.getParameter(
					"classNumber").getBytes("iso-8859-1"), "UTF-8");
			String specialName = request.getParameter("specialName");
			specialName = specialName == null ? "" : new String(request.getParameter(
					"specialName").getBytes("iso-8859-1"), "UTF-8");
			String className = request.getParameter("className");
			className = className == null ? "" : new String(request.getParameter(
					"className").getBytes("iso-8859-1"), "UTF-8");
			Timestamp startDate = null;
			if (request.getParameter("startDate") != null)
				startDate = Timestamp.valueOf(request.getParameter("startDate"));

			/*����ҵ���߼���ִ�а༶��Ϣ��ѯ*/
			List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(classNumber,specialName,className,startDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ClassInfos>").append("\r\n");
			for (int i = 0; i < classInfoList.size(); i++) {
				sb.append("	<ClassInfo>").append("\r\n")
				.append("		<classNumber>")
				.append(classInfoList.get(i).getClassNumber())
				.append("</classNumber>").append("\r\n")
				.append("		<specialName>")
				.append(classInfoList.get(i).getSpecialName())
				.append("</specialName>").append("\r\n")
				.append("		<className>")
				.append(classInfoList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("		<startDate>")
				.append(classInfoList.get(i).getStartDate())
				.append("</startDate>").append("\r\n")
				.append("		<headTeacher>")
				.append(classInfoList.get(i).getHeadTeacher())
				.append("</headTeacher>").append("\r\n")
				.append("	</ClassInfo>").append("\r\n");
			}
			sb.append("</ClassInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ClassInfo classInfo: classInfoList) {
				  stringer.object();
			  stringer.key("classNumber").value(classInfo.getClassNumber());
			  stringer.key("specialName").value(classInfo.getSpecialName());
			  stringer.key("className").value(classInfo.getClassName());
			  stringer.key("startDate").value(classInfo.getStartDate());
			  stringer.key("headTeacher").value(classInfo.getHeadTeacher());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӱ༶��Ϣ����ȡ�༶��Ϣ�������������浽�½��İ༶��Ϣ���� */ 
			ClassInfo classInfo = new ClassInfo();
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassNumber(classNumber);
			String specialName = new String(request.getParameter("specialName").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setSpecialName(specialName);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassName(className);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			classInfo.setStartDate(startDate);
			String headTeacher = new String(request.getParameter("headTeacher").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setHeadTeacher(headTeacher);

			/* ����ҵ���ִ����Ӳ��� */
			String result = classInfoDAO.AddClassInfo(classInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���༶��Ϣ����ȡ�༶��Ϣ�İ༶���*/
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = classInfoDAO.DeleteClassInfo(classNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���°༶��Ϣ֮ǰ�ȸ���classNumber��ѯĳ���༶��Ϣ*/
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			ClassInfo classInfo = classInfoDAO.GetClassInfo(classNumber);

			// �ͻ��˲�ѯ�İ༶��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("classNumber").value(classInfo.getClassNumber());
			  stringer.key("specialName").value(classInfo.getSpecialName());
			  stringer.key("className").value(classInfo.getClassName());
			  stringer.key("startDate").value(classInfo.getStartDate());
			  stringer.key("headTeacher").value(classInfo.getHeadTeacher());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���°༶��Ϣ����ȡ�༶��Ϣ�������������浽�½��İ༶��Ϣ���� */ 
			ClassInfo classInfo = new ClassInfo();
			String classNumber = new String(request.getParameter("classNumber").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassNumber(classNumber);
			String specialName = new String(request.getParameter("specialName").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setSpecialName(specialName);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassName(className);
			Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
			classInfo.setStartDate(startDate);
			String headTeacher = new String(request.getParameter("headTeacher").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setHeadTeacher(headTeacher);

			/* ����ҵ���ִ�и��²��� */
			String result = classInfoDAO.UpdateClassInfo(classInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
