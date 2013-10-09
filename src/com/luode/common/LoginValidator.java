package com.luode.common;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.validate.Validator;
import com.luode.member.model.Member;
import com.luode.util.MD5;

/**
 * 
 * 类描述：用户校验
 * @author David
 * @time 2013-5-18 上午11:45:51
 *
 */
public class LoginValidator extends Validator {
	
	private static final String msg = "message";

	protected void validate(Controller c) {
		String username = c.getPara("loginName");
		String password = c.getPara("password");
		if(StringKit.isBlank(username)){
			addError(msg, "用户名不能为空!");
		}else{
			if(StringKit.isBlank(password)){
				addError(msg, "密码不能为空");
			}else{
				Long userExist = Db.queryLong("SELECT count(*) FROM letter_member WHERE username=?", username);
				if(userExist.intValue()==0){
					addError(msg, "用户不存在!");
				}else{
					List<Object> values = new ArrayList<Object>();
					values.add(username);
					values.add(password);
					Object paras[] = values.toArray();
					Long passwordError = Db.queryLong("SELECT count(*) FROM letter_member WHERE username=? AND `password`=?", paras);
					if(passwordError.intValue()==0){
						addError(msg, "密码错误!");	
					}
				}

			}
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepPara("loginName");
		controller.renderJsp("/WEB-INF/member/login.jsp");
	}
}
