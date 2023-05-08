package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SubjectDAO;
import com.mobileserver.domain.Subject;

import org.json.JSONStringer;

public class SubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������Ŀ��Ϣҵ������*/
	private SubjectDAO subjectDAO = new SubjectDAO();

	/*Ĭ�Ϲ��캯��*/
	public SubjectServlet() {
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
			/*��ȡ��ѯ��Ŀ��Ϣ�Ĳ�����Ϣ*/
			String subjectName = request.getParameter("subjectName");
			subjectName = subjectName == null ? "" : new String(request.getParameter(
					"subjectName").getBytes("iso-8859-1"), "UTF-8");
			int subjectTypeObj = 0;
			if (request.getParameter("subjectTypeObj") != null)
				subjectTypeObj = Integer.parseInt(request.getParameter("subjectTypeObj"));
			String teacherObj = "";
			if (request.getParameter("teacherObj") != null)
				teacherObj = request.getParameter("teacherObj");
			String addTime = request.getParameter("addTime");
			addTime = addTime == null ? "" : new String(request.getParameter(
					"addTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����Ŀ��Ϣ��ѯ*/
			List<Subject> subjectList = subjectDAO.QuerySubject(subjectName,subjectTypeObj,teacherObj,addTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Subjects>").append("\r\n");
			for (int i = 0; i < subjectList.size(); i++) {
				sb.append("	<Subject>").append("\r\n")
				.append("		<subjectId>")
				.append(subjectList.get(i).getSubjectId())
				.append("</subjectId>").append("\r\n")
				.append("		<subjectName>")
				.append(subjectList.get(i).getSubjectName())
				.append("</subjectName>").append("\r\n")
				.append("		<subjectTypeObj>")
				.append(subjectList.get(i).getSubjectTypeObj())
				.append("</subjectTypeObj>").append("\r\n")
				.append("		<content>")
				.append(subjectList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<studentNumber>")
				.append(subjectList.get(i).getStudentNumber())
				.append("</studentNumber>").append("\r\n")
				.append("		<teacherObj>")
				.append(subjectList.get(i).getTeacherObj())
				.append("</teacherObj>").append("\r\n")
				.append("		<addTime>")
				.append(subjectList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</Subject>").append("\r\n");
			}
			sb.append("</Subjects>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Subject subject: subjectList) {
				  stringer.object();
			  stringer.key("subjectId").value(subject.getSubjectId());
			  stringer.key("subjectName").value(subject.getSubjectName());
			  stringer.key("subjectTypeObj").value(subject.getSubjectTypeObj());
			  stringer.key("content").value(subject.getContent());
			  stringer.key("studentNumber").value(subject.getStudentNumber());
			  stringer.key("teacherObj").value(subject.getTeacherObj());
			  stringer.key("addTime").value(subject.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ŀ��Ϣ����ȡ��Ŀ��Ϣ�������������浽�½�����Ŀ��Ϣ���� */ 
			Subject subject = new Subject();
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			subject.setSubjectId(subjectId);
			String subjectName = new String(request.getParameter("subjectName").getBytes("iso-8859-1"), "UTF-8");
			subject.setSubjectName(subjectName);
			int subjectTypeObj = Integer.parseInt(request.getParameter("subjectTypeObj"));
			subject.setSubjectTypeObj(subjectTypeObj);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			subject.setContent(content);
			int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
			subject.setStudentNumber(studentNumber);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			subject.setTeacherObj(teacherObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			subject.setAddTime(addTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = subjectDAO.AddSubject(subject);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ŀ��Ϣ����ȡ��Ŀ��Ϣ����Ŀ���*/
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = subjectDAO.DeleteSubject(subjectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ŀ��Ϣ֮ǰ�ȸ���subjectId��ѯĳ����Ŀ��Ϣ*/
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			Subject subject = subjectDAO.GetSubject(subjectId);

			// �ͻ��˲�ѯ����Ŀ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("subjectId").value(subject.getSubjectId());
			  stringer.key("subjectName").value(subject.getSubjectName());
			  stringer.key("subjectTypeObj").value(subject.getSubjectTypeObj());
			  stringer.key("content").value(subject.getContent());
			  stringer.key("studentNumber").value(subject.getStudentNumber());
			  stringer.key("teacherObj").value(subject.getTeacherObj());
			  stringer.key("addTime").value(subject.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ŀ��Ϣ����ȡ��Ŀ��Ϣ�������������浽�½�����Ŀ��Ϣ���� */ 
			Subject subject = new Subject();
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			subject.setSubjectId(subjectId);
			String subjectName = new String(request.getParameter("subjectName").getBytes("iso-8859-1"), "UTF-8");
			subject.setSubjectName(subjectName);
			int subjectTypeObj = Integer.parseInt(request.getParameter("subjectTypeObj"));
			subject.setSubjectTypeObj(subjectTypeObj);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			subject.setContent(content);
			int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
			subject.setStudentNumber(studentNumber);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			subject.setTeacherObj(teacherObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			subject.setAddTime(addTime);

			/* ����ҵ���ִ�и��²��� */
			String result = subjectDAO.UpdateSubject(subject);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
