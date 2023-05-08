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

    private int subjectTypeId;
    public void setSubjectTypeId(int subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }
    public int getSubjectTypeId() {
        return subjectTypeId;
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

    /*待操作的SubjectType对象*/
    private SubjectType subjectType;
    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }
    public SubjectType getSubjectType() {
        return this.subjectType;
    }

    /*跳转到添加SubjectType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加SubjectType信息*/
    @SuppressWarnings("deprecation")
    public String AddSubjectType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            subjectTypeDAO.AddSubjectType(subjectType);
            ctx.put("message",  java.net.URLEncoder.encode("SubjectType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SubjectType添加失败!"));
            return "error";
        }
    }

    /*查询SubjectType信息*/
    public String QuerySubjectType() {
        if(currentPage == 0) currentPage = 1;
        List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        subjectTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = subjectTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = subjectTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("subjectTypeList",  subjectTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySubjectTypeOutputToExcel() { 
        List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SubjectType信息记录"; 
        String[] headers = { "类型编号","类型名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SubjectType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SubjectType信息*/
    public String FrontQuerySubjectType() {
        if(currentPage == 0) currentPage = 1;
        List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        subjectTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = subjectTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = subjectTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("subjectTypeList",  subjectTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的SubjectType信息*/
    public String ModifySubjectTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键subjectTypeId获取SubjectType对象*/
        SubjectType subjectType = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subjectTypeId);

        ctx.put("subjectType",  subjectType);
        return "modify_view";
    }

    /*查询要修改的SubjectType信息*/
    public String FrontShowSubjectTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键subjectTypeId获取SubjectType对象*/
        SubjectType subjectType = subjectTypeDAO.GetSubjectTypeBySubjectTypeId(subjectTypeId);

        ctx.put("subjectType",  subjectType);
        return "front_show_view";
    }

    /*更新修改SubjectType信息*/
    public String ModifySubjectType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            subjectTypeDAO.UpdateSubjectType(subjectType);
            ctx.put("message",  java.net.URLEncoder.encode("SubjectType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SubjectType信息更新失败!"));
            return "error";
       }
   }

    /*删除SubjectType信息*/
    public String DeleteSubjectType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            subjectTypeDAO.DeleteSubjectType(subjectTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("SubjectType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SubjectType删除失败!"));
            return "error";
        }
    }

}
