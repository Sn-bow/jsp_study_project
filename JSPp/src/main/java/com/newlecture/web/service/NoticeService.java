package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public class NoticeService {
	// notice admin을 위한 서비스 함수 정리 -----------------------------------------
	// removeNoticeAll(ids)
	// pubNoticeAll(ids)
	// insertNotice(notice)
	// deleteNotice(id)
	// updateNotice(notice)
	// getNoticeNewestList()
	public int removeNoticeAll(int[] ids) {
		return 0;
	}

	public int pubNoticeAll(int[] ids) {
		return 0;
	}

	// return 값이 int인 이유는 정상적으로 등록되었을때 1 등록이 안되었을때 0을 반환하기 위해서이다.
	public int insertNotice(Notice notice) {
		int result = 0;

		String sql = "INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, FILES) VALUES(?,?,?,?,?)";
		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterId());
			st.setBoolean(4, notice.getPub());
			st.setString(5, notice.getFiles());

			result = st.executeUpdate();

			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int deleteNotice(int id) {
		return 0;
	}

	public int updateNotice(Notice notice) {
		return 0;
	}

	public List<Notice> getNoticeNewestList() {
		return null;
	}

	// notice admin을 위한 서비스 함수 정리 -------------------------------------------

	// 사용자를 위한 서비스 함수 ------------------------------------------------------

	// temp 리스트에서 게시물을 클릭했을때 조회수를 증가시키키기 위해서 사용하는 메소드
	public void updateNoticeHit(int id) {
		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";
		String sql = "UPDATE NOTICE SET HIT = HIT + 1 WHERE ID = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			int result = st.executeUpdate();

			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public method 사실 getNoticeList 보단 정확한 함수명으로 getNoticeViewList() 와 같은 명칭이 더 좋은
	// 명칭이다.
	public List<NoticeView> getNoticeList() {
		return getNoticeList("title", "", 1);
	}

	// override method
	public List<NoticeView> getNoticeList(int page) {
		return getNoticeList("title", "", page);
	}

	// override method
	public List<NoticeView> getNoticeList(String field, String query, int page) {

		List<NoticeView> noticeList = new ArrayList<>();

		int start = 1 + (page - 1) * 10;
		int end = page * 10;

		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";
		String sql = "SELECT * FROM ( SELECT ROWNUM NUM, N.* FROM ( "
				+ " SELECT * FROM NOTICE_COMMENT_COUNT_VIEW WHERE " + field + " LIKE ? ORDER BY REGDATE DESC ) N "
				+ " ) WHERE NUM BETWEEN ? AND ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setString(1, "%" + query + "%");
			st.setInt(2, start);
			st.setInt(3, end);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				int cmtCount = rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");

				NoticeView noticeView = new NoticeView(id, title, regDate, writerId, hit, files, pub, cmtCount);

				noticeList.add(noticeView);
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return noticeList;
	}

	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}

	public int getNoticeCount(String field, String query) {

		int count = 0;

		String sql = "SELECT COUNT(ID) COUNT FROM ( SELECT ROWNUM NUM, N.* FROM ( " + " SELECT * FROM NOTICE WHERE "
				+ field + " LIKE ? ORDER BY REGDATE DESC ) N )";

		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setString(1, "%" + query + "%");

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public Notice getNotice(int id) {

		Notice notice = null;

		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE WHERE ID = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");

				notice = new Notice(nid, title, regDate, writerId, hit, files, content, pub);
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;
	}

	public Notice getNextNotice(int id) {

		Notice notice = null;

		String sql = "SELECT * FROM NOTICE " + "WHERE ID = " + "    SELECT ID FROM NOTICE "
				+ "    WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID = ?) AND ROWNUM = 1 " + " )";
		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");

				notice = new Notice(nid, title, regDate, writerId, hit, files, content, pub);
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;
	}

	public Notice getPrevNotice(int id) {
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE " + "WHERE ID = " + "    SELECT ID FROM NOTICE "
				+ "    WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID = ?) AND ROWNUM = 1 " + " )";
		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");

				notice = new Notice(nid, title, regDate, writerId, hit, files, content, pub);
			}

			rs.close();
			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;
	}

	public int deleteNoticeAll(int[] ids) {

		int result = 0;

		String params = "";

		for (int i = 0; i < ids.length; i++) {
			params += ids[i];

			if (i < ids.length - 1)
				params += ",";

		}

		String sql = "DELETE NOTICE WHERE ID IN (" + params + ")";
		String url = "jdbc:oracle:thin:@211.58.105.31:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "9074");
			Statement st = con.createStatement();
			result = st.executeUpdate(sql);

			st.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
