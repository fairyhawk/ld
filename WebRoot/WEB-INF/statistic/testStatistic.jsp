<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script type="text/javascript" src="<%=path%>/common/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script>
<c:if test="${not empty info}">
alert('<c:out value="${info}"/>');
</c:if>
<c:remove var="info"/>
$(function(){
$(".boxy").boxy();
});
function query(){
	document.Form1.action = "<%=path%>/system/testStat";
		document.Form1.submit();
	}
</script>

</head>
<body>
	<div id="main">
		<div class="position">当前位置：统计报表&gt;&gt;测试统计</div>
		<div class="search">
			<form name="Form1" method="post">
				<p>
					姓名： <input name="memberName" id='memberName' type="text" class="text_box_style" size="30" value=${memberName }>&nbsp; 测试日期： <input id="beginDate" name='beginDate' class="Wdate"
						type="text" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${beginDate }" /> 至 <input id="endDate" name='endDate' class="Wdate"
						type="text" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'beginDate\')}'})" value="${endDate }" /> <a href="#" title="查询" class="btn_add_contacts boxy"
						onclick="query()"> <img src="<%=path%>/system/images/btn_search.gif" width="57" height="24" />
					</a>
				</p>
			</form>
		</div>
		<c:if test="${memberTests.list!=null }">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table_style">
				<tr>
					<td width="4%" align="center" style="background: #e9edf3">序号</td>
					<td width="7%" align="center" style="background: #e9edf3">姓名</td>
					<td width="10%" align="center" style="background: #e9edf3">单词书</td>
					<td width="6%" align="center" style="background: #e9edf3">单元</td>
					<td width="6%" align="center" style="background: #e9edf3">测试类型</td>
					<td width="6%" align="center" style="background: #e9edf3">测试完成</td>
					<td width="5%" align="center" style="background: #e9edf3">正确数</td>
					<td width="5%" align="center" style="background: #e9edf3">错误数</td>
					<td width="5%" align="center" style="background: #e9edf3">正确率</td>
					<td width="6%" align="center" style="background: #e9edf3">耗时(分钟)</td>
					<td width="12%" align="center" style="background: #e9edf3">开始时间</td>
					<td width="12%" align="center" style="background: #e9edf3">结束时间</td>
					<td width="4%" align="center" style="background: #e9edf3">操作</td>
				</tr>
				<c:forEach items="${memberTests.list}" var="mt" varStatus="st">
					<tr>
						<td align="center">${st.index+1}</td>
						<td align="center">${mt.mname}</td>
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
						<td align="center"><font class="font_red">[<a href="<%=path%>/system/testDetail/${mt.id}">详情</a>]
						</font></td>
					</tr>

				</c:forEach>
			</table>
			<div align="center">
				<c:import url="/common/page.jsp" />
			</div>
		</c:if>
		<c:if test="${recordList!=null }">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table_style">
				<tr>
					<td width="10%" align="center" style="background: #e9edf3">序号</td>
					<td width="10%" align="center" style="background: #e9edf3">是否正确</td>
					<td width="15%" align="center" style="background: #e9edf3">单词</td>
					<td width="15%" align="center" style="background: #e9edf3">音标</td>
					<td width="50%" align="center" style="background: #e9edf3">词义</td>
				</tr>
				<c:forEach items="${recordList}" var="tr" varStatus="st">
					<tr>
						<td align="center">${st.index+1}</td>
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
