package com.newlecture.web.controller.admin.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id_ = req.getParameter("id");
		int id = id_ != null && !id_.equals("") ? Integer.parseInt(id_) : 0;

		NoticeService service = new NoticeService();
		service.updateNoticeHit(id);
		Notice notice = service.getNotice(id);

		req.setAttribute("n", notice);

		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp").forward(req, resp);
	}
}
