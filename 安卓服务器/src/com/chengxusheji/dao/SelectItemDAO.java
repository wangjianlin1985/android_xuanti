package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Subject;
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.SelectItem;

@Service @Transactional
public class SelectItemDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddSelectItem(SelectItem selectItem) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(selectItem);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SelectItem> QuerySelectItemInfo(Subject subjectObj,Student studentObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SelectItem selectItem where 1=1";
    	if(null != subjectObj && subjectObj.getSubjectId()!=0) hql += " and selectItem.subjectObj.subjectId=" + subjectObj.getSubjectId();
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and selectItem.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List selectItemList = q.list();
    	return (ArrayList<SelectItem>) selectItemList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SelectItem> QuerySelectItemInfo(Subject subjectObj,Student studentObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SelectItem selectItem where 1=1";
    	if(null != subjectObj && subjectObj.getSubjectId()!=0) hql += " and selectItem.subjectObj.subjectId=" + subjectObj.getSubjectId();
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and selectItem.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	Query q = s.createQuery(hql);
    	List selectItemList = q.list();
    	return (ArrayList<SelectItem>) selectItemList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SelectItem> QueryAllSelectItemInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SelectItem";
        Query q = s.createQuery(hql);
        List selectItemList = q.list();
        return (ArrayList<SelectItem>) selectItemList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Subject subjectObj,Student studentObj) {
        Session s = factory.getCurrentSession();
        String hql = "From SelectItem selectItem where 1=1";
        if(null != subjectObj && subjectObj.getSubjectId()!=0) hql += " and selectItem.subjectObj.subjectId=" + subjectObj.getSubjectId();
        if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and selectItem.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
        Query q = s.createQuery(hql);
        List selectItemList = q.list();
        recordNumber = selectItemList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SelectItem GetSelectItemBySelectItemId(int selectItemId) {
        Session s = factory.getCurrentSession();
        SelectItem selectItem = (SelectItem)s.get(SelectItem.class, selectItemId);
        return selectItem;
    }

    /*更新SelectItem信息*/
    public void UpdateSelectItem(SelectItem selectItem) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(selectItem);
    }

    /*删除SelectItem信息*/
    public void DeleteSelectItem (int selectItemId) throws Exception {
        Session s = factory.getCurrentSession();
        Object selectItem = s.load(SelectItem.class, selectItemId);
        s.delete(selectItem);
    }

}
