package org.ranestar.test.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CommonSession extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonSession.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		String[] url = request.getRequestURI().split("/");
		String lastUrl = url[url.length - 1];
		
		String member = (String) session.getAttribute("session_user");
		String user_auth = (String) session.getAttribute("session_auth");
		
		if(member == null || "".equals(member)){
			alertMsg(response, "로그인 후 이용가능합니다.");
		}else{
			if(user_auth.equals("AU01")){
				if(lastUrl.equals("myInfo")){
					alertMsg(response, "해당 메뉴 이용 권한이 없습니다.");
				}
			}else if (user_auth.equals("AU02")){
				if(lastUrl.equals("work-owner")){
					alertMsg(response, "해당 메뉴 이용 권한이 없습니다.");
				}
			}
			
		}
		
		return true;
	}
	
	public void alertMsg(HttpServletResponse response, String msg) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("		alert('"+msg+"');");
		sb.append("		location.href='./';");
		sb.append("</script>");
		response.getWriter().print(sb.toString());
		response.getWriter().close();
	}
	
}
