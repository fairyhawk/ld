package com.luode.member.model;


import java.util.List;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * 类描述：MeberTest Model
 * @author David
 * @time 2013-5-26 下午5:29:12
 *
 */
@SuppressWarnings("serial")
public class MemberTest extends Model<MemberTest> {
	public static final MemberTest dao = new MemberTest();
	public Page<MemberTest> getMemberTestList(){
		return dao.paginate  (1, 10, "select *", "from letter_member_test order by id asc");
	}
	public List<MemberTest> getMemberTestList(Integer bookId, Integer memberId,Integer limit) {
		StringBuffer condition = new StringBuffer("FROM letter_member_test lmt WHERE lmt.member_id=?");
		if (bookId!=null)
			condition.append(" AND lmt.book_id = ? ");
		List<MemberTest> memberTests = MemberTest.dao.find("SELECT lmt.id,(SELECT lm.member_name FROM letter_member lm WHERE lm.id=lmt.member_id) mname," +
						"(SELECT lb.`name` FROM letter_book lb WHERE lb.id=lmt.book_id) bname ," +
						"(SELECT lbc.`name` FROM letter_book_chapter lbc WHERE lbc.id=lmt.chapter_id) cname," +
						"IF(lmt.test_type=1,'单元测试',IF(lmt.test_type=2,'错词测试','随机测试')) test_type,IF(lmt.test_finish=0,'未完成','已完成') test_finish," +
						"lmt.right_num,lmt.wrong_num,lmt.accuracy,lmt.expend_time,lmt.start_time,lmt.end_time "+condition.append(" ORDER BY id DESC LIMIT ?").toString(),memberId,bookId,limit);
		if(memberTests!=null && memberTests.size()>0)
			return memberTests;
		else
			return null;
	}
}