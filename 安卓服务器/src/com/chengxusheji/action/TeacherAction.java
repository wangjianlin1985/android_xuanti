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
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TeacherAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�photo��������*/
	private File photoFile;
	private String photoFileFileName;
	private String photoFileContentType;
	public File getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
	public String getPhotoFileFileName() {
		return photoFileFileName;
	}
	public void setPhotoFileFileName(String photoFileFileName) {
		this.photoFileFileName = photoFileFileName;
	}
	public String getPhotoFileContentType() {
		return photoFileContentType;
	}
	public void setPhotoFileContentType(String photoFileContentType) {
		this.photoFileContentType = photoFileContentType;
	}
    /*�������Ҫ��ѯ������: ��ʦ���*/
    private String teacherNumber;
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
    public String getTeacherNumber() {
        return this.teacherNumber;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
    }

    /*�������Ҫ��ѯ������: ְ��*/
    private String professName;
    public void setProfessName(String professName) {
        this.professName = professName;
    }
    public String getProfessName() {
        return this.professName;
    }

    /*�������Ҫ��ѯ������: ��ְ����*/
    private String inDate;
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    public String getInDate() {
        return this.inDate;
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource TeacherDAO teacherDAO;

    /*��������Teacher����*/
    private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return this.teacher;
    }

    /*��ת�����Teacher��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Teacher��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTeacher() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��ʦ����Ƿ��Ѿ�����*/
        String teacherNumber = teacher.getTeacherNumber();
        Teacher db_teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);
        if(null != db_teacher) {
            ctx.put("error",  java.net.URLEncoder.encode("�ý�ʦ����Ѿ�����!"));
            return "error";
        }
        try {
            /*�����ʦ��Ƭ�ϴ�*/
            String photoPath = "upload/noimage.jpg"; 
       	 	if(photoFile != null)
       	 		photoPath = photoUpload(photoFile,photoFileContentType);
       	 	teacher.setPhoto(photoPath);
            teacherDAO.AddTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTeacher��Ϣ*/
    public String QueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(teacherNumber == null) teacherNumber = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(professName == null) professName = "";
        if(inDate == null) inDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber, name, birthday, professName, inDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, name, birthday, professName, inDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherNumber", teacherNumber);
        ctx.put("name", name);
        ctx.put("birthday", birthday);
        ctx.put("professName", professName);
        ctx.put("inDate", inDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTeacherOutputToExcel() { 
        if(teacherNumber == null) teacherNumber = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(professName == null) professName = "";
        if(inDate == null) inDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber,name,birthday,professName,inDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Teacher��Ϣ��¼"; 
        String[] headers = { "��ʦ���","����","�Ա�","��������","��ʦ��Ƭ","ְ��","��ϵ�绰","��ְ����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<teacherList.size();i++) {
        	Teacher teacher = teacherList.get(i); 
        	dataset.add(new String[]{teacher.getTeacherNumber(),teacher.getName(),teacher.getSex(),new SimpleDateFormat("yyyy-MM-dd").format(teacher.getBirthday()),teacher.getPhoto(),teacher.getProfessName(),teacher.getTelephone(),new SimpleDateFormat("yyyy-MM-dd").format(teacher.getInDate())});
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
			response.setHeader("Content-disposition","attachment; filename="+"Teacher.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTeacher��Ϣ*/
    public String FrontQueryTeacher() {
        if(currentPage == 0) currentPage = 1;
        if(teacherNumber == null) teacherNumber = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(professName == null) professName = "";
        if(inDate == null) inDate = "";
        List<Teacher> teacherList = teacherDAO.QueryTeacherInfo(teacherNumber, name, birthday, professName, inDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teacherDAO.CalculateTotalPageAndRecordNumber(teacherNumber, name, birthday, professName, inDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teacherDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = teacherDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teacherList",  teacherList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teacherNumber", teacherNumber);
        ctx.put("name", name);
        ctx.put("birthday", birthday);
        ctx.put("professName", professName);
        ctx.put("inDate", inDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String ModifyTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teacherNumber��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);

        ctx.put("teacher",  teacher);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Teacher��Ϣ*/
    public String FrontShowTeacherQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teacherNumber��ȡTeacher����*/
        Teacher teacher = teacherDAO.GetTeacherByTeacherNumber(teacherNumber);

        ctx.put("teacher",  teacher);
        return "front_show_view";
    }

    /*�����޸�Teacher��Ϣ*/
    public String ModifyTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*�����ʦ��Ƭ�ϴ�*/
            if(photoFile != null) {
            	String photoPath = photoUpload(photoFile,photoFileContentType);
            	teacher.setPhoto(photoPath);
            }
            teacherDAO.UpdateTeacher(teacher);
            ctx.put("message",  java.net.URLEncoder.encode("Teacher��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacher��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Teacher��Ϣ*/
    public String DeleteTeacher() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teacherDAO.DeleteTeacher(teacherNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Teacherɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teacherɾ��ʧ��!"));
            return "error";
        }
    }

}
