package com.newlecture.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class Spag extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String num_ = req.getParameter("n");
		int num = num_ != null && !num_.equals("") ? Integer.parseInt(num_) : 0;

		String model = "";
		if (num % 2 != 0) {
			model = "홀수";
		} else {
			model = "짝수";
		}

		String[] names = { "newlec", "dragon" };

		Map<String, Object> notice = new HashMap<String, Object>();
		notice.put("id", 1);
		notice.put("title", "EL은 좋아요");

		req.setAttribute("notice", notice);

		req.setAttribute("names", names);

		req.setAttribute("result", model);

		// redirect
		// forward
		RequestDispatcher dispatcher = req.getRequestDispatcher("spag.jsp");
		dispatcher.forward(req, resp);

	}
}
