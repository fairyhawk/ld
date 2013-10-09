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
public class RegisterValidator extends Validator {
	
	protected void validate(Controller c) {
		validateRequiredString("member.username", "nameMsg", "请输入用户名!");
		validateEmail("member.email", "emailMsg", "请输入正确的邮箱");
		validateRequiredString("member.password", "passwordMsg", "密码不能为空");
		validateRequiredString("captchaCode", "captchaMsg", "验证码不能为空");
		validateEqualField("member.password", "password1", "ps1Msg", "两次输入密码不同");
		String username = c.getPara("member.username");
		String email = c.getPara("member.email");
		Long userNameCount = Db.queryLong("select IF(count(*)>0,TRUE,FALSE) from letter_member where username = ?", username);
		Long emailCount = Db.queryLong("select count(*) from letter_member where email = '"+email+"'");
		if(userNameCount.intValue()>0){
			 addError("nameMsg", "用户名已存在!");
		}
		if(emailCount.intValue()>0){
			addError("emailMsg", "邮箱已注册!");
		}
        String inputRandomCode = c.getPara("captchaCode");
        String captchaKey = c.getPara("captchaKey");
        if (StringKit.notBlank(inputRandomCode))
            inputRandomCode = inputRandomCode.toUpperCase();
        String systemRandomCode = c.getCookie(captchaKey);
        if (RandomCodeService.validate(inputRandomCode, systemRandomCode) == false) {
            addError("captchaMsg", "验证码输入不正确,请重新输入!");
        }
        
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Member.class);
		controller.renderJsp("/WEB-INF/member/register.jsp");
	}
}
