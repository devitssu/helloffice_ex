<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../common/head.jsp" %>
<style>
    #refList{
        background-color: #e9ecef;
        border: 1px solid #ced4da;
        border-radius: 0.25rem;
    }

    .ref-item{
        background-color: salmon;
        color: linen;
        border: 1px solid #ced4da;
        border-radius: 0.5rem;
    }
</style>
<body>
	<%@ include file="../common/header.jsp" %>

	<main id="main" class="main">
        <h2>수습사원 자기 평가서</h2>
		<form action="" method="post" accept-charset="UTF-8">
         <div class="row mb-3">
           <div class="col-md-8">
           	  <div class="row">
	           <label for="inputTitle" class="col-sm-1 col-form-label">제목</label>
	             <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTitle" name="title" value="${doc.selfEvalDoc.title}" readonly>
                  </div>
              </div>
           </div>
         </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label">결재선</label>
        </div>
        <div class="row mb-3 approval-list">
            <div class="card border-secondary mb-3 me-3" style="max-width: 12rem;">
                <div class="card-header text-center">기안</div>
                <div class="card-body text-secondary">
                    <p class="card-text text-center">
                        ${doc.selfEvalDoc.writerDep}<br>
                        <b>${doc.selfEvalDoc.writer}</b><br>
                        ${doc.selfEvalDoc.writerRank}
                    </p>
                </div>
            </div>
            <c:forEach items="${doc.approvals}" var="a">
                <div class="card border-success mb-3 me-3" style="max-width: 12rem;">
                    <div class="card-header text-center">결재</div>
                    <div class="card-body text-success">
                        <p class="card-text text-center">
                                ${a.depName}<br>
                            <b>${a.empName}</b><br>
                                ${a.empRank}
                        </p>
                    </div>
                    <c:if test="${a.isApprove eq 'Y'.charAt(0)}"><span class="badge bg-success">완료</span></c:if>
                    <c:if test="${type eq 'approval'}">
                        <c:if test="${a.empNo eq loginEmp.empNo}">
                            <c:if test="${a.activate eq 'Y'.charAt(0)}"><span id="approve" class="badge bg-primary">승인하기</span></c:if>
                        </c:if>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label">참조</label>
            <div class="col-sm-9" id="refList">
                <c:forEach items="${doc.references}" var="r">
                    <span class="float-start ref-item ref-result me-1 pe-2">${r.empName} [${r.depName}]</span>
                </c:forEach>
            </div>
        </div>
            <div class="row mb-3">
                <label class="col-sm-1 col-form-label">작성일</label>
                <div class="col-sm-2">
                    <input type="date" class="form-control" name="createDate" readonly>
                </div>
                <label class="col-sm-1 col-form-label">부서명</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" value="${doc.selfEvalDoc.writerDep}" readonly>
                </div>
                <label class="col-sm-1 col-form-label">작성자</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" value="${doc.selfEvalDoc.writer}" readonly>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-1 col-form-label">수습기간</label>
                <div class="col-sm-2">
                    <input type="date" class="form-control" name="startDate" readonly>
                </div>
                ~
                <div class="col-sm-2">
                    <input type="date" class="form-control" name="endDate" readonly>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">담당 업무 수행에 대한 소감 (필수)</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px"  name="content1" readonly>${doc.selfEvalDoc.content1}</textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">회사에 대한 소감 (필수)</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px" name="content2" readonly>${doc.selfEvalDoc.content2}</textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">담당 업무에 대한 본인의 적성 여부</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px" name="content3" readonly>${doc.selfEvalDoc.content3}</textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">기타 건의 사항</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px" name="content4" readonly>${doc.selfEvalDoc.content4}</textarea>
                </div>
            </div>
            <c:if test="${type eq 'doc'}">
                <div class="text-center">
                    <button type="button" class="btn btn-danger" id="deleteDoc">삭제하기</button>
                    <a href="${root}/workflow/doc"><button type="button" class="btn btn-secondary">목록으로</button></a>
                </div>
            </c:if>
            <c:if test="${type ne 'doc'}">
                <div class="text-center">
                    <a href="${root}/workflow/approval/doc"><button type="button" class="btn btn-secondary">목록으로</button></a>
                </div>
            </c:if>
       </form><!-- End Horizontal Form -->
	</main>
	<%@ include file="../common/footer.jsp" %>
</body>
<script>
    const currentUrl = document.location.pathname;

    $(document).ready(function () {
        const create = "${doc.selfEvalDoc.createDate}";
        const start = "${doc.selfEvalDoc.startDate}";
        const end = "${doc.selfEvalDoc.endDate}";

        const createDate = create.substring(0,10);
        const startDate = start.substring(0,10);
        const endDate = end.substring(0,10);

        $('input[name=createDate]').val(createDate);
        $('input[name=startDate]').val(startDate);
        $('input[name=endDate]').val(endDate);
    });

    $('#approve').on('click', function (){
        if(confirm("승인하시겠습니까?")){
            $.ajax({
                type: 'PATCH',
                url: currentUrl
            }).done(function(data){
                alert("승인되었습니다.")
                history.go(0);
            }).fail(function (data) {
                alert("문제가 발생했습니다.")
            });
        }
    });

    $('#deleteDoc').on('click', function () {
        if(confirm("삭제하시겠습니까?")){
            $.ajax({
                type: 'DELETE',
                url: currentUrl
            }).done(function(data){
                alert('삭제되었습니다.');
                location.href = "/helloffice/workflow/doc";
            }).fail(function (data) {
                alert(data.responseText);
                history.go(0);
            });
        }
    });
</script>
</html>