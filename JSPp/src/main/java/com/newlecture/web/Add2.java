package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/add2")
public class Add2 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		// 같은 name 속성 값을 가지고 있으면 배열로 들어오게 된다
		String[] num_ = req.getParameterValues("num");
		
		int result = 0;
		
		for(int i = 0; i < num_.length; i++) {
			// 지역변수로 선언한 num의 경우 매번 for문이 돌아가면서 새롭게 선언되는것이 아닌 값만 바뀌게 된다.
			int num = Integer.parseInt(num_[i]);
			result+=num;
		}
		
		out.printf("result is : %d", result);
	
	}
}
