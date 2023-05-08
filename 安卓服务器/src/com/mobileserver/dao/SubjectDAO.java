package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Subject;
import com.mobileserver.util.DB;

public class SubjectDAO {

	public List<Subject> QuerySubject(String subjectName,int subjectTypeObj,String teacherObj,String addTime) {
		List<Subject> subjectList = new ArrayList<Subject>();
		DB db = new DB();
		String sql = "select * from Subject where 1=1";
		if (!subjectName.equals(""))
			sql += " and subjectName like '%" + subjectName + "%'";
		if (subjectTypeObj != 0)
			sql += " and subjectTypeObj=" + subjectTypeObj;
		if (!teacherObj.equals(""))
			sql += " and teacherObj = '" + teacherObj + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Subject subject = new Subject();
				subject.setSubjectId(rs.getInt("subjectId"));
				subject.setSubjectName(rs.getString("subjectName"));
				subject.setSubjectTypeObj(rs.getInt("subjectTypeObj"));
				subject.setContent(rs.getString("content"));
				subject.setStudentNumber(rs.getInt("studentNumber"));
				subject.setTeacherObj(rs.getString("teacherObj"));
				subject.setAddTime(rs.getString("addTime"));
				subjectList.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return subjectList;
	}
	/* 传入题目信息对象，进行题目信息的添加业务 */
	public String AddSubject(Subject subject) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新题目信息 */
			String sqlString = "insert into Subject(subjectName,subjectTypeObj,content,studentNumber,teacherObj,addTime) values (";
			sqlString += "'" + subject.getSubjectName() + "',";
			sqlString += subject.getSubjectTypeObj() + ",";
			sqlString += "'" + subject.getContent() + "',";
			sqlString += subject.getStudentNumber() + ",";
			sqlString += "'" + subject.getTeacherObj() + "',";
			sqlString += "'" + subject.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "题目信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "题目信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除题目信息 */
	public String DeleteSubject(int subjectId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Subject where subjectId=" + subjectId;
			db.executeUpdate(sqlString);
			result = "题目信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "题目信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据题目编号获取到题目信息 */
	public Subject GetSubject(int subjectId) {
		Subject subject = null;
		DB db = new DB();
		String sql = "select * from Subject where subjectId=" + subjectId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				subject = new Subject();
				subject.setSubjectId(rs.getInt("subjectId"));
				subject.setSubjectName(rs.getString("subjectName"));
				subject.setSubjectTypeObj(rs.getInt("subjectTypeObj"));
				subject.setContent(rs.getString("content"));
				subject.setStudentNumber(rs.getInt("studentNumber"));
				subject.setTeacherObj(rs.getString("teacherObj"));
				subject.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return subject;
	}
	/* 更新题目信息 */
	public String UpdateSubject(Subject subject) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Subject set ";
			sql += "subjectName='" + subject.getSubjectName() + "',";
			sql += "subjectTypeObj=" + subject.getSubjectTypeObj() + ",";
			sql += "content='" + subject.getContent() + "',";
			sql += "studentNumber=" + subject.getStudentNumber() + ",";
			sql += "teacherObj='" + subject.getTeacherObj() + "',";
			sql += "addTime='" + subject.getAddTime() + "'";
			sql += " where subjectId=" + subject.getSubjectId();
			db.executeUpdate(sql);
			result = "题目信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "题目信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
