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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Subject GetSubjectBySubjectId(int subjectId) {
        Session s = factory.getCurrentSession();
        Subject subject = (Subject)s.get(Subject.class, subjectId);
        return subject;
    }

    /*����Subject��Ϣ*/
    public void UpdateSubject(Subject subject) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(subject);
    }

    /*ɾ��Subject��Ϣ*/
    public void DeleteSubject (int subjectId) throws Exception {
        Session s = factory.getCurrentSession();
        Object subject = s.load(Subject.class, subjectId);
        s.delete(subject);
    }

}
