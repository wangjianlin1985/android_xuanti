package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.SubjectDAO;
import com.chengxusheji.domain.Subject;
import com.chengxusheji.dao.SubjectTypeDAO;
import com.chengxusheji.domain.SubjectType;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SubjectAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��Ŀ����*/
    private String subjectName;
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectName() {
        return this.subjectName;
    }

    /*�������Ҫ��ѯ������: ��Ŀ����*/
    private SubjectType subjectTypeObj;
    public void setSubjectTypeObj(SubjectType subjectTypeObj) {
        this.subjectTypeObj = subjectTypeObj;
    }
    public SubjectType getSubjectTypeObj() {
        return this.subjectTypeObj;
    }

    /*�������Ҫ��ѯ������: ָ����ʦ*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int subjectId;
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    public int getSubjectId() {
        return subjectId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource SubjectTypeDAO subjectTypeDAO;
    @Resource TeacherDAO teacherDAO;
    @Resource SubjectDAO subjectDAO;

    /*��������Subject����*/
    private Subject subject;
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public Subject getSubject() {
        return this.subject;
    }

    /*��ת�����Subject��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�SubjectType��Ϣ*/
        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        /*��ѯ���е�Teacher��Ϣ*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*���Subject��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSubject() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SubjectType subjectTypeObj = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subject.getSubjectTypeObj().getSubjectTypeId());
            subject.setSubjectTypeObj(subjectTypeObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(subject.getTeacherObj().getTeacherNumber());
            subject.setTeacherObj(teacherObj);
            subjectDAO.AddSubject(subject);
            ctx.put("message",  java.net.URLEncoder.encode("Subject��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Subject���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSubject��Ϣ*/
    public String QuerySubject() {
        if(currentPage == 0) currentPage = 1;
        if(subjectName == null) subjectName = "";
        if(addTime == null) addTime = "";
        List<Subject> subjectList = subjectDAO.QuerySubjectInfo(subjectName, subjectTypeObj, teacherObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        subjectDAO.CalculateTotalPageAndRecordNumber(subjectName, subjectTypeObj, teacherObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = subjectDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = subjectDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("subjectList",  subjectList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("subjectName", subjectName);
        ctx.put("subjectTypeObj", subjectTypeObj);
        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySubjectOutputToExcel() { 
        if(subjectName == null) subjectName = "";
        if(addTime == null) addTime = "";
        List<Subject> subjectList = subjectDAO.QuerySubjectInfo(subjectName,subjectTypeObj,teacherObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Subject��Ϣ��¼"; 
        String[] headers = { "��Ŀ���","��Ŀ����","��Ŀ����","��Ŀ����","��ѡ����","ָ����ʦ","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<subjectList.size();i++) {
        	Subject subject = subjectList.get(i); 
        	dataset.add(new String[]{subject.getSubjectId() + "",subject.getSubjectName(),subject.getSubjectTypeObj().getSubjectTypeName(),
subject.getContent(),subject.getStudentNumber() + "",subject.getTeacherObj().getName(),
subject.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Subject.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯSubject��Ϣ*/
    public String FrontQuerySubject() {
        if(currentPage == 0) currentPage = 1;
        if(subjectName == null) subjectName = "";
        if(addTime == null) addTime = "";
        List<Subject> subjectList = subjectDAO.QuerySubjectInfo(subjectName, subjectTypeObj, teacherObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        subjectDAO.CalculateTotalPageAndRecordNumber(subjectName, subjectTypeObj, teacherObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = subjectDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = subjectDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("subjectList",  subjectList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("subjectName", subjectName);
        ctx.put("subjectTypeObj", subjectTypeObj);
        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Subject��Ϣ*/
    public String ModifySubjectQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������subjectId��ȡSubject����*/
        Subject subject = subjectDAO.GetSubjectBySubjectId(subjectId);

        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("subject",  subject);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Subject��Ϣ*/
    public String FrontShowSubjectQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������subjectId��ȡSubject����*/
        Subject subject = subjectDAO.GetSubjectBySubjectId(subjectId);

        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("subject",  subject);
        return "front_show_view";
    }

    /*�����޸�Subject��Ϣ*/
    public String ModifySubject() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SubjectType subjectTypeObj = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subject.getSubjectTypeObj().getSubjectTypeId());
            subject.setSubjectTypeObj(subjectTypeObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(subject.getTeacherObj().getTeacherNumber());
            subject.setTeacherObj(teacherObj);
            subjectDAO.UpdateSubject(subject);
            ctx.put("message",  java.net.URLEncoder.encode("Subject��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Subject��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Subject��Ϣ*/
    public String DeleteSubject() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            subjectDAO.DeleteSubject(subjectId);
            ctx.put("message",  java.net.URLEncoder.encode("Subjectɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Subjectɾ��ʧ��!"));
            return "error";
        }
    }

}
