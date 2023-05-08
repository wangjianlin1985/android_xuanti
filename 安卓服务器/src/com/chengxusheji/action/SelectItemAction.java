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

    /*�������Ҫ��ѯ������: ��Ŀ*/
    private Subject subjectObj;
    public void setSubjectObj(Subject subjectObj) {
        this.subjectObj = subjectObj;
    }
    public Subject getSubjectObj() {
        return this.subjectObj;
    }

    /*�������Ҫ��ѯ������: ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
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

    private int selectItemId;
    public void setSelectItemId(int selectItemId) {
        this.selectItemId = selectItemId;
    }
    public int getSelectItemId() {
        return selectItemId;
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
    @Resource SubjectDAO subjectDAO;
    @Resource StudentDAO studentDAO;
    @Resource SelectItemDAO selectItemDAO;

    /*��������SelectItem����*/
    private SelectItem selectItem;
    public void setSelectItem(SelectItem selectItem) {
        this.selectItem = selectItem;
    }
    public SelectItem getSelectItem() {
        return this.selectItem;
    }

    /*��ת�����SelectItem��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Subject��Ϣ*/
        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*���SelectItem��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSelectItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Subject subjectObj = subjectDAO.GetSubjectBySubjectId(selectItem.getSubjectObj().getSubjectId());
            selectItem.setSubjectObj(subjectObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(selectItem.getStudentObj().getStudentNumber());
            selectItem.setStudentObj(studentObj);
            selectItemDAO.AddSelectItem(selectItem);
            ctx.put("message",  java.net.URLEncoder.encode("SelectItem��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectItem���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSelectItem��Ϣ*/
    public String QuerySelectItem() {
        if(currentPage == 0) currentPage = 1;
        List<SelectItem> selectItemList = selectItemDAO.QuerySelectItemInfo(subjectObj, studentObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        selectItemDAO.CalculateTotalPageAndRecordNumber(subjectObj, studentObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = selectItemDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QuerySelectItemOutputToExcel() { 
        List<SelectItem> selectItemList = selectItemDAO.QuerySelectItemInfo(subjectObj,studentObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SelectItem��Ϣ��¼"; 
        String[] headers = { "ѡ��id","��Ŀ","ѧ��","ѡ��ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SelectItem.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSelectItem��Ϣ*/
    public String FrontQuerySelectItem() {
        if(currentPage == 0) currentPage = 1;
        List<SelectItem> selectItemList = selectItemDAO.QuerySelectItemInfo(subjectObj, studentObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        selectItemDAO.CalculateTotalPageAndRecordNumber(subjectObj, studentObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = selectItemDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�SelectItem��Ϣ*/
    public String ModifySelectItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectItemId��ȡSelectItem����*/
        SelectItem selectItem = selectItemDAO.GetSelectItemBySelectItemId(selectItemId);

        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("selectItem",  selectItem);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SelectItem��Ϣ*/
    public String FrontShowSelectItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectItemId��ȡSelectItem����*/
        SelectItem selectItem = selectItemDAO.GetSelectItemBySelectItemId(selectItemId);

        List<Subject> subjectList = subjectDAO.QueryAllSubjectInfo();
        ctx.put("subjectList", subjectList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("selectItem",  selectItem);
        return "front_show_view";
    }

    /*�����޸�SelectItem��Ϣ*/
    public String ModifySelectItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Subject subjectObj = subjectDAO.GetSubjectBySubjectId(selectItem.getSubjectObj().getSubjectId());
            selectItem.setSubjectObj(subjectObj);
            Student studentObj = studentDAO.GetStudentByStudentNumber(selectItem.getStudentObj().getStudentNumber());
            selectItem.setStudentObj(studentObj);
            selectItemDAO.UpdateSelectItem(selectItem);
            ctx.put("message",  java.net.URLEncoder.encode("SelectItem��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectItem��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SelectItem��Ϣ*/
    public String DeleteSelectItem() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            selectItemDAO.DeleteSelectItem(selectItemId);
            ctx.put("message",  java.net.URLEncoder.encode("SelectItemɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectItemɾ��ʧ��!"));
            return "error";
        }
    }

}
