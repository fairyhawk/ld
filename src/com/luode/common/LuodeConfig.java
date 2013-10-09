package com.luode.common;

import com.demo.blog.Blog;
import com.demo.blog.BlogController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.quartz.QuartzPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.luode.letter.controller.LetterController;
import com.luode.letter.model.LetterBook;
import com.luode.letter.model.LetterBookChapter;
import com.luode.letter.model.LetterBookType;
import com.luode.letter.model.LetterDetail;
import com.luode.member.controller.MemberController;
import com.luode.member.model.LearnState;
import com.luode.member.model.Member;
import com.luode.member.model.MemberBook;
import com.luode.member.model.MemberTest;
import com.luode.member.model.TestRecord;
import com.luode.system.controller.SystemController;
import com.luode.system.model.SystemUser;

/**
 * API引导式配置
 */
public class LuodeConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
		me.add("/blog", BlogController.class);
		me.add("/letter", LetterController.class);
		me.add("/member", MemberController.class);
		me.add("/system", SystemController.class);
		me.add("/common", CommonController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型
		arp.addMapping("letter_book_chapter", LetterBookChapter.class);	
		arp.addMapping("letter_book", LetterBook.class);	
		arp.addMapping("letter_booktype", LetterBookType.class);	
		arp.addMapping("letter_detail", LetterDetail.class);	
		arp.addMapping("letter_member", Member.class);//用户表
		arp.addMapping("letter_member_book", MemberBook.class);	//用户_单词书关联表
		arp.addMapping("letter_learn_state", LearnState.class);	//用户学习情况表
		arp.addMapping("letter_test_record", TestRecord.class);	//用户测试记录情况表
		arp.addMapping("letter_member_test", MemberTest.class);	//用户测试表
		arp.addMapping("letter_system_user", SystemUser.class);	//系统管理员表
		//2013-09-22 注册定时
		QuartzPlugin qp = new QuartzPlugin("quartzjob.properties");
		me.add(qp);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
		me.add(new SessionInViewInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8080, "/", 5);
	}
}
