<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>背单词—罗德国际教育</title>
</head>
<frameset rows="79,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="<%=path %>/system/head.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="132,*" frameborder="no" border="0" framespacing="0">
    <frame src="left.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
  	<frameset rows="80,*" cols="*" frameborder="no" border="0" framespacing="0">
		<frame src="top_nav.jsp" name="topnavFrame" id="topnavFrame" title="topnavFrame" />
    	<frame src="welcome.jsp" name="mainFrame" id="mainFrame" title="mainFrame" />
     </frameset>
  </frameset>
</frameset>
<noframes>
<body>
</body>
</noframes></html>
