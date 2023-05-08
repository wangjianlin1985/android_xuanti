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
import com.chengxusheji.dao.GuestBookDAO;
import com.chengxusheji.domain.GuestBook;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GuestBookAction extends BaseAction {

    /*界面层需要查询的属性: 标题*/
    private String qustion;
    public void setQustion(String qustion) {
        this.qustion = qustion;
    }
    public String getQustion() {
        return this.qustion;
    }

    /*界面层需要查询的属性: 提问学生*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }

    /*界面层需要查询的属性: 解答老师*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
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

    private int guestBookId;
    public void setGuestBookId(int guestBookId) {
        this.guestBookId = guestBookId;
    }
    public int getGuestBookId() {
        return guestBookId;
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
    @Resource StudentDAO studentDAO;
    @Resource TeacherDAO teacherDAO;
    @Resource GuestBookDAO guestBookDAO;

    /*待操作的GuestBook对象*/
    private GuestBook guestBook;
    public void setGuestBook(GuestBook guestBook) {
        this.guestBook = guestBook;
    }
    public GuestBook getGuestBook() {
        return this.guestBook;
    }

    /*跳转到添加GuestBook视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加GuestBook信息*/
    @SuppressWarnings("deprecation")
    public String AddGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student student = studentDAO.GetStudentByStudentNumber(guestBook.getStudent().getStudentNumber());
            guestBook.setStudent(student);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(guestBook.getTeacherObj().getTeacherNumber());
            guestBook.setTeacherObj(teacherObj);
            guestBookDAO.AddGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook添加失败!"));
            return "error";
        }
    }

    /*查询GuestBook信息*/
    public String QueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(qustion == null) qustion = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(qustion, student, teacherObj, currentPage);
        /*计算总的页数和总的记录数*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(qustion, student, teacherObj);
        /*获取到总的页码数目*/
        totalPage = guestBookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = guestBookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("guestBookList",  guestBookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qustion", qustion);
        ctx.put("student", student);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryGuestBookOutputToExcel() { 
        if(qustion == null) qustion = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(qustion,student,teacherObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GuestBook信息记录"; 
        String[] headers = { "记录id","标题","提问学生","提问时间","老师回复","解答老师","回复时间","回复标志"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<guestBookList.size();i++) {
        	GuestBook guestBook = guestBookList.get(i); 
        	dataset.add(new String[]{guestBook.getGuestBookId() + "",guestBook.getQustion(),guestBook.getStudent().getName(),
guestBook.getQuestionTime(),guestBook.getReply(),guestBook.getTeacherObj().getName(),
guestBook.getReplyTime(),guestBook.getReplyFlag() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"GuestBook.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询GuestBook信息*/
    public String FrontQueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(qustion == null) qustion = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(qustion, student, teacherObj, currentPage);
        /*计算总的页数和总的记录数*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(qustion, student, teacherObj);
        /*获取到总的页码数目*/
        totalPage = guestBookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = guestBookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("guestBookList",  guestBookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qustion", qustion);
        ctx.put("student", student);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "front_query_view";
    }

    /*查询要修改的GuestBook信息*/
    public String ModifyGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键guestBookId获取GuestBook对象*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("guestBook",  guestBook);
        return "modify_view";
    }

    /*查询要修改的GuestBook信息*/
    public String FrontShowGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键guestBookId获取GuestBook对象*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("guestBook",  guestBook);
        return "front_show_view";
    }

    /*更新修改GuestBook信息*/
    public String ModifyGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student student = studentDAO.GetStudentByStudentNumber(guestBook.getStudent().getStudentNumber());
            guestBook.setStudent(student);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(guestBook.getTeacherObj().getTeacherNumber());
            guestBook.setTeacherObj(teacherObj);
            guestBookDAO.UpdateGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook信息更新失败!"));
            return "error";
       }
   }

    /*删除GuestBook信息*/
    public String DeleteGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            guestBookDAO.DeleteGuestBook(guestBookId);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook删除失败!"));
            return "error";
        }
    }

}
