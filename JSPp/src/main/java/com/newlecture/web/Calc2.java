package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/calc2")
public class Calc2 extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ServletContext application = req.getServletContext();
		HttpSession session = req.getSession();
		// 보냈었던 쿠키를 읽어들이기 위한 작업
		Cookie[] cookies = req.getCookies();
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String v_ = req.getParameter("v");		
		String op = req.getParameter("operator");
		
		int v = v_ != null && !v_.equals("") ? Integer.parseInt(v_) : 0;
		
		if(op.equals("=")) {
			int result = 0;
			
			/* int x = (Integer)application.getAttribute("value"); */
//			int x = (Integer)session.getAttribute("value");
			int x = 0;
			for (Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			}
			
			int y = v;
//			String operator = (String)application.getAttribute("op");
//			String operator = (String)session.getAttribute("op");
			String operator = "";
			for(Cookie c : cookies) {
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}
			}
			
			result = operator.equals("+") ? x + y : x - y;
			
			resp.getWriter().printf("result is : %d", result);
		}else {
//			application.setAttribute("value", v);
//			application.setAttribute("op", op);
			
//			session.setAttribute("value", v);
//			session.setAttribute("op", op);
			
			// cookie 심는 작업
			// 쿠키생성
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", String.valueOf(op));
			// 관련 URL에만 값이 전달될수록 설정 | Path 설정
			valueCookie.setPath("/calc2");
			opCookie.setPath("/calc2");
			// maxAge 를 설정하면 기본 브라우저를 닫아도 삭제되는 유지기간을 변경할 수 있다.
			// maxAge를 설정하게되면 브라우저에있는 메모리에 저장되다가
			// 컴퓨터 내부 파일에 저장되게 된다. | 디스크의 파일
			// cookie 만료날짜 초단위 60초 = 1분 1시간 = 60 * 60 24시간 = 60 * 60 * 24
			valueCookie.setMaxAge(24 * 60 * 60);
			opCookie.setMaxAge(24 * 60 * 60);
			// 쿠키를 클라이언트에게 보내기 | header에 심여져서 전달됨
			resp.addCookie(valueCookie);
			resp.addCookie(opCookie);
		}
		
				
	}
}
