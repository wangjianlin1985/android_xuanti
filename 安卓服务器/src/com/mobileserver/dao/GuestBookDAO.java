package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.GuestBook;
import com.mobileserver.util.DB;

public class GuestBookDAO {

	public List<GuestBook> QueryGuestBook(String qustion,String student,String teacherObj) {
		List<GuestBook> guestBookList = new ArrayList<GuestBook>();
		DB db = new DB();
		String sql = "select * from GuestBook where 1=1";
		if (!qustion.equals(""))
			sql += " and qustion like '%" + qustion + "%'";
		if (!student.equals(""))
			sql += " and student = '" + student + "'";
		if (!teacherObj.equals(""))
			sql += " and teacherObj = '" + teacherObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				GuestBook guestBook = new GuestBook();
				guestBook.setGuestBookId(rs.getInt("guestBookId"));
				guestBook.setQustion(rs.getString("qustion"));
				guestBook.setStudent(rs.getString("student"));
				guestBook.setQuestionTime(rs.getString("questionTime"));
				guestBook.setReply(rs.getString("reply"));
				guestBook.setTeacherObj(rs.getString("teacherObj"));
				guestBook.setReplyTime(rs.getString("replyTime"));
				guestBook.setReplyFlag(rs.getInt("replyFlag"));
				guestBookList.add(guestBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return guestBookList;
	}
	/* 传入留言交流对象，进行留言交流的添加业务 */
	public String AddGuestBook(GuestBook guestBook) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新留言交流 */
			String sqlString = "insert into GuestBook(qustion,student,questionTime,reply,teacherObj,replyTime,replyFlag) values (";
			sqlString += "'" + guestBook.getQustion() + "',";
			sqlString += "'" + guestBook.getStudent() + "',";
			sqlString += "'" + guestBook.getQuestionTime() + "',";
			sqlString += "'" + guestBook.getReply() + "',";
			sqlString += "'" + guestBook.getTeacherObj() + "',";
			sqlString += "'" + guestBook.getReplyTime() + "',";
			sqlString += guestBook.getReplyFlag();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "留言交流添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "留言交流添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除留言交流 */
	public String DeleteGuestBook(int guestBookId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from GuestBook where guestBookId=" + guestBookId;
			db.executeUpdate(sqlString);
			result = "留言交流删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "留言交流删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录id获取到留言交流 */
	public GuestBook GetGuestBook(int guestBookId) {
		GuestBook guestBook = null;
		DB db = new DB();
		String sql = "select * from GuestBook where guestBookId=" + guestBookId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				guestBook = new GuestBook();
				guestBook.setGuestBookId(rs.getInt("guestBookId"));
				guestBook.setQustion(rs.getString("qustion"));
				guestBook.setStudent(rs.getString("student"));
				guestBook.setQuestionTime(rs.getString("questionTime"));
				guestBook.setReply(rs.getString("reply"));
				guestBook.setTeacherObj(rs.getString("teacherObj"));
				guestBook.setReplyTime(rs.getString("replyTime"));
				guestBook.setReplyFlag(rs.getInt("replyFlag"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return guestBook;
	}
	/* 更新留言交流 */
	public String UpdateGuestBook(GuestBook guestBook) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update GuestBook set ";
			sql += "qustion='" + guestBook.getQustion() + "',";
			sql += "student='" + guestBook.getStudent() + "',";
			sql += "questionTime='" + guestBook.getQuestionTime() + "',";
			sql += "reply='" + guestBook.getReply() + "',";
			sql += "teacherObj='" + guestBook.getTeacherObj() + "',";
			sql += "replyTime='" + guestBook.getReplyTime() + "',";
			sql += "replyFlag=" + guestBook.getReplyFlag();
			sql += " where guestBookId=" + guestBook.getGuestBookId();
			db.executeUpdate(sql);
			result = "留言交流更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "留言交流更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
