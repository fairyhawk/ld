<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面数据分页</title>
</head>
<body>
	<!-- 定义的变量 -->
	<div>
		<!-- 从request中取出传入的参数，放入本页变量中 -->

		<!-- 分页逻辑 -->
		<!-- 上一页标签 -->
		<p class="green-black ">
			总共 &nbsp;&nbsp; ${totalPage } &nbsp;&nbsp; 页 &nbsp;&nbsp;&nbsp;
			<c:if test="${pageNumber<=1}">
				<span>上一页</span>&nbsp;&nbsp;
</c:if>
			<c:if test="${pageNumber>1}">
				<a href="${pageUrl}${pageNumber-1}">上一页</a>
			</c:if>
			当前第 &nbsp;&nbsp; ${pageNumber} &nbsp;&nbsp; 页 &nbsp;&nbsp;
			<c:if test="${pageNumber==totalPage}">
				<span>下一页</span>
			</c:if>
			<c:if test="${pageNumber!=totalPage}">
				<a href="${pageUrl}${pageNumber+1}">下一页</a>
			</c:if>
			&nbsp;&nbsp; <span>选择:</span> <select name="pageSelect" onchange="window.location.href=this.options[selectedIndex].value">
				<c:forEach var="i" begin="1" end="${totalPage}" step="1">
					<c:if test="${i==pageNumber}">
						<option value="${pageUrl}${i}" selected>${i}</option>
					</c:if>
					<c:if test="${i!=pageNumber}">
						<option value="${pageUrl}${i}">${i}</option>
					</c:if>
				</c:forEach>
			</select>
		</p>
	</div>
</body>
</html>