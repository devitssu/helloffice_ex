<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../common/head.jsp" %>
<body>
	<%@ include file="../common/header.jsp" %>
	<% 
		String url = request.getAttribute("javax.servlet.forward.request_uri").toString();
		request.setAttribute("url", url);
	%>	
	<main id="main" class="main">
		<h2>워크플로우 - 수신함</h2>
		<h3>문서 목록</h3>

		<table class="table table-hover" id="progressTable">
			<thead>
			<tr>
				<th scope="col">서식명</th>
				<th scope="col">제목</th>
				<th scope="col">기안일</th>
				<th scope="col">작성자</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="l">
				<tr onclick="docDetail(${l.formSeq}, ${l.docSeq})">
					<th scope="row">${l.formName}</th>
					<td>${l.title}</td>
					<td>${l.createTime}</td>
					<td>${l.writerName}[${l.writerDep}]</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>

	</main>
	<%@ include file="../common/footer.jsp" %>
	
	<script type="text/javascript">
		function docDetail(formSeq, docSeq){
			location.href= 'form/'+ formSeq + "/doc/" + docSeq;
		}
	</script>
</body>
</html>