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
<meta name="keywords" content="罗德单词书，创建你自己的单词书">
<meta name="description" content="罗德单词书">
<title>背单词—罗德国际教育</title>
<link rel="stylesheet" type="text/css" href="/css/jquery.css">
<link rel="stylesheet" type="text/css" href="/css/noty_theme_default.css">
<link rel="stylesheet" type="text/css" href="/css/new_wordbook.css">
<link rel="stylesheet" type="text/css" href="/css/fontdiao.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.css">
</head>

<body data-spy="scroll" data-target=".subnav" data-offset="50">
	<img class="hide" style="position: fixed; right: 160px; z-index: 10000000; top: 40px;" src="wordbook_files/clip.png">
	<div align="center">
		<c:import url="/pages/navbar.jsp" />
	</div>

	<div class="logo">
		<div class="container">
			<div class="sub-nav">
				<ul class="nav nav-pills">
					<li class="active"><a href="/letter/${member.id}">我的单词书</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="gradient-bar">&nbsp;</div>

	<div class="container main-body ">

		<div class="row">
			<div class="span8">

				<div class="tabbable">
					<ul class="nav nav-tabs" id="wordbook-wordlist-switcher">

						<li class="active"><a href="#wordbook-tab" data-toggle="tab" id="wordbook-tab-trigger">单词书</a></li>

					</ul>
					<div class="tab-content">

						<div class="tab-pane active" id="wordbook-tab">

							<div class="hide"></div>

							<h3>正在学习的单词书</h3>
							<div class="row well alert-success">

								<c:if test="${myBooks!=null }">
									<c:forEach items="${myBooks}" var="myBook">
										<div class="span3 wordbook-detail-container">
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
												<div class="wordbook-master-percent">
													<span>学习进度: </span> 0.0%
												</div>
												<c:choose>
													<c:when test="${myBook.is_finish == 0}">
														<a class="btn btn-success" href="/letter/bookChapter/${myBook.id}-${member.id}">开始学习</a>
													</c:when>
													<c:when test="${myBook.is_finish == 1}">
														<div class="wordbook-finish-date">
															<span>完成时间： </span>${myBook.finish_time}
														</div>
													</c:when>
												</c:choose>
											</div>
										</div>
									</c:forEach>
								</c:if>
							</div>



						</div>

						<div class="tab-pane" id="wordlist-tab">



							<div class="well">你还没有创建任何词串</div>




						</div>
					</div>
				</div>
				<div class="start-to-learn-wordbook-hint">
					<div class="modal hide" id="start-to-learn-wordbook-hint-modal">
						<div class="modal-header">
							<button class="close" data-dismiss="modal">×</button>
							<h3>更换单词书/词串</h3>
						</div>
						<div class="modal-body">

							<p id="current_learning_wordbook_container">
								你目前正在学习 单词书 <span class="current_learning">日常生活英语</span>,

							</p>

							<p>
								确定更换为 <span class="gonna_learn_type"></span> <span class="gonna_learn"></span> 吗?
							</p>
							<div class="alert">更换单词书当天不生效，次日生效。即当天不会从新单词书里为你添加新词</div>
						</div>
						<div class="modal-footer">
							<a href="#" class="btn" data-dismiss="modal">取消</a> <a href="#" class="btn btn-primary">确认更换</a>
						</div>
					</div>
					<div id="delete-confirm-prompt" class="hide">确定删除这本单词书吗？</div>
				</div>

			</div>

			<div class="span4">

				<div class="page-header">
					<h3>单词书分类</h3>
				</div>
				<span class="hide" id="current-category-id"></span>

				<ul class="nav nav-tabs nav-stacked" id="wordbook-category-list">
					<c:if test="${bookType!=null }">
						<c:forEach items="${bookType}" var="bookt">
							<li><a href="/letter/book/${bookt.id}-${member.id}">${bookt.name}</a></li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>

		<!-- Footer
      ================================================== -->

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/js/jquery-1.js"></script>
	<script src="/js/jquery_002.js"></script>
	<script src="/js/soundmanager2-jsmin.js">
		
	</script>
	<script src="/js/jquery.js">
		
	</script>

	<script src="/js/jquery_003.js"></script>
	<script type="text/javascript" src="/js/shanbay-base.js" charset="utf-8"></script>
	<script type="text/javascript" src="/js/shanbay-all.js" charset="utf-8"></script>
	<script type="text/javascript" src="/js/new_wordbook.js" charset="utf-8"></script>
	<script>
		trigger_start_to_learn_wordbook_hint();
		trigger_start_to_learn_wordlist_hint();
		init_create_button();
	</script>
	<script src="/js/jquery_003.js"></script>

	<script>
		delete_user_wordbook();
	</script>


	<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', 'UA-24491297-1' ]);
		_gaq.push([ '_setDomainName', '.shanbay.com' ]);
		_gaq.push([ '_trackPageview' ]);

		(function() {
			if (window.location.href.indexOf('shanbay.com') == -1)
				return;
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
	</script>


	<div>
		<embed id="ciba_grabword_plugin" type="application/ciba-grabword-plugin" height="0" hidden="true" width="0">
	</div>
</body>
</html>