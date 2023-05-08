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
    public void AddSubjectType(SubjectType subjectType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(subjectType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SubjectType> QuerySubjectTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SubjectType subjectType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SubjectType GetSubjectTypeBySubjectTypeId(int subjectTypeId) {
        Session s = factory.getCurrentSession();
        SubjectType subjectType = (SubjectType)s.get(SubjectType.class, subjectTypeId);
        return subjectType;
    }

    /*����SubjectType��Ϣ*/
    public void UpdateSubjectType(SubjectType subjectType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(subjectType);
    }

    /*ɾ��SubjectType��Ϣ*/
    public void DeleteSubjectType (int subjectTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object subjectType = s.load(SubjectType.class, subjectTypeId);
        s.delete(subjectType);
    }

}
