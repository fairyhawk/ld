<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=path%>/system/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/system/boxy/boxy.css" rel="stylesheet" type="text/css" />
<title>背单词—罗德国际教育</title>
<script type="text/javascript" src="<%=path%>/system/boxy/jquery.boxy.js"></script>
<script type="text/javascript" src="<%=path%>/system/boxy/jquery-1.2.6.pack.js"></script>
<script>
<c:if test="${not empty info}">
alert('<c:out value="${info}"/>');
</c:if>
<c:remove var="info"/>
$(function(){
$(".boxy").boxy();
});
function query(){
	document.Form1.action = "<%=path%>/system/queryMember";
		document.Form1.submit();
	}
</script>

</head>
<body>
	<div id="main">
		<div class="position">当前位置：学员管理&gt;&gt;正式学员</div>
		<div class="search">
			<form name="Form1" method="post">
				<p>姓名：
					<input name="memberName" id='memberName' type="text" class="text_box_style" size="40" />&nbsp; 
						<a href="#" title="查询" class="btn_add_contacts boxy" onclick="query()"> 
							<img src="<%=path%>/system/images/btn_search.gif" width="57" height="24" />
						</a>
						<a href="<%=path %>/system/toAddMemberPage" title="添加"> 
							<img src="<%=path%>/system/images/btn_add.gif" width="57" height="24" />
						</a>
				</p>
			</form>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table_style">
			<tr>
				<td width="10%" align="center" style="background: #e9edf3">序号</td>
				<td width="10%" align="center" style="background: #e9edf3">用户名</td>
				<td width="10%" align="center" style="background: #e9edf3">密码</td>
				<td width="16%" align="center" style="background: #e9edf3">姓名</td>
				<td width="16%" align="center" style="background: #e9edf3">邮箱</td>
				<td width="14%" align="center" style="background: #e9edf3">联系电话</td>
				<td width="16%" align="center" style="background: #e9edf3">添加日期</td>
				<td width="14%" align="center" style="background: #e9edf3">操作</td>
			</tr>
			<c:if test="${members.list!=null }">
				<c:forEach items="${members.list}" var="member" varStatus="st">
					<tr>
						<td align="center">${st.index+1}</td>
						<td align="center">${member.username}</td>
						<td align="center">${member.password}</td>
						<td align="center">${member.member_name}</td>
						<td align="center">${member.email}</td>
						<td align="center">${member.telephone}</td>
						<td align="center">${member.register_date}</td>
						<td align="center"><font class="font_red">[<a href="<%=path%>/system/toModifyMemberPage/${member.id}">修改</a>]
						</font></td>
					</tr>
				</c:forEach>
			</c:if>

		</table>

		<div align="center">
			<c:import url="/common/page.jsp" />
		</div>
	</div>
</body>
</html>
