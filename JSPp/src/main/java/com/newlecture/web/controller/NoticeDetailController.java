package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// JDBC 와같은 외부 라이브러리를 사용하기 위해서는 기존의 Build Path 를 이용해서 라이브러리를 추가하는것이 아니라
		// src -> webapp -> WEB-INF -> lib 폴더 안에 ojdbc8.jar 파일을 넣어주면 된다.
		// 해당 폴더안에 넣어주는 이유는 JSPp 프로젝트에서 컴파일이 된후 해당 프로젝트에서 실행되는게 아니라 톰캣서버에서 배포되어 실행되기
		// 때문이다
		// 그렇게 되면 톰캣에서 실행될때 build path부분이 달라지게 된다.
		// 라이브러리가 배포될때 같이 배포되어야 되기때문에 그렇다
		// 이는 자바실행환경 및 톰캣 과같은 라이브러리는 톰캣에 배포되어 실행되는 실행환경에도 존재하기 떄문이다.
		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";
		String sql = "select * from notice where id=?";

		String id_ = req.getParameter("id");
		int id = id_ != null && !id_.equals("") ? Integer.parseInt(id_) : 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			rs.next();

			String title = rs.getString("TITLE");
			Date regDate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			int hit = rs.getInt("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");

			Notice notice = new Notice(id, title, regDate, writerId, hit, files, content);

			System.out.println(notice.getTitle());
			System.out.println(notice.getFiles());

			req.setAttribute("n", notice);

			/*
			 * req.setAttribute("title", title); req.setAttribute("regDate", regDate);
			 * req.setAttribute("writerId", writerId); req.setAttribute("hit", hit);
			 * req.setAttribute("files", files); req.setAttribute("content", content);
			 */

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp");
		dispatcher.forward(req, resp);

	}
}
