<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../common/head.jsp" %>

<head>
	<link rel="stylesheet" href="${root}/resources/assets/css/hrCss/hrCss.css" type="text/css">
</head>

<body>
	<%@ include file = "../common/header.jsp" %>
	
	<main id="main" class="main">
		<section class="section">
            <!-- 페이지 내용 -->
			<div class="body">
				<section class="section">
					<div class="row">
						<div class="col-lg-12">
							<div class="card card_radius">
                                <div class="container-fluid">
                                    <div class="navbar_content_l">
                                        <button onclick="history.back()" class="button_none_deco">
                                            <h1 style="padding: 1.5rem 0rem"><i class="bi bi-arrow-left"></i></h1>
                                        </button>
                                    </div>
                                </div>
								<div class="invite_wrapper">
                                    <div style="margin: auto; width: 55vw;">
                                        <div class="invite_contents">
                                            <div class="title_mid"> 필수정보 </div>
                                            <div class="col-sm-12">
												<form method="post" action="${emp.empNo}">
													<input type="hidden" name="_method" value="PATCH">
													<div class="">
														<div class="form-floating mb-3">
															<input type="text" class="form-control" id="name" name="empName" value="${emp.empName}">
															<label for="name"> 이름을 입력하세요 </label>
														</div>
														<div class="form-floating mb-3">
															<input type="email" class="form-control" id="email" name="email" value="${emp.email}">
															<label for="email">email@example.com</label>
														</div>
														<div class="form-floating mb-3">
															<input type="date" class="form-control" id="entryDate" name="entryDate" value="${emp.entryDate}">
															<label for="entryDate">  입사일을 설정해주세요 </label>
														</div>
														<div class="form-floating mb-3">
															<select class="form-control" id="deptList" name="depNo" placeholder="부서">
																<c:forEach items="${deptList}" var="d">
																	<option value="${d.depNo}">${d.depName}</option>
																</c:forEach>
															</select>
															<label for="deptList"> 부서 </label>
														</div>
														<div class="form-floating mb-3">
															<input type="text" class="form-control" id="empPosition" name="empPosition" value="${emp.empPosition}">
															<label for="empPosition"> 역할(직무) </label>
														</div>
														<div class="form-floating mb-3">
															<select id="empRank" name="empRank" class="form-select" aria-label="Default select example">
																<option value="인턴" ${"인턴" eq emp.empRank ? "selected" : ""}>인턴</option>
																<option value="사원" ${"사원" eq emp.empRank ? "selected" : ""}>사원</option>
																<option value="대리" ${"대리" eq emp.empRank ? "selected" : ""}>대리</option>
																<option value="과장" ${"과장" eq emp.empRank ? "selected" : ""}>과장</option>
																<option value="부장" ${"부장" eq emp.empRank ? "selected" : ""}>부장</option>
																<option value="대표" ${"대표" eq emp.empRank ? "selected" : ""}>대표</option>
															</select>
															<label for="empRank">직급</label>
														</div>
														<div class="form-floating mb-3">
															<select id="adminLevel" name="adminLevel" class="form-select" aria-label="Default select example">
																<option value="1" ${1 eq emp.adminLevel ? "selected" : ""}> 1 : [인턴, 사원, 대리] </option>
																<option value="2" ${2 eq emp.adminLevel ? "selected" : ""}> 2 : [과장, 부장] </option>
																<option value="3" ${3 eq emp.adminLevel ? "selected" : ""}> 3 : [대표] </option>
															</select>
															<label for="adminLevel"> 관리레벨 </label>
														</div>
														<div class="clearfix"></div>
													</div>
													<div class="d-grid gap-2 mt-3 mb-5">
                                                        <button class="btn btn-primary" type="submit">수정하기</button>
                                                    </div>
                                                </form>
                                            </div> <span style="color:red;">${message}</span>
                                        </div>
                                    </div>
                                </div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</section>
		
	</main>
	
	<%@ include file = "../common/footer.jsp" %>
</body>
<script>
	$(document).ready(function (){
		$('#deptList').val('${emp.depNo}').prop("selected", true);
	});

</script>
</html>