<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Subject" %>
<%@ page import="com.chengxusheji.domain.SubjectType" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的SubjectType信息
    List<SubjectType> subjectTypeList = (List<SubjectType>)request.getAttribute("subjectTypeList");
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    Subject subject = (Subject)request.getAttribute("subject");

%>
<HTML><HEAD><TITLE>查看题目信息</TITLE>
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
    <td width=30%>题目编号:</td>
    <td width=70%><%=subject.getSubjectId() %></td>
  </tr>

  <tr>
    <td width=30%>题目名称:</td>
    <td width=70%><%=subject.getSubjectName() %></td>
  </tr>

  <tr>
    <td width=30%>题目类型:</td>
    <td width=70%>
      <%=subject.getSubjectTypeObj().getSubjectTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>题目内容:</td>
    <td width=70%><%=subject.getContent() %></td>
  </tr>

  <tr>
    <td width=30%>限选人数:</td>
    <td width=70%><%=subject.getStudentNumber() %></td>
  </tr>

  <tr>
    <td width=30%>指导老师:</td>
    <td width=70%>
      <%=subject.getTeacherObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>发布时间:</td>
    <td width=70%><%=subject.getAddTime() %></td>
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
