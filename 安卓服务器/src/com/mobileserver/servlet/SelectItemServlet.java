package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SelectItemDAO;
import com.mobileserver.domain.SelectItem;

import org.json.JSONStringer;

public class SelectItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造学生选题业务层对象*/
	private SelectItemDAO selectItemDAO = new SelectItemDAO();

	/*默认构造函数*/
	public SelectItemServlet() {
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
			/*获取查询学生选题的参数信息*/
			int subjectObj = 0;
			if (request.getParameter("subjectObj") != null)
				subjectObj = Integer.parseInt(request.getParameter("subjectObj"));
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");

			/*调用业务逻辑层执行学生选题查询*/
			List<SelectItem> selectItemList = selectItemDAO.QuerySelectItem(subjectObj,studentObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SelectItems>").append("\r\n");
			for (int i = 0; i < selectItemList.size(); i++) {
				sb.append("	<SelectItem>").append("\r\n")
				.append("		<selectItemId>")
				.append(selectItemList.get(i).getSelectItemId())
				.append("</selectItemId>").append("\r\n")
				.append("		<subjectObj>")
				.append(selectItemList.get(i).getSubjectObj())
				.append("</subjectObj>").append("\r\n")
				.append("		<studentObj>")
				.append(selectItemList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<selectTime>")
				.append(selectItemList.get(i).getSelectTime())
				.append("</selectTime>").append("\r\n")
				.append("	</SelectItem>").append("\r\n");
			}
			sb.append("</SelectItems>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SelectItem selectItem: selectItemList) {
				  stringer.object();
			  stringer.key("selectItemId").value(selectItem.getSelectItemId());
			  stringer.key("subjectObj").value(selectItem.getSubjectObj());
			  stringer.key("studentObj").value(selectItem.getStudentObj());
			  stringer.key("selectTime").value(selectItem.getSelectTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加学生选题：获取学生选题参数，参数保存到新建的学生选题对象 */ 
			SelectItem selectItem = new SelectItem();
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			selectItem.setSelectItemId(selectItemId);
			int subjectObj = Integer.parseInt(request.getParameter("subjectObj"));
			selectItem.setSubjectObj(subjectObj);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setStudentObj(studentObj);
			String selectTime = new String(request.getParameter("selectTime").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setSelectTime(selectTime);

			/* 调用业务层执行添加操作 */
			String result = selectItemDAO.AddSelectItem(selectItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除学生选题：获取学生选题的选题id*/
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			/*调用业务逻辑层执行删除操作*/
			String result = selectItemDAO.DeleteSelectItem(selectItemId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新学生选题之前先根据selectItemId查询某个学生选题*/
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			SelectItem selectItem = selectItemDAO.GetSelectItem(selectItemId);

			// 客户端查询的学生选题对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("selectItemId").value(selectItem.getSelectItemId());
			  stringer.key("subjectObj").value(selectItem.getSubjectObj());
			  stringer.key("studentObj").value(selectItem.getStudentObj());
			  stringer.key("selectTime").value(selectItem.getSelectTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新学生选题：获取学生选题参数，参数保存到新建的学生选题对象 */ 
			SelectItem selectItem = new SelectItem();
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			selectItem.setSelectItemId(selectItemId);
			int subjectObj = Integer.parseInt(request.getParameter("subjectObj"));
			selectItem.setSubjectObj(subjectObj);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setStudentObj(studentObj);
			String selectTime = new String(request.getParameter("selectTime").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setSelectTime(selectTime);

			/* 调用业务层执行更新操作 */
			String result = selectItemDAO.UpdateSelectItem(selectItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
