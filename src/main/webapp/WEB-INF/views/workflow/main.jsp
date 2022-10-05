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
		<h2>결재</h2>
		<h3>서식 목록</h3>
			<div class="row align-items-top">
					<div class="col-lg-3">
						<div class="card" onClick="postDetail(1)">
							<div class="card-body">
								<h5 class="card-title">근태신청서</h5>
								<p class="card-text">휴가/반차/반반차 신청</p>
							</div>
						</div>
					</div>
					<div class="col-lg-3">
						<div class="card" onClick="postDetail(2)">
							<div class="card-body">
								<h5 class="card-title">수습사원 자기 평가서</h5>
								<p class="card-text">수습 해지일 전까지 작성하여 제출</p>
							</div>
						</div>
					</div>
			</div>

	</main>
	<%@ include file="../common/footer.jsp" %>
	
	<script type="text/javascript">
		function postDetail(no){
			location.href= 'workflow/form/'+ no;
		}
		
		let urlParams = new URL(document.location.href).searchParams;
		let page = urlParams.get('page');
		
		$(document).ready(function(){
			$(`.page[value=${ '${page}' }]`).closest('li').addClass("active");
		});

		$('#category').change(function(){
			$('#first').trigger('click');
		});

		$('#count').change(function(){
			$('#first').trigger('click');
		});
		
	</script>
</body>
</html>