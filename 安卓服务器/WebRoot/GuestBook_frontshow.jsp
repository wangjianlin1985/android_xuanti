<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GuestBook" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    GuestBook guestBook = (GuestBook)request.getAttribute("guestBook");

%>
<HTML><HEAD><TITLE>查看留言交流</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>记录id:</td>
    <td width=70%><%=guestBook.getGuestBookId() %></td>
  </tr>

  <tr>
    <td width=30%>标题:</td>
    <td width=70%><%=guestBook.getQustion() %></td>
  </tr>

  <tr>
    <td width=30%>提问学生:</td>
    <td width=70%>
      <%=guestBook.getStudent().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>提问时间:</td>
    <td width=70%><%=guestBook.getQuestionTime() %></td>
  </tr>

  <tr>
    <td width=30%>老师回复:</td>
    <td width=70%><%=guestBook.getReply() %></td>
  </tr>

  <tr>
    <td width=30%>解答老师:</td>
    <td width=70%>
      <%=guestBook.getTeacherObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>回复时间:</td>
    <td width=70%><%=guestBook.getReplyTime() %></td>
  </tr>

  <tr>
    <td width=30%>回复标志:</td>
    <td width=70%><%=guestBook.getReplyFlag() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
