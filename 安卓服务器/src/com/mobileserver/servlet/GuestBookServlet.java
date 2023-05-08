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

	/*�������Խ���ҵ������*/
	private GuestBookDAO guestBookDAO = new GuestBookDAO();

	/*Ĭ�Ϲ��캯��*/
	public GuestBookServlet() {
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
			/*��ȡ��ѯ���Խ����Ĳ�����Ϣ*/
			String qustion = request.getParameter("qustion");
			qustion = qustion == null ? "" : new String(request.getParameter(
					"qustion").getBytes("iso-8859-1"), "UTF-8");
			String student = "";
			if (request.getParameter("student") != null)
				student = request.getParameter("student");
			String teacherObj = "";
			if (request.getParameter("teacherObj") != null)
				teacherObj = request.getParameter("teacherObj");

			/*����ҵ���߼���ִ�����Խ�����ѯ*/
			List<GuestBook> guestBookList = guestBookDAO.QueryGuestBook(qustion,student,teacherObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������Խ�������ȡ���Խ����������������浽�½������Խ������� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = guestBookDAO.AddGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����Խ�������ȡ���Խ����ļ�¼id*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = guestBookDAO.DeleteGuestBook(guestBookId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������Խ���֮ǰ�ȸ���guestBookId��ѯĳ�����Խ���*/
			int guestBookId = Integer.parseInt(request.getParameter("guestBookId"));
			GuestBook guestBook = guestBookDAO.GetGuestBook(guestBookId);

			// �ͻ��˲�ѯ�����Խ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������Խ�������ȡ���Խ����������������浽�½������Խ������� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = guestBookDAO.UpdateGuestBook(guestBook);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
