package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 50 * 5)
@WebServlet("/admin/board/notice/reg")
public class RegContorller extends HttpServlet {
	// 글쓰기 위한 페이지 요청
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(req, resp);
	}

	// 글을 쓴다음 post요청 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		String title = req.getParameter("title");
		System.out.println(title);
		String content = req.getParameter("content");
		System.out.println(content);
		String isOpen_ = req.getParameter("open");
		System.out.println(isOpen_);
		boolean isOpen = isOpen_ != null && !isOpen_.equals("") ? Boolean.parseBoolean(isOpen_) : false;

		// 단일 file 받기

		Collection<Part> parts = req.getParts();
		StringBuilder builder = new StringBuilder();

		for (Part p : parts) {
			if (!p.getName().equals("file"))
				continue;
			if (p.getSize() == 0)
				continue;
			Part filePart = p;
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");

			InputStream fis = filePart.getInputStream();

			String realPath = req.getServletContext().getRealPath("/upload");
			System.out.println(realPath);

			File path = new File(realPath);
			if (!path.exists())
				path.mkdirs();

			// 자바에서 제공해주는 경로기법 File.separator | / \
			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);

			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = fis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.close();
			fis.close();
		}

		builder.delete(builder.length() - 1, builder.length());

		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(isOpen);
		notice.setWriterId("newlec");
		notice.setFiles(builder.toString());

		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);

		resp.sendRedirect("list");
	}
}
