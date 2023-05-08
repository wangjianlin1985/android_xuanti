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

	/*构造题目信息业务层对象*/
	private SubjectDAO subjectDAO = new SubjectDAO();

	/*默认构造函数*/
	public SubjectServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询题目信息的参数信息*/
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

			/*调用业务逻辑层执行题目信息查询*/
			List<Subject> subjectList = subjectDAO.QuerySubject(subjectName,subjectTypeObj,teacherObj,addTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加题目信息：获取题目信息参数，参数保存到新建的题目信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = subjectDAO.AddSubject(subject);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除题目信息：获取题目信息的题目编号*/
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			/*调用业务逻辑层执行删除操作*/
			String result = subjectDAO.DeleteSubject(subjectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新题目信息之前先根据subjectId查询某个题目信息*/
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			Subject subject = subjectDAO.GetSubject(subjectId);

			// 客户端查询的题目信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新题目信息：获取题目信息参数，参数保存到新建的题目信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = subjectDAO.UpdateSubject(subject);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
