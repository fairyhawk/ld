package com.luode.system.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.luode.common.AddMemberValidator;
import com.luode.common.CommonController;
import com.luode.common.ModifyMemberValidator;
import com.luode.letter.model.LetterBook;
import com.luode.member.model.Member;
import com.luode.member.model.MemberBook;
import com.luode.member.model.MemberTest;
import com.luode.sms.SendSMS;
import com.luode.system.model.SystemUser;

public class SystemController extends CommonController {
	private static final Logger LOGGER = Logger.getLogger(CommonController.class);
	public void index() {
		renderJsp("/WEB-INF/system/login.jsp");
	}

	/**
	 * 
	 * 方法描述：系统管理员登录
	 * 
	 * @author David
	 * @time 2013-5-26 下午9:18:20
	 * 
	 */
	public void login() {
		String loginName = getPara("loginName");
		String password = getPara("password");
		SystemUser systemUser = getSessionAttr("systemUser");
		if (systemUser == null) {
			systemUser = SystemUser.dao.findFirst("select * from letter_system_user where login_name=? and password=?", loginName, password);
			if (systemUser != null) {
				HttpSession session = getSession();
				session.setAttribute("systemUser", systemUser);
				setAttr("message", "a");
				renderJsp("/system/index.jsp");
			} else {
				renderJsp("/WEB-INF/system/login.jsp");
			}
		} else {
			renderJsp("/system/index.jsp");
		}

		// 相关操作
	}

	public void logout() {
		HttpSession session = getSession();
		session.removeAttribute("systemUser");
		redirect("/system");
	}

	public void queryMember() {
		String memberName = getRequest().getParameter("memberName");
		if (StringKit.isBlank(memberName)) {
			Page<Member> members = Member.dao.paginate(getParaToInt(0, 1), 10, "select *", "FROM letter_member ORDER BY id DESC");
			setAttr("members", members);
			setAttr("pageNumber", members.getPageNumber());
			setAttr("pageSize", members.getPageSize());
			setAttr("totalPage", members.getTotalPage());
			setAttr("totalRow", members.getTotalRow());
			setAttr("pageUrl", "/system/queryMember/");

		} else {
			Page<Member> members = Member.dao.paginate(getParaToInt(0, 1), 20, "select *", "FROM letter_member WHERE member_name = ? ORDER BY id DESC", memberName);
			setAttr("members", members);
			setAttr("pageNumber", members.getPageNumber());
			setAttr("pageSize", members.getPageSize());
			setAttr("totalPage", members.getTotalPage());
			setAttr("totalRow", members.getTotalRow());
			setAttr("pageUrl", "/system/queryMember");
		}
		renderJsp("/WEB-INF/system/manageMember.jsp");
	}

	public void toAddMemberPage() {
		renderJsp("/WEB-INF/system/msgbox_member_add.jsp");
	}

	@Before(AddMemberValidator.class)
	public void addMember() {
		Date userdate = new Date();
		Member member = getModel(Member.class);
		member.set("register_date", userdate);
		member.set("last_test_date", userdate);//2012-09-26增加用户最后一次测试时间
		member.save();
		Integer memberId = member.getInt("id");
		List<LetterBook> bookList = LetterBook.dao.getLetterBookAll();
		for(LetterBook lb : bookList){
			Long bookId = lb.getLong("id");
			MemberBook mb = MemberBook.dao.findFirst("SELECT * FROM letter_member_book lmb WHERE lmb.member_id=? AND lmb.book_id=?", memberId, bookId);
			if (mb == null) {
				new MemberBook().set("member_id", memberId).set("book_id", bookId).set("start_time", new Date()).save();
				String sql = "INSERT INTO letter_learn_state(member_id,word_id,book_id,chapter_id)SELECT " + memberId + ", ld.id,ld.book_id,ld.book_chapter_id FROM letter_detail ld WHERE ld.book_id=" + bookId;
				Db.update(sql);
			} else{
				LOGGER.info("单词书已存在");
			}
		}
		String password = member.getStr("password");
		String mobile = member.getStr("telephone");
    	String userName = member.getStr("username");
    	SendSMS.send("亲爱的罗德小伙伴，你已成功开通单词系统账户。用户名："+userName+"，密码："+password+"。祝学习愉快！【罗德国际教育】", mobile);
		redirect("/system/queryMember");
	}

	public void toModifyMemberPage() {
		Member member = Member.dao.findById(getParaToInt(0));
		setAttr("member", member);
		renderJsp("/WEB-INF/system/msgbox_member_modify.jsp");
	}

	@Before(ModifyMemberValidator.class)
	public void modifyMember() {
		Member memberNew = getModel(Member.class);
		Member member = Member.dao.findById(memberNew.getInt("id"));
		String oldPassword = member.getStr("password");
		member.set("username", memberNew.getStr("username"));
		member.set("member_name", memberNew.getStr("member_name"));
		member.set("password", memberNew.getStr("password"));
		member.set("email", memberNew.getStr("email"));
		member.set("telephone", memberNew.getStr("telephone"));
		member.update();
		Integer memberId = member.getInt("id");
		List<LetterBook> bookList = LetterBook.dao.getLetterBookAll();
		for(LetterBook lb : bookList){
			Long bookId = lb.getLong("id");
			MemberBook mb = MemberBook.dao.findFirst("SELECT * FROM letter_member_book lmb WHERE lmb.member_id=? AND lmb.book_id=?", memberId, bookId);
			if (mb == null) {
				new MemberBook().set("member_id", memberId).set("book_id", bookId).set("start_time", new Date()).save();
				String sql = "INSERT INTO letter_learn_state(member_id,word_id,book_id,chapter_id)SELECT " + memberId + ", ld.id,ld.book_id,ld.book_chapter_id FROM letter_detail ld WHERE ld.book_id=" + bookId;
				Db.update(sql);
			} else{
				LOGGER.info("单词书已存在");
			}
		}
		String password = member.getStr("password");
		if(!password.equals(oldPassword)){
			String mobile = member.getStr("telephone");
			String userName = member.getStr("username");
			SendSMS.send("亲爱的罗德小伙伴你好，你在罗德单词系统中的新密码为["+password+"]。祝你在罗德能够快乐的学习！【罗德国际教育】", mobile);
		}
		redirect("/system/queryMember");
	}

	public void testStat() {
		Integer pageNum = getParaToInt(3);
		String memberName = "";
		String beginDate = "";
		String endDate = "";
		String pageUrl = "";
		if (pageNum == null) {
			memberName = getRequest().getParameter("memberName");
			beginDate = getRequest().getParameter("beginDate");
			endDate = getRequest().getParameter("endDate");
			String mname = "";
			String bDate = "";
			String eDate = "";
			if (StringKit.notBlank(beginDate)) {
				bDate = beginDate.replace("-", "_");
			}
			if (StringKit.notBlank(endDate)) {
				eDate = endDate.replace("-", "_");
			}
			if (StringKit.notBlank(memberName))
				mname = memberName;
			pageUrl = "/system/testStat/" + mname + "-" + bDate + "-" + eDate + "-";
		} else {
			String mname = getPara(0);
			String bDate = getPara(1);
			String eDate = getPara(2);
			if (StringKit.notBlank(bDate)) {
				beginDate = bDate.replace("_", "-");
			} else {
				bDate = "";
			}
			if (StringKit.notBlank(eDate)) {
				endDate = eDate.replace("_", "-");
			} else {
				eDate = "";
			}
			if (StringKit.notBlank(mname)) {
				memberName = mname;
			} else {
				mname = "";
			}
			pageUrl = "/system/testStat/" + mname + "-" + bDate + "-" + eDate + "-";
		}

		StringBuffer condition = new StringBuffer("FROM letter_member_test lmt,letter_member lm WHERE lmt.member_id=lm.id ");
		if (StringKit.notBlank(memberName))
			condition.append("AND lm.member_name LIKE '%" + memberName + "%' ");
		if (StringKit.notBlank(beginDate))
			condition.append("AND start_time >= '" + beginDate + " 00:00:00' ");
		if (StringKit.notBlank(endDate))
			condition.append("AND start_time <= '" + endDate + " 23:59:59' ");
		Page<MemberTest> memberTests = MemberTest.dao.paginate(
						getParaToInt(3, 1), 10,
						"SELECT lmt.id,(SELECT lm.member_name FROM letter_member lm WHERE lm.id=lmt.member_id) mname," +
						"(SELECT lb.`name` FROM letter_book lb WHERE lb.id=lmt.book_id) bname ," +
						"(SELECT lbc.`name` FROM letter_book_chapter lbc WHERE lbc.id=lmt.chapter_id) cname," +
						"IF(lmt.test_type=1,'单元测试',IF(lmt.test_type=2,'错词测试','随机测试')) test_type,IF(lmt.test_finish=0,'未完成','已完成') test_finish," +
						"lmt.right_num,lmt.wrong_num,lmt.accuracy,lmt.expend_time,lmt.start_time,lmt.end_time",
						condition.append("ORDER BY id DESC").toString());
		setAttr("memberTests", memberTests);
		setAttr("pageNumber", memberTests.getPageNumber());
		setAttr("pageSize", memberTests.getPageSize());
		setAttr("totalPage", memberTests.getTotalPage());
		setAttr("totalRow", memberTests.getTotalRow());
		setAttr("pageUrl", pageUrl);
		setAttr("beginDate", beginDate);
		setAttr("endDate", endDate);
		setAttr("memberName", memberName);
		renderJsp("/WEB-INF/statistic/testStatistic.jsp");
	}

	public void testDetail() {
		Integer testId = getParaToInt(0);
		List<Record> testRecordList = Db.find("SELECT IF(t.is_right=0,'否','是') isright,ld.word,ld.soundmark,ld.explanation FROM letter_detail ld,letter_test_record t WHERE ld.id = t.word_id AND t.test_id=? ORDER BY t.is_right", testId);
		// List<LetterDetail> testRecordList =
		// LetterDetail.dao.find("SELECT IF(t.is_right=0,'否','是') isright,ld.word,ld.soundmark,ld.explanation FROM letter_detail ld,letter_test_record t WHERE ld.id = t.word_id AND t.test_id=? ORDER BY t.is_right",testId);
		setAttr("recordList", testRecordList);
		renderJsp("/WEB-INF/statistic/testStatistic.jsp");
	}
}
