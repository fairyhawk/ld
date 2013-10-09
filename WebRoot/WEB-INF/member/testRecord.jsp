<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="robots" content="index,follow" />
<meta name="copyright" content="Copyright 2003-2013. www.loude.org . All Rights Reserved.">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="">
<meta name="ujianVerification" content="142a9b3e2632832be6781c7e6a9c436f">
<title>背单词—罗德国际教育</title>
<link href="<%=path%>/system/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/system/boxy/boxy.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/css/jquery.css">
<link rel="stylesheet" type="text/css" href="/css/noty_theme_default.css">
<link rel="stylesheet" type="text/css" href="/css/orgchart.css">
<link rel="stylesheet" type="text/css" href="/css/shanbay.css">
<link rel="stylesheet" type="text/css" href="/css/fontdiao.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.css">
<script type="text/javascript" src="/js/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/common/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript">
<c:if test="${not empty info}">
alert('<c:out value="${info}"/>');
</c:if>
<c:remove var="info"/>
$(function(){
$(".boxy").boxy();
});
function query(){
	document.Form1.action = "<%=path%>/letter/testRecord";
	document.Form1.submit();
}
</script>
</head>
<body>
	<img class="hide" style="position: fixed; right: 160px; z-index: 10000000; top: 40px;" src="/images/clip.png">
	<div align="center">
		<c:import url="/pages/navbar.jsp" />
	</div>
	<div class="gradient-bar">&nbsp;</div>
	<div id="main" align="center">
		<div class="search">
			<form name="Form1" method="post">
				<p>
					单词书：
					<select name="letterBookId" style="width: 150px">
						<option value="">--请选择单词书--</option>
						<c:forEach items="${letterBookList}" var="lb" varStatus="st">
							<option value="${lb.id }">${lb.name }</option>
						</c:forEach>
					</select>
					&nbsp; 
					完成情况：
					<select name="testFinish" style="width: 150px">
						<option value="">--请选择完成情况--</option>
						<option value="0">--未完成--</option>
						<option value="1">--已完成--</option>
					</select>
					&nbsp; 
					测试类型：
					<select name="testType" style="width: 150px">
						<option value="">--请选择测试类型--</option>
						<option value="1">--单元测试--</option>
						<option value="2">--错词测试--</option>
						<option value="3">--随机测试--</option>
					</select>
					<br>
					测试日期： <input id="beginDate" name='beginDate' class="Wdate"
						type="text" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${beginDate }" /> 
						至 <input id="endDate" name='endDate' class="Wdate"
						type="text" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'beginDate\')}'})" value="${endDate }" /> 
						<a href="#" title="查询" class="btn_add_contacts boxy" onclick="query()">
						<img src="<%=path%>/system/images/btn_search.gif" width="57" height="24" />
					</a>
				</p>
			</form>
		</div>
			<c:if test="${memberTests!=null }">
			<table width="1000px" border="0" cellpadding="0" cellspacing="1" class="table_style">
				<tr>
					<td width="4%" align="center" style="background: #e9edf3">序号</td>
					<td width="12%" align="center" style="background: #e9edf3">单词书</td>
					<td width="6%" align="center" style="background: #e9edf3">单元</td>
					<td width="6%" align="center" style="background: #e9edf3">测试类型</td>
					<td width="6%" align="center" style="background: #e9edf3">测试完成</td>
					<td width="5%" align="center" style="background: #e9edf3">正确数</td>
					<td width="5%" align="center" style="background: #e9edf3">错误数</td>
					<td width="5%" align="center" style="background: #e9edf3">正确率</td>
					<td width="7%" align="center" style="background: #e9edf3">耗时(分钟)</td>
					<td width="12%" align="center" style="background: #e9edf3">开始时间</td>
					<td width="12%" align="center" style="background: #e9edf3">结束时间</td>
					<td width="10%" align="center" style="background: #e9edf3">操作</td>
				</tr>
				<c:forEach items="${memberTests}" var="mt" varStatus="st">
					<tr>
						<td align="center">${st.index+1}</td>
						<td align="center">${mt.bname}</td>
						<td align="center">${mt.cname}</td>
						<td align="center">${mt.test_type}</td>
						<td align="center">${mt.test_finish}</td>
						<td align="center">${mt.right_num}</td>
						<td align="center">${mt.wrong_num}</td>
						<td align="center">${mt.accuracy}</td>
						<td align="center">${mt.expend_time}</td>
						<td align="center"><fmt:formatDate value="${mt.start_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><fmt:formatDate value="${mt.end_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><font class="font_red">
						<c:if test="${mt.test_finish=='未完成' }">
						[<a href="<%=path%>/letter/goOnTestStep2/${mt.id}">继续测试</a>]
						</c:if>
						[<a href="<%=path%>/letter/testDetail/${mt.id}">详情</a>]
						</font></td>
					</tr>

				</c:forEach>
			</table>
		</c:if>
		<c:if test="${recordList!=null }">
			<table width="1000px" border="0" cellpadding="0" cellspacing="1" class="table_style">
				<tr>
					<td width="10%" align="center" style="background: #e9edf3">序号</td>
					<td width="15%" align="center" style="background: #e9edf3">单词书</td>
					<td width="10%" align="center" style="background: #e9edf3">单元</td>
					<td width="10%" align="center" style="background: #e9edf3">是否正确</td>
					<td width="10%" align="center" style="background: #e9edf3">单词</td>
					<td width="10%" align="center" style="background: #e9edf3">音标</td>
					<td width="35%" align="center" style="background: #e9edf3">词义</td>
				</tr>
				<c:forEach items="${recordList}" var="tr" varStatus="st">
					<tr>
						<td align="center">${st.index+1}</td>
						<td align="center">${tr.book_name}</td>
						<td align="center">${tr.book_chapter}</td>
						<td align="center">${tr.isright}</td>
						<td align="left">${tr.word}</td>
						<td align="left">${tr.soundmark}</td>
						<td align="left">${tr.explanation}</td>
						</font>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>		
	</div>
</body>
</html>