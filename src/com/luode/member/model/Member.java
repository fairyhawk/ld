package com.luode.member.model;


import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * 类描述：Member Model
 * @author David
 * @time 2013-5-18 上午11:43:25
 *
 */
@SuppressWarnings("serial")
public class Member extends Model<Member> {
	public static final Member dao = new Member();
	public Page<Member> getMemberList(){
		return dao.paginate  (1, 10, "select *", "from member order by id asc");
	}
}