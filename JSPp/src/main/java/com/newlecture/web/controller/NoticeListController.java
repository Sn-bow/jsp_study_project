package com.newlecture.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// list?f=title&q=a

		String page_ = req.getParameter("p");
		String field_ = req.getParameter("f");
		String query_ = req.getParameter("q");

		int page = page_ != null && !page_.equals("") ? Integer.parseInt(page_) : 1;
		String field = field_ != null && !field_.equals("") ? field_ : "title";
		String query = query_ != null && !query_.equals("") ? query_ : "";

		NoticeService service = new NoticeService();
		List<Notice> noticeList = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);

		req.setAttribute("list", noticeList);
		req.setAttribute("count", count);

		// view 페이지는 META-INF 폴더안에 옮겨서 사용
		req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(req, resp);

	}
}
