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
import com.chengxusheji.dao.SelectItemDAO;
import com.chengxusheji.domain.SelectItem;
import com.chengxusheji.dao.SubjectDAO;
import com.chengxusheji.domain.Subject;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SelectItemAction extends BaseAction {

    /*界面层需要查询的属性: 题目*/
    private Subject subjectObj;
    public void setSubjectObj(Subject subjectObj) {
        this.subjectObj = subjectObj;
    }
    public Subject getSubjectObj() {
        return this.subjectObj;
    }

    /*界面层需要查询的属性: 学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
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

    private int selectItemId;
    public void setSelectItemId(int selectItemId) {
        this.selectItemId = selectItemId;
    }
    public int getSelectItemId() {
        return selectItemId;
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
    @Resource SubjectDAO subjectDAO;
    @Resource StudentDAO studentDAO;
    @Resource SelectItemDAO selectItemDAO;

    /*待操作的SelectItem对象*/
    private SelectItem selectItem;
    public void setSelectItem(SelectItem selectItem) {
        this.selectItem = selectItem;
    }
    public SelectItem getSelectItem() {
        return this.selectItem;
    }

    /*跳转到添加SelectItem视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Subject信息*/
        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*添加SelectItem信息*/
    @SuppressWarnings("deprecation")
    public String AddSelectItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Subject subjectObj = subjectDAO.GetSubjectBySubjectId(selectItem.getSubjectObj().getSubjectId());
            selectItem.setSubjectObj(subjectObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(selectItem.getStudentObj().getStudentNumber());
            selectItem.setStudentObj(studentObj);
            selectItemDAO.AddSelectItem(selectItem);
            ctx.put("message",  java.net.URLEncoder.encode("SelectItem添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectItem添加失败!"));
            return "error";
        }
    }

    /*查询SelectItem信息*/
    public String QuerySelectItem() {
        if(currentPage == 0) currentPage = 1;
        List<SelectItem> selectItemList = selectItemDAO.QuerySelectItemInfo(subjectObj, studentObj, currentPage);
        /*计算总的页数和总的记录数*/
        selectItemDAO.CalculateTotalPageAndRecordNumber(subjectObj, studentObj);
        /*获取到总的页码数目*/
        totalPage = selectItemDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = selectItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("selectItemList",  selectItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("subjectObj", subjectObj);
        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySelectItemOutputToExcel() { 
        List<SelectItem> selectItemList = selectItemDAO.QuerySelectItemInfo(subjectObj,studentObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SelectItem信息记录"; 
        String[] headers = { "选题id","题目","学生","选题时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<selectItemList.size();i++) {
        	SelectItem selectItem = selectItemList.get(i); 
        	dataset.add(new String[]{selectItem.getSelectItemId() + "",selectItem.getSubjectObj().getSubjectName(),
selectItem.getStudentObj().getName(),
selectItem.getSelectTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SelectItem.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SelectItem信息*/
    public String FrontQuerySelectItem() {
        if(currentPage == 0) currentPage = 1;
        List<SelectItem> selectItemList = selectItemDAO.QuerySelectItemInfo(subjectObj, studentObj, currentPage);
        /*计算总的页数和总的记录数*/
        selectItemDAO.CalculateTotalPageAndRecordNumber(subjectObj, studentObj);
        /*获取到总的页码数目*/
        totalPage = selectItemDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = selectItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("selectItemList",  selectItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("subjectObj", subjectObj);
        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "front_query_view";
    }

    /*查询要修改的SelectItem信息*/
    public String ModifySelectItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectItemId获取SelectItem对象*/
        SelectItem selectItem = selectItemDAO.GetSelectItemBySelectItemId(selectItemId);

        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("selectItem",  selectItem);
        return "modify_view";
    }

    /*查询要修改的SelectItem信息*/
    public String FrontShowSelectItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectItemId获取SelectItem对象*/
        SelectItem selectItem = selectItemDAO.GetSelectItemBySelectItemId(selectItemId);

        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("selectItem",  selectItem);
        return "front_show_view";
    }

    /*更新修改SelectItem信息*/
    public String ModifySelectItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Subject subjectObj = subjectDAO.GetSubjectBySubjectId(selectItem.getSubjectObj().getSubjectId());
            selectItem.setSubjectObj(subjectObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(selectItem.getStudentObj().getStudentNumber());
            selectItem.setStudentObj(studentObj);
            selectItemDAO.UpdateSelectItem(selectItem);
            ctx.put("message",  java.net.URLEncoder.encode("SelectItem信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectItem信息更新失败!"));
            return "error";
       }
   }

    /*删除SelectItem信息*/
    public String DeleteSelectItem() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            selectItemDAO.DeleteSelectItem(selectItemId);
            ctx.put("message",  java.net.URLEncoder.encode("SelectItem删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectItem删除失败!"));
            return "error";
        }
    }

}
