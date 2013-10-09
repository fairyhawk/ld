package com.luode.common;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.validate.Validator;
import com.luode.member.model.Member;

/**
 * 
 * 类描述：用户校验
 * @author David
 * @time 2013-5-18 上午11:45:51
 *
 */
public class ModifyMemberValidator extends Validator {
	
	protected void validate(Controller c) {
		String id = c.getPara("member.id");
		if(StringKit.isBlank(id)){
			addError("modifyMsg", "修改失败!");
		}else{
			Member member = Member.dao.findById(Integer.parseInt(id));
			if(member == null){
				addError("modifyMsg", "用户不存在!");
			}else{
				validateRequiredString("member.username", "usernameMsg", "请输入用户名!");
				validateRequiredString("member.member_name", "memberNameMsg", "请输入学员姓名!");
				validateEmail("member.email", "emailMsg", "请输入正确的邮箱");
				validateRequiredString("member.password", "passwordMsg", "密码不能为空");
				String username = c.getPara("member.username");
				String member_name = c.getPara("member.member_name");
				String email = c.getPara("member.email");
				Long userNameCount = Db.queryLong("select IF(count(*)>0,TRUE,FALSE) from letter_member where username = ? AND id != ?", username,Integer.parseInt(id));
				Long memberNameCount = Db.queryLong("select IF(count(*)>0,TRUE,FALSE) from letter_member where member_name = ? AND id != ?", member_name,Integer.parseInt(id));
				Long emailCount = Db.queryLong("select count(*) from letter_member where email = '"+email+"'"+" AND id!="+id);
				if(userNameCount.intValue()>0){
					 addError("usernameMsg", "用户名已存在!");
				}
				if(emailCount.intValue()>0){
					addError("emailMsg", "邮箱已注册!");
				}
				if(memberNameCount.intValue()>0){
					addError("memberNameMsg", "用户姓名已存在!");
				}				
			}
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Member.class);
		controller.renderJsp("/WEB-INF/system/msgbox_member_modify.jsp");
	}
}
