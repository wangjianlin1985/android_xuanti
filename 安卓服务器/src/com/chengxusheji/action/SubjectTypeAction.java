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
import com.chengxusheji.dao.SubjectTypeDAO;
import com.chengxusheji.domain.SubjectType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SubjectTypeAction extends BaseAction {

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

    private int subjectTypeId;
    public void setSubjectTypeId(int subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }
    public int getSubjectTypeId() {
        return subjectTypeId;
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

    /*��������SubjectType����*/
    private SubjectType subjectType;
    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }
    public SubjectType getSubjectType() {
        return this.subjectType;
    }

    /*��ת�����SubjectType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���SubjectType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSubjectType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            subjectTypeDAO.AddSubjectType(subjectType);
            ctx.put("message",  java.net.URLEncoder.encode("SubjectType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SubjectType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSubjectType��Ϣ*/
    public String QuerySubjectType() {
        if(currentPage == 0) currentPage = 1;
        List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        subjectTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = subjectTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = subjectTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("subjectTypeList",  subjectTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySubjectTypeOutputToExcel() { 
        List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SubjectType��Ϣ��¼"; 
        String[] headers = { "���ͱ��","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<subjectTypeList.size();i++) {
        	SubjectType subjectType = subjectTypeList.get(i); 
        	dataset.add(new String[]{subjectType.getSubjectTypeId() + "",subjectType.getSubjectTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SubjectType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSubjectType��Ϣ*/
    public String FrontQuerySubjectType() {
        if(currentPage == 0) currentPage = 1;
        List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        subjectTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = subjectTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = subjectTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("subjectTypeList",  subjectTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SubjectType��Ϣ*/
    public String ModifySubjectTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������subjectTypeId��ȡSubjectType����*/
        SubjectType subjectType = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subjectTypeId);

        ctx.put("subjectType",  subjectType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SubjectType��Ϣ*/
    public String FrontShowSubjectTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������subjectTypeId��ȡSubjectType����*/
        SubjectType subjectType = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subjectTypeId);

        ctx.put("subjectType",  subjectType);
        return "front_show_view";
    }

    /*�����޸�SubjectType��Ϣ*/
    public String ModifySubjectType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            subjectTypeDAO.UpdateSubjectType(subjectType);
            ctx.put("message",  java.net.URLEncoder.encode("SubjectType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SubjectType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SubjectType��Ϣ*/
    public String DeleteSubjectType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            subjectTypeDAO.DeleteSubjectType(subjectTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("SubjectTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SubjectTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
