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
		<h2>워크플로우 - 결재함</h2>
		<h3>문서 목록</h3>

		<ul class="nav nav-tabs nav-tabs-bordered d-flex" id="borderedTabJustified" role="tablist">
			<li class="nav-item flex-fill" role="presentation">
				<button class="nav-link w-100 active" id="progress-tab" data-bs-toggle="tab" data-bs-target="#bordered-justified-progress" type="button" role="tab" aria-controls="home" aria-selected="true">결재 대기</button>
			</li>
			<li class="nav-item flex-fill" role="presentation">
				<button class="nav-link w-100" id="completed-tab" data-bs-toggle="tab" data-bs-target="#bordered-justified-profile" type="button" role="tab" aria-controls="profile" aria-selected="false">결재 완료</button>
			</li>

		</ul>
		<div class="tab-content pt-2" id="borderedTabJustifiedContent">
			<div class="tab-pane fade show active" id="bordered-justified-progress" role="tabpanel" aria-labelledby="progress-tab">
				<table class="table table-hover" id="progressTable">
					<thead>
					<tr>
						<th scope="col">서식명</th>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">기안일</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${readyList}" var="l">
						<tr onclick="docDetail(${l.formSeq}, ${l.docSeq})">
							<th scope="row">${l.formName}</th>
							<td>${l.title}</td>
							<td>${l.writerName}[${l.writerDep}]</td>
							<td>${l.createTime}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="tab-pane fade" id="bordered-justified-profile" role="tabpanel" aria-labelledby="completed-tab">
				<table class="table table-hover" id="completeTable">
					<thead>
					<tr>
						<th scope="col">서식명</th>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">기안일</th>
						<th scope="col">결재일</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${approvedList}" var="l">
						<tr onclick="docDetail(${l.formSeq}, ${l.docSeq})">
							<th scope="row">${l.formName}</th>
							<td>${l.title}</td>
							<td>${l.writerName}[${l.writerDep}]</td>
							<td>${l.approveTime}</td>
							<td>${l.createTime}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div><!-- End Tabs -->

	</main>
	<%@ include file="../common/footer.jsp" %>
	
	<script type="text/javascript">
		function docDetail(formSeq, docSeq){
			location.href= 'form/'+ formSeq + "/doc/" + docSeq;
		}
	</script>
</body>
</html>