package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SubjectType;
import com.mobileserver.util.DB;

public class SubjectTypeDAO {

	public List<SubjectType> QuerySubjectType() {
		List<SubjectType> subjectTypeList = new ArrayList<SubjectType>();
		DB db = new DB();
		String sql = "select * from SubjectType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SubjectType subjectType = new SubjectType();
				subjectType.setSubjectTypeId(rs.getInt("subjectTypeId"));
				subjectType.setSubjectTypeName(rs.getString("subjectTypeName"));
				subjectTypeList.add(subjectType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return subjectTypeList;
	}
	/* 传入题目类型对象，进行题目类型的添加业务 */
	public String AddSubjectType(SubjectType subjectType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新题目类型 */
			String sqlString = "insert into SubjectType(subjectTypeName) values (";
			sqlString += "'" + subjectType.getSubjectTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "题目类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "题目类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除题目类型 */
	public String DeleteSubjectType(int subjectTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SubjectType where subjectTypeId=" + subjectTypeId;
			db.executeUpdate(sqlString);
			result = "题目类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "题目类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据类型编号获取到题目类型 */
	public SubjectType GetSubjectType(int subjectTypeId) {
		SubjectType subjectType = null;
		DB db = new DB();
		String sql = "select * from SubjectType where subjectTypeId=" + subjectTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				subjectType = new SubjectType();
				subjectType.setSubjectTypeId(rs.getInt("subjectTypeId"));
				subjectType.setSubjectTypeName(rs.getString("subjectTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return subjectType;
	}
	/* 更新题目类型 */
	public String UpdateSubjectType(SubjectType subjectType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SubjectType set ";
			sql += "subjectTypeName='" + subjectType.getSubjectTypeName() + "'";
			sql += " where subjectTypeId=" + subjectType.getSubjectTypeId();
			db.executeUpdate(sql);
			result = "题目类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "题目类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
