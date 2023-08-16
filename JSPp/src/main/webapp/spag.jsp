<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%pageContext.setAttribute("result", "hello"); %>
	<div><%= request.getAttribute("result") %>입니다.</div>
	<div>${requestScope.result}입니다.</div>
	<div>${result}입니다.</div>
	<div>${names[0]}</div>
	<div>${names[1]}</div>
	<div>${notice.id}</div>
	<div>${notice.title}</div>
	<div>${param.n > 3}</div>
	<div>${param.n gt 3}</div>
	<div>${param.n ge 3}</div>
	<div>${param.n lt 3}</div>
	<div>${param.n le 3}</div>
	<div>${empty param.n} null 일경우나 빈문자일경우에도 참이 되는 연산자 empty</div>
	<div>${not empty param.n} null이 아닐경우나 빈문자 아닐일우에도 참이 되는 연산자 empty</div>
	<div>${param.n}</div>
	<div>${header.accept}</div>
</body>
</html>