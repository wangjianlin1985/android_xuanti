<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GuestBook" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    GuestBook guestBook = (GuestBook)request.getAttribute("guestBook");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改留言交流</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var qustion = document.getElementById("guestBook.qustion").value;
    if(qustion=="") {
        alert('请输入标题!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="GuestBook/GuestBook_ModifyGuestBook.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录id:</td>
    <td width=70%><input id="guestBook.guestBookId" name="guestBook.guestBookId" type="text" value="<%=guestBook.getGuestBookId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>标题:</td>
    <td width=70%><textarea id="guestBook.qustion" name="guestBook.qustion" rows=5 cols=50><%=guestBook.getQustion() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>提问学生:</td>
    <td width=70%>
      <select name="guestBook.student.studentNumber">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNumber().equals(guestBook.getStudent().getStudentNumber()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNumber() %>' <%=selected %>><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>提问时间:</td>
    <td width=70%><input id="guestBook.questionTime" name="guestBook.questionTime" type="text" size="20" value='<%=guestBook.getQuestionTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>老师回复:</td>
    <td width=70%><textarea id="guestBook.reply" name="guestBook.reply" rows=5 cols=50><%=guestBook.getReply() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>解答老师:</td>
    <td width=70%>
      <select name="guestBook.teacherObj.teacherNumber">
      <%
        for(Teacher teacher:teacherList) {
          String selected = "";
          if(teacher.getTeacherNumber().equals(guestBook.getTeacherObj().getTeacherNumber()))
            selected = "selected";
      %>
          <option value='<%=teacher.getTeacherNumber() %>' <%=selected %>><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>回复时间:</td>
    <td width=70%><input id="guestBook.replyTime" name="guestBook.replyTime" type="text" size="20" value='<%=guestBook.getReplyTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>回复标志:</td>
    <td width=70%><input id="guestBook.replyFlag" name="guestBook.replyFlag" type="text" size="8" value='<%=guestBook.getReplyFlag() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
