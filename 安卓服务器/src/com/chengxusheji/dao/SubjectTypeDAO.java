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
import com.chengxusheji.domain.SubjectType;

@Service @Transactional
public class SubjectTypeDAO {

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
    public void AddSubjectType(SubjectType subjectType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(subjectType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SubjectType> QuerySubjectTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SubjectType subjectType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List subjectTypeList = q.list();
    	return (ArrayList<SubjectType>) subjectTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SubjectType> QuerySubjectTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SubjectType subjectType where 1=1";
    	Query q = s.createQuery(hql);
    	List subjectTypeList = q.list();
    	return (ArrayList<SubjectType>) subjectTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SubjectType> QueryAllSubjectTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SubjectType";
        Query q = s.createQuery(hql);
        List subjectTypeList = q.list();
        return (ArrayList<SubjectType>) subjectTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SubjectType subjectType where 1=1";
        Query q = s.createQuery(hql);
        List subjectTypeList = q.list();
        recordNumber = subjectTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SubjectType GetSubjectTypeBySubjectTypeId(int subjectTypeId) {
        Session s = factory.getCurrentSession();
        SubjectType subjectType = (SubjectType)s.get(SubjectType.class, subjectTypeId);
        return subjectType;
    }

    /*更新SubjectType信息*/
    public void UpdateSubjectType(SubjectType subjectType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(subjectType);
    }

    /*删除SubjectType信息*/
    public void DeleteSubjectType (int subjectTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object subjectType = s.load(SubjectType.class, subjectTypeId);
        s.delete(subjectType);
    }

}
