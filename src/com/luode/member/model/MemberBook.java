package com.luode.member.model;


import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * 类描述：MemberBook Model
 * @author David
 * @time 2013-5-18 上午11:43:25
 *
 */
@SuppressWarnings("serial")
public class MemberBook extends Model<MemberBook> {
	public static final MemberBook dao = new MemberBook();
	public Page<MemberBook> getMemberBookList(){
		return dao.paginate  (1, 10, "select *", "from letter_member_book order by id asc");
	}
}