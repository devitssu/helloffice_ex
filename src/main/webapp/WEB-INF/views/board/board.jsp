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
	<c:if test="${list[0].depName eq '대표'}">
		<h2>전체 게시판</h2>
		<c:if test="${loginEmp.adminLevel eq 3}">
			<small><a href="${url}/categories"><button>게시판관리</button></a></small>
		</c:if>
	</c:if>
		<c:forEach items="${categories}" var="c">
			<c:if test="${c.ref eq 0}">
				<h2>
					<c:if test="${c.name eq '전체'}">
						${c.name}
						<c:if test="${loginEmp.adminLevel eq 3}">
							<small><a href="/helloffice/admin/board/${page.boardNo}/categories"><button>게시판관리</button></a></small>
						</c:if>
					</c:if>
					<c:if test="${c.name ne '전체'}">
						${c.name}
						<c:if test="${loginEmp.adminLevel >= 2}">
							<small><a href="/helloffice/admin/board/${page.boardNo}/categories"><button>게시판관리</button></a></small>
						</c:if>
					</c:if>
				</h2>
			</c:if>
		</c:forEach>
		<nav class="navbar navbar-expand-lg bg-light">
			<div class="container-fluid">
				<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
					<div class="navbar-nav">
						<c:forEach items="${categories}" var="c">
							<c:if test="${c.seq eq page.boardNo}"><a class="nav-link disabled" href='#'>${c.name}</a></c:if>
							<c:if test="${c.seq ne page.boardNo}"><a class="nav-link" href="/helloffice/board/${c.seq}">${c.name}</a></c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</nav>
		<form action="" method="GET">
		<div class="row mb-3">
			<div class="col-md-1">
				<select id="category" name="category">
					<option > 카테고리 </option>
					<option value="전체" ${page.category eq "전체" ? "selected" : ""}>전체</option>
					<option value="일반" ${page.category eq "일반" ? "selected" : ""}>일반</option>
					<option value="인사" ${page.category eq "인사" ? "selected" : ""}>인사</option>
				</select>
			</div>
			<div class="col-md-1">
				<select id="count" name="count">
					<option > 게시글 수 </option>
					<option value="10" ${page.count eq 10 ? "selected" : ""}>10개</option>
					<option value="15" ${page.count eq 15 ? "selected" : ""}>15개</option>
					<option value="20" ${page.count eq 20 ? "selected" : ""}>20개</option>
				</select>
			</div>
			<div class="col-md-4">
				<div class="input-group search-bar">
				  <label class="input-group-text" for="search">검색</label>
				  <input type="text" class="form-control" name="search" id="search" value="${page.search}" aria-describedby="button-addon2">
				  <button class="btn btn-outline-secondary" type="submit" ><i class="bi bi-search"></i></button>
				</div>
			</div>
			<div class="col-md-3"></div>
			<div class="col-md-2">
				<a href="${url}/post"><button type="button" class="btn btn-outline-secondary">게시글 등록</button></a>
			</div>
		</div>
	              <table class="table table-hover">
	                <thead>
	                  <tr>
	                    <th scope="col">카테고리</th>
	                    <th scope="col">제목</th>
	                    <th scope="col">작성자</th>
	                    <th scope="col">등록일</th>
	                    <th scope="col">조회수</th>
	                  </tr>
	                </thead>
	                <tbody>
					<c:forEach items="${noticeList}" var="n">
						<tr class="table-primary" onClick="postDetail(${n.postNo})">
							<td>공지</td>
							<td>${n.title}</td>
							<td>${n.empName}</td>
							<td>${n.dateString}</td>
							<td>${n.viewCnt}</td>
						</tr>
					</c:forEach>
	                <c:forEach items="${list}" var="l">
	                  <tr onClick="postDetail(${l.postNo})">
	                    <td>${l.category}</td>
	                    <td>
							<c:if test="${l.depth > 0}">
								<c:forEach begin="1" end="${l.depth}">
									<i class="bx bx-subdirectory-right"></i>
								</c:forEach>
								${l.title}
							</c:if>
							<c:if test="${l.depth == 0}">
								${l.title}
							</c:if>
						</td>
	                    <td>${l.empName}</td>
	                    <td>${l.dateString}</td>
	                    <td>${l.viewCnt}</td>
	                  </tr>
	                </c:forEach>
	                </tbody>
	              </table>	
	              
              <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                  <li class="page-item">
                  <button class="page-link" aria-label="Previous" id="first" name="page" value="1">
                    <span aria-hidden="true">&laquo;</span>
                  </button>
                  </li>
                  
                  <c:if test="${page.page > 1}">
	                  <li class="page-item"><button type="submit" class="page-link" name="page" value="${page.page - 1}">이전</button></li>
                  </c:if>
                  
                  <c:forEach begin="${page.startPage}" end="${page.endPage}" var="i">
	                  <li class="page-item"><button type="submit" class="page-link page" name="page" value="${i}">${i}</button></li>
                  </c:forEach>
                  
                  <c:if test="${page.page < page.last}">
	                  <li class="page-item"><button type="submit" class="page-link" name="page" value="${page.page + 1}">다음</button></li>
                  </c:if>
                  
                  <li class="page-item">
                  <button class="page-link" aria-label="Next" name="page" value="${page.last}">
                    <span aria-hidden="true">&raquo;</span>
                  </button>
                  </li>
                </ul>
              </nav>
	              
		</form>
		
	</main>
	<%@ include file="../common/footer.jsp" %>
	
	<script type="text/javascript">
		function postDetail(no){
			location.href= '${boardNo}/'+ no;
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