package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ClassInfo;
import com.mobileserver.util.DB;

public class ClassInfoDAO {

	public List<ClassInfo> QueryClassInfo(String classNumber,String specialName,String className,Timestamp startDate) {
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		DB db = new DB();
		String sql = "select * from ClassInfo where 1=1";
		if (!classNumber.equals(""))
			sql += " and classNumber like '%" + classNumber + "%'";
		if (!specialName.equals(""))
			sql += " and specialName like '%" + specialName + "%'";
		if (!className.equals(""))
			sql += " and className like '%" + className + "%'";
		if(startDate!=null)
			sql += " and startDate='" + startDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNumber(rs.getString("classNumber"));
				classInfo.setSpecialName(rs.getString("specialName"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setStartDate(rs.getTimestamp("startDate"));
				classInfo.setHeadTeacher(rs.getString("headTeacher"));
				classInfoList.add(classInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfoList;
	}
	/* ����༶��Ϣ���󣬽��а༶��Ϣ�����ҵ�� */
	public String AddClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����°༶��Ϣ */
			String sqlString = "insert into ClassInfo(classNumber,specialName,className,startDate,headTeacher) values (";
			sqlString += "'" + classInfo.getClassNumber() + "',";
			sqlString += "'" + classInfo.getSpecialName() + "',";
			sqlString += "'" + classInfo.getClassName() + "',";
			sqlString += "'" + classInfo.getStartDate() + "',";
			sqlString += "'" + classInfo.getHeadTeacher() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�༶��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���༶��Ϣ */
	public String DeleteClassInfo(String classNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ClassInfo where classNumber='" + classNumber + "'";
			db.executeUpdate(sqlString);
			result = "�༶��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݰ༶��Ż�ȡ���༶��Ϣ */
	public ClassInfo GetClassInfo(String classNumber) {
		ClassInfo classInfo = null;
		DB db = new DB();
		String sql = "select * from ClassInfo where classNumber='" + classNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				classInfo = new ClassInfo();
				classInfo.setClassNumber(rs.getString("classNumber"));
				classInfo.setSpecialName(rs.getString("specialName"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setStartDate(rs.getTimestamp("startDate"));
				classInfo.setHeadTeacher(rs.getString("headTeacher"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfo;
	}
	/* ���°༶��Ϣ */
	public String UpdateClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ClassInfo set ";
			sql += "specialName='" + classInfo.getSpecialName() + "',";
			sql += "className='" + classInfo.getClassName() + "',";
			sql += "startDate='" + classInfo.getStartDate() + "',";
			sql += "headTeacher='" + classInfo.getHeadTeacher() + "'";
			sql += " where classNumber='" + classInfo.getClassNumber() + "'";
			db.executeUpdate(sql);
			result = "�༶��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
