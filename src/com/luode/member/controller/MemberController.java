package com.luode.member.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.luode.common.CommonController;
import com.luode.common.LoginValidator;
import com.luode.common.RegisterValidator;
import com.luode.letter.model.LetterBook;
import com.luode.letter.model.LetterBookType;
import com.luode.member.model.LearnState;
import com.luode.member.model.Member;
import com.luode.member.model.MemberBook;
import com.luode.member.model.MemberTest;
import com.luode.util.MD5;

import java.util.*;

import javax.servlet.http.HttpSession;

public class MemberController extends CommonController {
    //显示所有书分类
    public void index() {
    	HttpSession session = getSession();
    	Member member = (Member) session.getAttribute("member");
    	if(member == null){
    		renderJsp("/WEB-INF/member/login.jsp");
    	}else{
    		redirect("/member/review/"+member.getInt("id"));
    	}
    }

    public void register(){
    	renderJsp("/WEB-INF/member/register.jsp");
    }
    
    @ClearInterceptor
    @Before(RegisterValidator.class)
    public void addMember(){
    	Member member = getModel(Member.class);
    	member.set("register_date", new Date());
    	member.set("password", MD5.encryption32Uppercase(member.getStr("password")));
    	member.save();
    	renderJsp("/WEB-INF/member/login.jsp");
    }
    @ClearInterceptor
    @Before(LoginValidator.class)
    public void login(){
    		String loginName = getPara("loginName");
    		String password = getPara("password");
    		Member memberInDb = Member.dao.findFirst("select * from letter_member where username=? and password=?", loginName,password);
    		if (memberInDb != null) {
    			HttpSession session = getSession();
    			session.setAttribute("member", memberInDb);
    	    	setAttr("member", memberInDb);
    	    	review2(memberInDb);
    		}else{
    			renderJsp("/WEB-INF/member/login.jsp");
    		}
    		//相关操作
    }
    
    /**
     * 
     * 方法描述：推出操作
     * @author David
     * @time 2013-5-22 下午8:57:15
     *
     */
    public void logout(){
    	HttpSession session = getSession();
    	session.removeAttribute("member");
    	renderJsp("/WEB-INF/member/login.jsp");
    }
    
    /**
     * 
     * 方法描述：查看用户学习测试情况
     * @author David
     * @time 2013-5-22 下午8:58:04
     *
     */
    public void review2(Member member){
//    	Integer memberId = getParaToInt(0);
//    	Member member = Member.dao.findById(memberId);
    	Integer memberId= member.getInt("id");
    	List<MemberBook> memberBooks = MemberBook.dao.find("SELECT lb.*,lmb.member_id,lmb.book_id,lmb.is_finish,lmb.finish_time FROM letter_book lb,letter_member_book lmb WHERE lb.id=lmb.book_id AND lmb.member_id=?",memberId);
    	setAttr("bookType", LetterBookType.dao.paginate(1, 100, "select *", "from letter_booktype order by id asc"));
    	setAttr("memberBookList", memberBooks);
    	setAttr("member", member);
    	if(memberBooks != null && memberBooks.size()>0){
    		for(MemberBook memberBook :memberBooks){
    			System.out.println(memberBook.getInt("book_id"));
    			Long noLearning = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND lls.right_num=0 AND lls.wrong_num=0",memberId,memberBook.getInt("book_id"));
        		Long haveLearned = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND (lls.right_num!=0 OR lls.wrong_num!=0)",memberId,memberBook.getInt("book_id"));
        		memberBook.put("noLearning", noLearning);//未测试单词
	    		memberBook.put("haveLearned", haveLearned);//已经测试单次数量
    		}
    		renderJsp("/WEB-INF/member/review.jsp");
    	}else{
    		redirect("/letter/book/2-"+memberId);///letter/book/2-1
    	}
    	
    }
    public void review(){
    	Integer memberId = getParaToInt(0);
    	Member member = Member.dao.findById(memberId);
    	List<MemberBook> memberBooks = MemberBook.dao.find("SELECT lb.*,lmb.member_id,lmb.book_id,lmb.is_finish,lmb.finish_time FROM letter_book lb,letter_member_book lmb WHERE lb.id=lmb.book_id AND lmb.member_id=?",memberId);
    	setAttr("bookType", LetterBookType.dao.paginate(1, 100, "select *", "from letter_booktype order by id asc"));
    	setAttr("memberBookList", memberBooks);
    	setAttr("member", member);
    	if(memberBooks != null && memberBooks.size()>0){
    		for(MemberBook memberBook :memberBooks){
    			System.out.println(memberBook.getInt("book_id"));
    			Long noLearning = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND lls.right_num=0 AND lls.wrong_num=0",memberId,memberBook.getInt("book_id"));
        		Long haveLearned = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND (lls.right_num!=0 OR lls.wrong_num!=0)",memberId,memberBook.getInt("book_id"));
        		memberBook.put("noLearning", noLearning);//未测试单词
	    		memberBook.put("haveLearned", haveLearned);//已经测试单次数量
    		}
    		
    	}
    	renderJsp("/WEB-INF/member/review.jsp");
    }
    public static void main(String[] args) {
    	Random random = new Random();
    	
    	System.out.println(random.nextDouble());
	}
}
