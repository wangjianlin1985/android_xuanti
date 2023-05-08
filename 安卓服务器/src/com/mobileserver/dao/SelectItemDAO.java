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
	/* 传入学生选题对象，进行学生选题的添加业务 */
	public String AddSelectItem(SelectItem selectItem) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新学生选题 */
			String sqlString = "insert into SelectItem(subjectObj,studentObj,selectTime) values (";
			sqlString += selectItem.getSubjectObj() + ",";
			sqlString += "'" + selectItem.getStudentObj() + "',";
			sqlString += "'" + selectItem.getSelectTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "学生选题添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学生选题添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除学生选题 */
	public String DeleteSelectItem(int selectItemId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SelectItem where selectItemId=" + selectItemId;
			db.executeUpdate(sqlString);
			result = "学生选题删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学生选题删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据选题id获取到学生选题 */
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
	/* 更新学生选题 */
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
			result = "学生选题更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学生选题更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
