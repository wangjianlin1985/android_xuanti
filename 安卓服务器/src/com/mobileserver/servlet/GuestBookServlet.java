package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.GuestBookDAO;
import com.mobileserver.domain.GuestBook;

import org.json.JSONStringer;

public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造留言交流业务层对象*/
	private GuestBookDAO guestBookDAO = new GuestBookDAO();

	/*默认构造函数*/
	public GuestBookServlet() {
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
			/*获取查询留言交流的参数信息*/
			String qustion = request.getParameter("qustion");
			qustion = qustion == null ? "" : new String(request.getParameter(
					"qustion").getBytes("iso-8859-1"), "UTF-8");
			String student = "";
			if (request.getParameter("student") != null)
				student = request.getParameter("student");
			String teacherObj = "";
			if (request.getParameter("teacherObj") != null)
				teacherObj = request.getParameter("teacherObj");

			/*调用业务逻辑层执行留言交流查询*/
			List<GuestBook> guestBookList = guestBookDAO.QueryGuestBook(qustion,student,teacherObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<GuestBooks>").append("\r\n");
			for (int i = 0; i < guestBookList.size(); i++) {
				sb.append("	<GuestBook>").append("\r\n")
				.append("		<guestBookId>")
				.append(guestBookList.get(i).getGuestBookId())
				.append("</guestBookId>").append("\r\n")
				.append("		<qustion>")
				.append(guestBookList.get(i).getQustion())
				.append("</qustion>").append("\r\n")
				.append("		<student>")
				.append(guestBookList.get(i).getStudent())
				.append("</student>").append("\r\n")
				.append("		<questionTime>")
				.append(guestBookList.get(i).getQuestionTime())
				.append("</questionTime>").append("\r\n")
				.append("		<reply>")
				.append(guestBookList.get(i).getReply())
				.append("</reply>").append("\r\n")
				.append("		<teacherObj>")
				.append(guestBookList.get(i).getTeacherObj())
				.append("</teacherObj>").append("\r\n")
				.append("		<replyTime>")
				.append(guestBookList.get(i).getReplyTime())
				.append("</replyTime>").append("\r\n")
				.append("		<replyFlag>")
				.append(guestBookList.get(i).getReplyFlag())
				.append("</replyFlag>").append("\r\n")
				.append("	</GuestBook>").append("\r\n");
			}
			sb.append("</GuestBooks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(GuestBook guestBook: guestBookList) {
				  stringer.object();
			  stringer.key("guestBookId").value(guestBook.getGuestBookId());
			  stringer.key("qustion").value(guestBook.getQustion());
			  stringer.key("student").value(guestBook.getStudent());
			  stringer.key("questionTime").value(guestBook.getQuestionTime());
			  stringer.key("reply").value(guestBook.getReply());
			  stringer.key("teacherObj").value(guestBook.getTeacherObj());
			  stringer.key("replyTime").value(guestBook.getReplyTime());
			  stringer.key("replyFlag").value(guestBook.getReplyFlag());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加留言交流：获取留言交流参数，参数保存到新建的留言交流对象 */ 
			GuestBook guestBook = new GuestBook();
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			guestBook.setGuestBookId(guestBookId);
			String qustion = new String(request.getParameter("qustion").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setQustion(qustion);
			String student = new String(request.getParameter("student").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setStudent(student);
			String questionTime = new String(request.getParameter("questionTime").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setQuestionTime(questionTime);
			String reply = new String(request.getParameter("reply").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setReply(reply);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setTeacherObj(teacherObj);
			String replyTime = new String(request.getParameter("replyTime").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setReplyTime(replyTime);
			int replyFlag = Integer.parseInt(request.getParameter("replyFlag"));
			guestBook.setReplyFlag(replyFlag);

			/* 调用业务层执行添加操作 */
			String result = guestBookDAO.AddGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除留言交流：获取留言交流的记录id*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			/*调用业务逻辑层执行删除操作*/
			String result = guestBookDAO.DeleteGuestBook(guestBookId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新留言交流之前先根据guestBookId查询某个留言交流*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			GuestBook guestBook = guestBookDAO.GetGuestBook(guestBookId);

			// 客户端查询的留言交流对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("guestBookId").value(guestBook.getGuestBookId());
			  stringer.key("qustion").value(guestBook.getQustion());
			  stringer.key("student").value(guestBook.getStudent());
			  stringer.key("questionTime").value(guestBook.getQuestionTime());
			  stringer.key("reply").value(guestBook.getReply());
			  stringer.key("teacherObj").value(guestBook.getTeacherObj());
			  stringer.key("replyTime").value(guestBook.getReplyTime());
			  stringer.key("replyFlag").value(guestBook.getReplyFlag());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新留言交流：获取留言交流参数，参数保存到新建的留言交流对象 */ 
			GuestBook guestBook = new GuestBook();
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			guestBook.setGuestBookId(guestBookId);
			String qustion = new String(request.getParameter("qustion").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setQustion(qustion);
			String student = new String(request.getParameter("student").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setStudent(student);
			String questionTime = new String(request.getParameter("questionTime").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setQuestionTime(questionTime);
			String reply = new String(request.getParameter("reply").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setReply(reply);
			String teacherObj = new String(request.getParameter("teacherObj").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setTeacherObj(teacherObj);
			String replyTime = new String(request.getParameter("replyTime").getBytes("iso-8859-1"), "UTF-8");
			guestBook.setReplyTime(replyTime);
			int replyFlag = Integer.parseInt(request.getParameter("replyFlag"));
			guestBook.setReplyFlag(replyFlag);

			/* 调用业务层执行更新操作 */
			String result = guestBookDAO.UpdateGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
