package com.luode.common;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;

/**
 * CommonController
 */
public class CommonController extends Controller {
	public void index() {
		redirect("/member");
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	public void savedCaptcha() {
		HttpSession session = getSession();
		String sessionId = session.getId();
		render(new RandomCodeRender(sessionId));
	}
}
