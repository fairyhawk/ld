<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<!-- saved from url=(0038)http://www.shanbay.com/accounts/login/ -->
<html lang="zh-CN" xmlns:wb="“http://open.weibo.com/wb”">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta name="robots" content="index,follow">
<meta name="copyright" content="Copyright 2003-2013. www.shanbay.com . All Rights Reserved.">

<title>背单词—罗德国际教育</title>
<meta name="keywords" content="17bdc 背单词 单词量 单词量测试 单词 英语单词 背单词软件 英语学习网站 学英语">
<meta name="description" content="智能安排词汇复习，在真实阅读环境里激活词汇，强化阅读能力，并记录你成长的每一步， 彻底告别死记硬背和边背边忘。和百万用户组队学习，共同提高">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="">
<meta name="ujianVerification" content="142a9b3e2632832be6781c7e6a9c436f">

<!-- Le styles -->
<link rel="stylesheet" type="text/css" href="<%=path %>/css/jquery.noty.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/noty_theme_default.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/cover.css">


<style>
body {
	margin-top: 0;
	padding-top: 0px;
}

.navbar.navbar-fixed-top {
	display: none;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/fontdiao.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/font-awesome.css">
<style type="text/css" id="LB_ADBLOCK_0">
[src*="&sourcesuninfo=ad-"],[id^="youku_ad_"],#Class_1_ad>.footad,#body>#myads,[align="center"]>a>[src^="http://drmcmm.baidu.com/media/"][width="960"][height="80"],[id^="span_myads"],[id^="lovexin"],#ks>.DaKuang,.ks>.DaKuang,#topad>.tad,.box1>.ad,[class="center margintop border clear data"]>.margintop,[class="center margintop border clear data"]>[width="880"][height="90"],[class="center margintop border clear"]>.margintop,#Class_1_ad>[id^="china_ads_div"],#foot>#foot2>#Class_1_ad,#floatDiv>#rightFloat,#top>.topSponsor.mt10,.mainArea.px9>.bottomSponsor,img[src^="http://user.hongdou.gxnews.com.cn/upload/index/"],[src^=" http://soft.mumayi.net/js/"],.a_fr.a_cb,.adarea,.adarea_top,#lovexin1,#lovexin2,.m_ad,#topBanner,.ad1,.ad2,.ad3,.ad4,.ad5,.ad6,.ad7,.ad8,.ad9,.adSpace,.ad_240_h,.side-Rad,.ol_ad_xiala,iframe[src^="http://a.cntv.cn"],#bd1,#bd2,#bd3,.h8r.font0,.topbod>.topbodr,.topbodr>table[width="670"][height="70"],.widget_ad_slot_wrapper,.gg01,.gg02,.gg03,.gg04,.gg05,.gg06,.gg07,.gg08,.gg09,#_SNYU_Ad_Wrap,[id^="snyu_slot_"],.random-box>.random-banner,.top-ads-list,.palm01-ad,.topAd950,[class^="ad-banner"][id^="ad_"],.gonglue_rightad,iframe[src^="http://www.37cs.com/html/click/"],#AdZoneRa,#AdZoneRb,#AdZoneRc,#AdZoneRd,#AdZoneRe,#AdZoneRf,#AdZoneRg,div[id="adAreaBbox"],.absolute.a_cover,#QQCOM_Width3,#auto_gen_6,.l_qq_com,#cj_ad,[href^="http://c.l.qq.com/adsclick?oid="],.right_ad_lefttext,.right_ad_righttext,.AdTop-Article-QQ,.business-Article-QQ,.qiye-Article-QQ,.AdBox-Article-QQ,td[width="960"][height="90"][align="center"],.adclass,.ad1,[id^="tonglan_"],.ads5,.adv,.ads220_60,.ad-h60,.ad-h65,.ggs,[class^="ads360"],.news_ad_box,#XianAd,.Ad3Top-Article-QQ,.AdTop2-Article-QQ,.adbutton-Aritcle-QQ,#AdRight-Article-QQ,[id^=ad-block],.sidBoxNoborder.mar-b8,#ent_ad,[class="ad"][id="ad_bottom"],[class="ad"][id="ad_top"],.plrad,.ad300,#top_ad1,#top_ad2,#top_ad3,#top_ad4,#top_ad5,#top_ad6,#top_ad7,#top_ad8,#top_ad9,#mid_ad1,#mid_ad2,#mid_ad3,#mid_ad4,#mid_ad5,#mid_ad6,#mid_ad7,#mid_ad8,#mid_ad9,#ads1,#ads2,#ads3,#ads4,#ads5,#ads6,#ads7,#ads8,#ads9,.dlAd,.changeAd,.unionCpv,#Advertisement,iframe[src*="/advertisement."],img[src*="/advertisement."],.ad_headerbanner,#ad_headerbanner,div[class^=ad_textlink],iframe[src*="guanggao"],img[src*="guanggao"],#ads-top,#ads-bot,.adBanner,#topAd,.top_ad,.topAds,.topAd,.ad_column,#ad1,#ad2,#ad3,#ad4,#ad5,#ad6,#ad7,#ad8,#ad9,.ad_footerbanner,#adleft,#adright,.advleft,.advright,.ad980X60,.banner-ad,.top-ad,#adright,#AdLayer1,#AdLayer2,#AdLayer3,div[href*="click.mediav.com"],div[class=top_ad],div[class^="headads"],div[class="ads"],.txtad,.guanggao,#guanggao,.adclass,div[id*="AdsLayer"],.ad950,.guangg,.header-ads-top,#adleft,#adright,#ad_show,.ad_pic,#fullScreenAd,div[class^="adbox"],#topADImg,div[class^="ad_box"],div[id^="adbox"],div[class^="ads_"],div[alt*="￥ﾹ﾿￥ﾑﾊ￤ﾽﾍ"],div[id^="ads_"],div[src*="/adfile/"],.delayadv,#vplayad,.jadv,div[src*="/ads/"],div[src*="/advpic/"],div[id*="_adbox"],div[id*="-adbox"],div[class^="showad"],div[id^="adshow"],#bottomads,.ad_column,div[id^="_AdSame"],iframe[src^="http://drmcmm.baidu.com"],div[src^="http://drmcmm.baidu.com"],frame[src^="http://drmcmm.baidu.com"],div[href^="http://www.baidu.com/cpro.php?"],iframe[href^="http://www.baidu.com/cpro.php?"],div[src^="http://cpro.baidu.com"],div[src^="http://eiv.baidu.com"],div[href^="http://www.baidu.com/baidu.php?url="],div[href^="http://www.baidu.com/adrc.php?url="],.ad_text,div[href^="http://click.cm.sandai.net"],div.adA.area,div[src*=".qq937.com"],iframe[src*=".qq937.com"],div[src*=".88210212.com"],iframe[src*=".88210212.com"],.adBox,.adRight,.adLeft,.banner-ads,.right_ad,.left_ad,.content_ad,.post-top-gg,div[class*="_ad_slot"],.col_ad,.block_ad,div[class^="adList"],.adBlue,.mar_ad,div[id^="ArpAdPro"],.adItem,.ggarea,.adiframe,iframe[src*="/adiframe/"],#bottom_ad,.bottom_ad,.crumb_ad,.topadna,.topadbod,div[src*="qq494.cn"],iframe[src*="qq494.cn"],.topadbod,embed[src*="gamefiles.qq937.com"],embed[src*="17kuxun.com"],.crazy_ad_layer,#crazy_ad_layer,.bannerad,iframe[src*="/ads/"],img[src*="/ads/"],embed[src*="/ads/"],#crazy_ad_float,.crazy_ad_float,.main_ad,.topads,div[class^="txtadsblk"],.head-ad,div[src*="/728x90."],img[src*="/728x90."],embed[src*="/728x90."],iframe[src*="/gg/"],img[src*="/gg/"],iframe[src^="http://www.460.com.cn"],#bg_ad,.ad_pic,iframe[src*="gg.yxdown.com"],.ad_top,#baiduSpFrame,.flashad,#flashad,#ShowAD,[onclick^="ad_clicked"],[class^="ad_video_"],#ad_240,.wp.a_f,.a_mu,#hd_ad,#top_ads,#header_ad,#adbanner,#adbanner_1,#Left_bottomAd,#Right_bottomAd,#ad_alimama,#vipTip,.ad_pip,#show-gg,.ad-box,.advbox,.widget-ads.banner,.a760x100,.a200x375,.a760x100,.a200x100,.ad_left,.ad_right
	{
	display: none !important;
}
</style>
</head>

<body data-spy="scroll" data-target=".subnav" data-offset="50">

	<div class="gradient-bar">&nbsp;</div>

	<div class="container main-body ">
		<div id="container">
			<div align="center">
				<c:import url="/pages/head.jsp" />
			</div>
			<hr class="head-separator">
			<div class="cover-content">

				<div id="register-body">
					<div id="blue-background">
						<div class="register-box login">
							<div class="register-box-inner">

								<h1>
									登录 <small>
	
										<p class="align-center">
											忘记用户名或密码？ 请联系排课老师
										</p>

									</small>
								</h1>
								<hr>
								<form action="/member/login" method="post" id="loginform">
									<div style="display: none">
										<input type="hidden" name="csrfmiddlewaretoken" value="b378f77e2c4482edd3e82e31af240881">
									</div>
									<ul>
										<li><ul class="errorlist">${message}</ul></li>
										<li><label for="id_username">用户名:</label> <input id="id_username" type="text" name=loginName value="${loginName }" maxlength="20"></li>
										<li><label for="id_password">密码:</label> <input type="password" name="password" id="id_password"></li>
									</ul>
									<div class="reset-password">
									<br>
										<!-- <a href="#">重设密码</a> -->
									</div>
									<div class="clear"></div>
									<div class="login-buttons">
										<button type="submit" class="" name="login">登录</button>
									</div>
								</form>
								<p></p>

							</div>
						</div>

					</div>
				</div>

			</div>
		</div>
	</div>

<!--乐语代码-->
<script type="text/javascript" charset="utf-8" src="http://gate.looyu.com/49437/109521.js"></script>
<!--乐语代码-->
</body>
</html>