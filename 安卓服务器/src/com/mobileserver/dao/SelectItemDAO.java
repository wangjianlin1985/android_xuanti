package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SelectItem;
import com.mobileserver.util.DB;

public class SelectItemDAO {

	public List<SelectItem> QuerySelectItem(int subjectObj,String studentObj) {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		DB db = new DB();
		String sql = "select * from SelectItem where 1=1";
		if (subjectObj != 0)
			sql += " and subjectObj=" + subjectObj;
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SelectItem selectItem = new SelectItem();
				selectItem.setSelectItemId(rs.getInt("selectItemId"));
				selectItem.setSubjectObj(rs.getInt("subjectObj"));
				selectItem.setStudentObj(rs.getString("studentObj"));
				selectItem.setSelectTime(rs.getString("selectTime"));
				selectItemList.add(selectItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return selectItemList;
	}
	/* ����ѧ��ѡ����󣬽���ѧ��ѡ������ҵ�� */
	public String AddSelectItem(SelectItem selectItem) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧ��ѡ�� */
			String sqlString = "insert into SelectItem(subjectObj,studentObj,selectTime) values (";
			sqlString += selectItem.getSubjectObj() + ",";
			sqlString += "'" + selectItem.getStudentObj() + "',";
			sqlString += "'" + selectItem.getSelectTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧ��ѡ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ��ѡ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧ��ѡ�� */
	public String DeleteSelectItem(int selectItemId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SelectItem where selectItemId=" + selectItemId;
			db.executeUpdate(sqlString);
			result = "ѧ��ѡ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ��ѡ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѡ��id��ȡ��ѧ��ѡ�� */
	public SelectItem GetSelectItem(int selectItemId) {
		SelectItem selectItem = null;
		DB db = new DB();
		String sql = "select * from SelectItem where selectItemId=" + selectItemId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				selectItem = new SelectItem();
				selectItem.setSelectItemId(rs.getInt("selectItemId"));
				selectItem.setSubjectObj(rs.getInt("subjectObj"));
				selectItem.setStudentObj(rs.getString("studentObj"));
				selectItem.setSelectTime(rs.getString("selectTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return selectItem;
	}
	/* ����ѧ��ѡ�� */
	public String UpdateSelectItem(SelectItem selectItem) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SelectItem set ";
			sql += "subjectObj=" + selectItem.getSubjectObj() + ",";
			sql += "studentObj='" + selectItem.getStudentObj() + "',";
			sql += "selectTime='" + selectItem.getSelectTime() + "'";
			sql += " where selectItemId=" + selectItem.getSelectItemId();
			db.executeUpdate(sql);
			result = "ѧ��ѡ����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ��ѡ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
