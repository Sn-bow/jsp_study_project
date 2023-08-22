package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet {
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
		List<NoticeView> noticeList = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);

		req.setAttribute("list", noticeList);
		req.setAttribute("count", count);

		// view 페이지는 META-INF 폴더안에 옮겨서 사용
		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] openIds = req.getParameterValues("open-id");
		String[] delIds = req.getParameterValues("del-id");
		String cmd = req.getParameter("cmd");

		NoticeService service = new NoticeService();

		switch (cmd) {
		case "all-open":
			for (String openId : openIds) {
				System.out.printf("open id : %s", openId).println();
			}
			break;
		case "all-del":
			int[] ids = new int[delIds.length];
			for (int i = 0; i < delIds.length; i++) {
				ids[i] = Integer.parseInt(delIds[i]);
			}
			int result = service.deleteNoticeAll(ids);
			System.out.println(result);
			break;
		}

		resp.sendRedirect("/admin/board/notice/list");
	}

}
