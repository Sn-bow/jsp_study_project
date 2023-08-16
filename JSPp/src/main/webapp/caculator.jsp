  <%!
    	public void aaa() {
    	System.out.println("aaa1");
    }
    %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	
int x = 3;
int y = 5;

String cnt_ = request.getParameter("cnt");
int cnt = cnt_ != null && !cnt_.equals("") ? Integer.parseInt(cnt_) : 100;


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<%for(int i = 0; i < cnt; i++) { %>
	<div>hello world</div>
	<% } %>
	<button onclick="aaa()">aaawq</button>
</body>
</html>

  