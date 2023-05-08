package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SubjectTypeDAO;
import com.mobileserver.domain.SubjectType;

import org.json.JSONStringer;

public class SubjectTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造题目类型业务层对象*/
	private SubjectTypeDAO subjectTypeDAO = new SubjectTypeDAO();

	/*默认构造函数*/
	public SubjectTypeServlet() {
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
			/*获取查询题目类型的参数信息*/

			/*调用业务逻辑层执行题目类型查询*/
			List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SubjectTypes>").append("\r\n");
			for (int i = 0; i < subjectTypeList.size(); i++) {
				sb.append("	<SubjectType>").append("\r\n")
				.append("		<subjectTypeId>")
				.append(subjectTypeList.get(i).getSubjectTypeId())
				.append("</subjectTypeId>").append("\r\n")
				.append("		<subjectTypeName>")
				.append(subjectTypeList.get(i).getSubjectTypeName())
				.append("</subjectTypeName>").append("\r\n")
				.append("	</SubjectType>").append("\r\n");
			}
			sb.append("</SubjectTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SubjectType subjectType: subjectTypeList) {
				  stringer.object();
			  stringer.key("subjectTypeId").value(subjectType.getSubjectTypeId());
			  stringer.key("subjectTypeName").value(subjectType.getSubjectTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加题目类型：获取题目类型参数，参数保存到新建的题目类型对象 */ 
			SubjectType subjectType = new SubjectType();
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			subjectType.setSubjectTypeId(subjectTypeId);
			String subjectTypeName = new String(request.getParameter("subjectTypeName").getBytes("iso-8859-1"), "UTF-8");
			subjectType.setSubjectTypeName(subjectTypeName);

			/* 调用业务层执行添加操作 */
			String result = subjectTypeDAO.AddSubjectType(subjectType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除题目类型：获取题目类型的类型编号*/
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = subjectTypeDAO.DeleteSubjectType(subjectTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新题目类型之前先根据subjectTypeId查询某个题目类型*/
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			SubjectType subjectType = subjectTypeDAO.GetSubjectType(subjectTypeId);

			// 客户端查询的题目类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("subjectTypeId").value(subjectType.getSubjectTypeId());
			  stringer.key("subjectTypeName").value(subjectType.getSubjectTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新题目类型：获取题目类型参数，参数保存到新建的题目类型对象 */ 
			SubjectType subjectType = new SubjectType();
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			subjectType.setSubjectTypeId(subjectTypeId);
			String subjectTypeName = new String(request.getParameter("subjectTypeName").getBytes("iso-8859-1"), "UTF-8");
			subjectType.setSubjectTypeName(subjectTypeName);

			/* 调用业务层执行更新操作 */
			String result = subjectTypeDAO.UpdateSubjectType(subjectType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
