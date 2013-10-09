package com.luode.member.model;


import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * 类描述：LearnState Model
 * @author David
 * @time 2013-5-18 上午11:43:25
 *
 */
@SuppressWarnings("serial")
public class LearnState extends Model<LearnState> {
	public static final LearnState dao = new LearnState();
	public Page<LearnState> getLearnStateList(){
		return dao.paginate  (1, 10, "select *", "from letter_learn_state order by id asc");
	}
}