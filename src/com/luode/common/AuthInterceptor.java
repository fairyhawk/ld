/**
 ************************************************************
 *  Copyright (c) 2011-2013 罗德教育 
 ************************************************************
 * @Package com.luode.common
 * @Title：AuthInterceptor.java
 * @author: David
 * @Date:2013-5-18 下午5:48:28
 */
package com.luode.common;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.luode.member.model.Member;
import com.luode.system.model.SystemUser;

/**
 * 类描述：
 * 
 * @author David
 * @time 2013-5-18 下午5:48:28
 * 
 */
public class AuthInterceptor implements Interceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jfinal.aop.Interceptor#intercept(com.jfinal.core.ActionInvocation)
	 */
	@Override
	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();
		Member loginUser = controller.getSessionAttr("member");
		SystemUser systemUser = controller.getSessionAttr("systemUser");
		String actionkey = ai.getActionKey();
		if (actionkey.contains("/system")) {
			if (systemUser != null||"/system".equals(actionkey)||"/system/login".equals(actionkey)) {
				ai.invoke();
			} else {
				controller.redirect("/system");
			}
		} else {
			if (loginUser != null || "/".equals(actionkey) || "/member".equals(actionkey) || "/member/login".equals(actionkey) || "/member/register".equals(actionkey) || "/member/addMember".equals(actionkey)) {
				if ("/member/review".equals(actionkey) && loginUser != null) {
					Integer memberId = controller.getParaToInt(0);
					Integer loginUserId = loginUser.getInt("id");
					if (!loginUserId.equals(memberId)) {
						controller.redirect("/404.html");
					} else {
						ai.invoke();
					}
				} else {
					ai.invoke();
				}
			} else {
				controller.redirect("/member");
			}
		}
	}

}
