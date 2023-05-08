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
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.domain.Subject;

@Service @Transactional
public class SubjectDAO {

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
    public void AddSubject(Subject subject) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(subject);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Subject> QuerySubjectInfo(String subjectName,SubjectType subjectTypeObj,Teacher teacherObj,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Subject subject where 1=1";
    	if(!subjectName.equals("")) hql = hql + " and subject.subjectName like '%" + subjectName + "%'";
    	if(null != subjectTypeObj && subjectTypeObj.getSubjectTypeId()!=0) hql += " and subject.subjectTypeObj.subjectTypeId=" + subjectTypeObj.getSubjectTypeId();
    	if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and subject.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
    	if(!addTime.equals("")) hql = hql + " and subject.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List subjectList = q.list();
    	return (ArrayList<Subject>) subjectList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Subject> QuerySubjectInfo(String subjectName,SubjectType subjectTypeObj,Teacher teacherObj,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Subject subject where 1=1";
    	if(!subjectName.equals("")) hql = hql + " and subject.subjectName like '%" + subjectName + "%'";
    	if(null != subjectTypeObj && subjectTypeObj.getSubjectTypeId()!=0) hql += " and subject.subjectTypeObj.subjectTypeId=" + subjectTypeObj.getSubjectTypeId();
    	if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and subject.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
    	if(!addTime.equals("")) hql = hql + " and subject.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List subjectList = q.list();
    	return (ArrayList<Subject>) subjectList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Subject> QueryAllSubjectInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Subject";
        Query q = s.createQuery(hql);
        List subjectList = q.list();
        return (ArrayList<Subject>) subjectList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String subjectName,SubjectType subjectTypeObj,Teacher teacherObj,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Subject subject where 1=1";
        if(!subjectName.equals("")) hql = hql + " and subject.subjectName like '%" + subjectName + "%'";
        if(null != subjectTypeObj && subjectTypeObj.getSubjectTypeId()!=0) hql += " and subject.subjectTypeObj.subjectTypeId=" + subjectTypeObj.getSubjectTypeId();
        if(null != teacherObj && !teacherObj.getTeacherNumber().equals("")) hql += " and subject.teacherObj.teacherNumber='" + teacherObj.getTeacherNumber() + "'";
        if(!addTime.equals("")) hql = hql + " and subject.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List subjectList = q.list();
        recordNumber = subjectList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Subject GetSubjectBySubjectId(int subjectId) {
        Session s = factory.getCurrentSession();
        Subject subject = (Subject)s.get(Subject.class, subjectId);
        return subject;
    }

    /*更新Subject信息*/
    public void UpdateSubject(Subject subject) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(subject);
    }

    /*删除Subject信息*/
    public void DeleteSubject (int subjectId) throws Exception {
        Session s = factory.getCurrentSession();
        Object subject = s.load(Subject.class, subjectId);
        s.delete(subject);
    }

}
