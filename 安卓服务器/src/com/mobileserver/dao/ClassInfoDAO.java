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
	/* 传入班级信息对象，进行班级信息的添加业务 */
	public String AddClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新班级信息 */
			String sqlString = "insert into ClassInfo(classNumber,specialName,className,startDate,headTeacher) values (";
			sqlString += "'" + classInfo.getClassNumber() + "',";
			sqlString += "'" + classInfo.getSpecialName() + "',";
			sqlString += "'" + classInfo.getClassName() + "',";
			sqlString += "'" + classInfo.getStartDate() + "',";
			sqlString += "'" + classInfo.getHeadTeacher() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "班级信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "班级信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除班级信息 */
	public String DeleteClassInfo(String classNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ClassInfo where classNumber='" + classNumber + "'";
			db.executeUpdate(sqlString);
			result = "班级信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "班级信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据班级编号获取到班级信息 */
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
	/* 更新班级信息 */
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
			result = "班级信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "班级信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
