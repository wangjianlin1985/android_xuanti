<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>基于安卓的毕业设计选题管理系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>ClassInfo/ClassInfo_FrontQueryClassInfo.action" target="OfficeMain">班级信息</a></li> 
			<li><a href="<%=basePath %>Student/Student_FrontQueryStudent.action" target="OfficeMain">学生信息</a></li> 
			<li><a href="<%=basePath %>Teacher/Teacher_FrontQueryTeacher.action" target="OfficeMain">教师信息</a></li> 
			<li><a href="<%=basePath %>Subject/Subject_FrontQuerySubject.action" target="OfficeMain">题目信息</a></li> 
			<li><a href="<%=basePath %>SelectItem/SelectItem_FrontQuerySelectItem.action" target="OfficeMain">学生选题</a></li> 
			<li><a href="<%=basePath %>GuestBook/GuestBook_FrontQueryGuestBook.action" target="OfficeMain">留言交流</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
