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
	/* ������Ŀ���Ͷ��󣬽�����Ŀ���͵����ҵ�� */
	public String AddSubjectType(SubjectType subjectType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ŀ���� */
			String sqlString = "insert into SubjectType(subjectTypeName) values (";
			sqlString += "'" + subjectType.getSubjectTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ŀ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ŀ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ŀ���� */
	public String DeleteSubjectType(int subjectTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SubjectType where subjectTypeId=" + subjectTypeId;
			db.executeUpdate(sqlString);
			result = "��Ŀ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ŀ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �������ͱ�Ż�ȡ����Ŀ���� */
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
	/* ������Ŀ���� */
	public String UpdateSubjectType(SubjectType subjectType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SubjectType set ";
			sql += "subjectTypeName='" + subjectType.getSubjectTypeName() + "'";
			sql += " where subjectTypeId=" + subjectType.getSubjectTypeId();
			db.executeUpdate(sql);
			result = "��Ŀ���͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ŀ���͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
