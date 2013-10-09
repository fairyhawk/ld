package com.luode.system.model;


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
public class SystemUser extends Model<SystemUser> {
	public static final SystemUser dao = new SystemUser();
	public Page<SystemUser> getSystemUserList(){
		return dao.paginate  (1, 10, "select *", "from letter_system_user order by id asc");
	}
}