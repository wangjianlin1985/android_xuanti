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

    /*界面层需要查询的属性: 题目名称*/
    private String subjectName;
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectName() {
        return this.subjectName;
    }

    /*界面层需要查询的属性: 题目类型*/
    private SubjectType subjectTypeObj;
    public void setSubjectTypeObj(SubjectType subjectTypeObj) {
        this.subjectTypeObj = subjectTypeObj;
    }
    public SubjectType getSubjectTypeObj() {
        return this.subjectTypeObj;
    }

    /*界面层需要查询的属性: 指导老师*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
    }

    /*界面层需要查询的属性: 发布时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource SubjectTypeDAO subjectTypeDAO;
    @Resource TeacherDAO teacherDAO;
    @Resource SubjectDAO subjectDAO;

    /*待操作的Subject对象*/
    private Subject subject;
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public Subject getSubject() {
        return this.subject;
    }

    /*跳转到添加Subject视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的SubjectType信息*/
        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加Subject信息*/
    @SuppressWarnings("deprecation")
    public String AddSubject() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SubjectType subjectTypeObj = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subject.getSubjectTypeObj().getSubjectTypeId());
            subject.setSubjectTypeObj(subjectTypeObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(subject.getTeacherObj().getTeacherNumber());
            subject.setTeacherObj(teacherObj);
            subjectDAO.AddSubject(subject);
            ctx.put("message",  java.net.URLEncoder.encode("Subject添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Subject添加失败!"));
            return "error";
        }
    }

    /*查询Subject信息*/
    public String QuerySubject() {
        if(currentPage == 0) currentPage = 1;
        if(subjectName == null) subjectName = "";
        if(addTime == null) addTime = "";
        List<Subject> subjectList = subjectDAO.QuerySubjectInfo(subjectName, subjectTypeObj, teacherObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        subjectDAO.CalculateTotalPageAndRecordNumber(subjectName, subjectTypeObj, teacherObj, addTime);
        /*获取到总的页码数目*/
        totalPage = subjectDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QuerySubjectOutputToExcel() { 
        if(subjectName == null) subjectName = "";
        if(addTime == null) addTime = "";
        List<Subject> subjectList = subjectDAO.QuerySubjectInfo(subjectName,subjectTypeObj,teacherObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Subject信息记录"; 
        String[] headers = { "题目编号","题目名称","题目类型","题目内容","限选人数","指导老师","发布时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Subject.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Subject信息*/
    public String FrontQuerySubject() {
        if(currentPage == 0) currentPage = 1;
        if(subjectName == null) subjectName = "";
        if(addTime == null) addTime = "";
        List<Subject> subjectList = subjectDAO.QuerySubjectInfo(subjectName, subjectTypeObj, teacherObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        subjectDAO.CalculateTotalPageAndRecordNumber(subjectName, subjectTypeObj, teacherObj, addTime);
        /*获取到总的页码数目*/
        totalPage = subjectDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Subject信息*/
    public String ModifySubjectQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键subjectId获取Subject对象*/
        Subject subject = subjectDAO.GetSubjectBySubjectId(subjectId);

        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("subject",  subject);
        return "modify_view";
    }

    /*查询要修改的Subject信息*/
    public String FrontShowSubjectQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键subjectId获取Subject对象*/
        Subject subject = subjectDAO.GetSubjectBySubjectId(subjectId);

        List<SubjectType> subjectTypeList = subjectTypeDAO.QueryAllSubjectTypeInfo();
        ctx.put("subjectTypeList", subjectTypeList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("subject",  subject);
        return "front_show_view";
    }

    /*更新修改Subject信息*/
    public String ModifySubject() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SubjectType subjectTypeObj = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subject.getSubjectTypeObj().getSubjectTypeId());
            subject.setSubjectTypeObj(subjectTypeObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(subject.getTeacherObj().getTeacherNumber());
            subject.setTeacherObj(teacherObj);
            subjectDAO.UpdateSubject(subject);
            ctx.put("message",  java.net.URLEncoder.encode("Subject信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Subject信息更新失败!"));
            return "error";
       }
   }

    /*删除Subject信息*/
    public String DeleteSubject() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            subjectDAO.DeleteSubject(subjectId);
            ctx.put("message",  java.net.URLEncoder.encode("Subject删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Subject删除失败!"));
            return "error";
        }
    }

}
