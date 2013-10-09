<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css" href="/css/jquery.css">
<link rel="stylesheet" type="text/css" href="/css/noty_theme_default.css">
<link rel="stylesheet" type="text/css" href="/css/orgchart.css">
<link rel="stylesheet" type="text/css" href="/css/shanbay.css">
<link rel="stylesheet" type="text/css" href="/css/fontdiao.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.css">
<script type="text/javascript" src="/js/jquery-1.9.0.js"></script>
<script type="text/javascript">
	/* $(document).ready(function() {
		$("button").click(function() {
			var memberId = $("#mId").val();
			var myBookId = $("#bId").val();
			window.location.href = "/letter/bookChapter/" + myBookId + "-" + memberId;
		});
	}); */
	function gotobook(myBookId){
		var memberId = $("#mId").val();
		window.location.href = "/letter/bookChapter/" + myBookId + "-" + memberId;
	}
</script>
</head>
<body data-spy="scroll" data-target=".subnav" data-offset="50">
	<img class="hide" style="position: fixed; right: 160px; z-index: 10000000; top: 40px;" src="/images/clip.png">
	<div align="center">
		<c:import url="/pages/navbar.jsp" />
	</div>
	<div class="gradient-bar">&nbsp;</div>
	<div class="container main-body ">

		<div class="row hide progress-box">
			<div class="span12 progress-box-wrap">
				<div class="row">
					<div class="span1">
						<h6 class="pull-right">进度</h6>
					</div>
					<div class="span9">
						<div class="well review-progress">
							<div class="progress pull-right progress-danger" style="width: 0%;">
								<div class="bar" style="width: 100%">
									<span class="num"></span>
								</div>
							</div>
							<div class="progress pull-left progress-success" style="width: 0%;">
								<div class="bar" style="width: 100%">
									<span class="num"></span>
								</div>
							</div>
							<div style="width: 0%;" class="progress pull-left progress-reviewed ">
								<div class="bar reviewed pull-left" style="width: 100%;">
									<span class="num"></span>
								</div>
							</div>
							<div class="progress pull-left progress-unreviewed" style="width: 99.9%;">
								<div class="bar" style="width: 100%">
									<span class="num">42</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="review" class="span12">
				<c:if test="${memberBookList!=null && memberBookList.size()>0 }">
					<c:forEach items="${memberBookList}" var="myBook" varStatus="status">
					<%-- <c:if test="${status.index<1 }"> --%>
				
				<div class="hero-unit start-review">
					 
						<div class="span5">
							<input type="hidden" name="mId" id="mId" value="${member.id}"> <input type="hidden" name="bId" id="bId" value="${myBook.id}">
							<div class="row">
								<div class="wordbook-cover span thumbnail">
									<a href="/letter/bookChapter/${myBook.id}-${member.id}"><img src="${myBook.picture}"></a>
								</div>
								<div class="wordbook-basic-info span">
									<div title="${myBook.name}" class="wordbook-title">
										<a href="/letter/bookChapter/${myBook.id}-${member.id}">${myBook.name}</a>
									</div>
									<div class="wordbook-count">
										<span>单词数: </span>${myBook.wordsnumber}<br>
									</div>
									<c:choose>
										<c:when test="${myBook.is_finish == 0}">
										</c:when>
										<c:when test="${myBook.is_finish == 1}">
											<div class="wordbook-finish-date">
												<div class="hide all-wordbook-finished alert alert-success">
													恭喜你，你收藏的单词书已学完！点击<a target='_blank' href='/wordbook/'>这里</a>再选一本吧
												</div>
												<span>完成时间： </span>${myBook.finish_time}
											</div>
										</c:when>
									</c:choose>
								</div>
							</div>

						</div>
						
						<div class="row">
							<div class="span5">
								<div class="row">
									<h2 class="span2 today">
										${myBook.noLearning}<small>未测单词</small>
									</h2>
									<h2 class="span3">
										${myBook.haveLearned}<small>已测单词</small>
									</h2>
								</div>
								<div class="row buttons">
									<button onclick="gotobook(${myBook.id})" class="span3 btn-large btn-primary start-review-button">继续学习</button>
								</div>
							</div>
						</div>
					</div>
					<%-- </c:if> --%>
				</c:forEach>
				</c:if>
					<c:if test="${memberBookList==null|| memberBookList.size()==0 }">
						<div class="hero-unit start-review">
						<div class="row">
							<div class="span5">
								<h4 class="inadequate-daily-quota-hint">
									<div class=page-header>
										<h4>没有新的单词书词？赶快选一个吧</h4>
									</div>
									<ul>
										<c:if test="${bookType!=null }">
											<c:forEach items="${bookType.list}" var="bt">
												<li><a href="/letter/book/${bt.id}-${member.id}">${bt.name}</a></li>
											</c:forEach>
										</c:if>
									</ul>
								</h4>
							</div>
							<h4>1. 在当天，每一个新词都会经历自评，测试以及探索三个阶段，会帮助你在当天建立足够印象。</h4>
							<h4 class="second">2. 日后我们会自动帮助你反复复习，巩固强化你的记忆。</h4>
						</div>
					 
					</div>
					</c:if>				
				
				<div class="quote">
					<blockquote>
						<p>
							While there is life there is hope.<br>一息若存，希望不灭。 
						</p>
						<small>英国谚语</small>
					</blockquote>
				</div>
				<hr style="height: 2px; background-color: #EEE;">
				<div class="promotion">
					<div class="row">
						<div class="span6">
							<span class="title">罗德单词（建设中）</span>
						</div>
						<div class="span6">
							<span class="title">罗德阅读（建设中）</span>
						</div>
					</div>
					<div class="row">
						<div class="span6">
							<div class="row">
								<div class="span3">
									<img src="/images/icon_iphone.png"> <span class="subtitle">iPHONE 下载</span>
								</div>
								<div class="span3">
									<img src="/images/icon_android.png"> <span class="subtitle">ANDROID 下载</span>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="row">
								<div class="span3">
									<img src="/images/icon_shanbaynews.png"> <span class="subtitle">罗德新闻</span>
								</div>
								<div class="span3">
									<img src="/images/icon_readplan.png"> <span class="subtitle">阅读计划</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="affix-prompt"></div>
</body>
</html>