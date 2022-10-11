<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../common/head.jsp" %>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
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
        <h2>근태신청서</h2>
		<form action="" method="post" accept-charset="UTF-8">
         <div class="row mb-3">
           <div class="col-md-8">
           	  <div class="row">
	           <label for="inputTitle" class="col-sm-1 col-form-label">제목</label>
	             <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTitle" name="title" value="${doc.offDoc.title}" readonly>
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
                        ${doc.offDoc.writerDep}<br>
                        <b>${doc.offDoc.writer}</b><br>
                        ${doc.offDoc.writerRank}
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
            <label class="col-sm-1 col-form-label">근태명</label>
            <div class="col-sm-2">
                <select class="form-select" name="offType" disabled>
                    <option value="ANNUAL" ${"ANNUAL" eq doc.offDoc.offType ? "selected" : ""}>연차</option>
                    <option value="HALF_AM" ${"HALF_AM" eq doc.offDoc.offType ? "selected" : ""}>오전반차</option>
                    <option value="HALF_PM" ${"HALF_PM" eq doc.offDoc.offType ? "selected" : ""}>오후반차</option>
                    <option value="QUARTER" ${"QUARTER" eq doc.offDoc.offType ? "selected" : ""}>반반차</option>
                    <option value="HEALTH" ${"HEALTH" eq doc.offDoc.offType ? "selected" : ""}>보건휴가</option>
                    <option value="OFFICIAL" ${"OFFICIAL" eq doc.offDoc.offType ? "selected" : ""}>공가</option>
                </select>
            </div>
        </div>
            <fieldset class="row mb-3">
                <legend class="col-form-label col-sm-1 pt-0">단위</legend>
                <div class="col-sm-1">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" id="timeRadio" name="radio" value="time" checked>
                        <label class="form-check-label" for="timeRadio">
                            시간
                        </label>
                    </div>
                </div>
                <div class="col-sm-1">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" id="dateRadio" name="radio" value="date">
                        <label class="form-check-label" for="dateRadio">
                            일
                        </label>
                    </div>
                </div>
            </fieldset>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">시작일/시작시간</label>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="startDate" readonly>
                </div>
                <div class="col-sm-3">
                    <input class="form-control timepicker" name="startTime" readonly>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">종료일/종료시간</label>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="endDate" disabled>
                </div>
                <div class="col-sm-3">
                    <input class="form-control timepicker" name="endTime" readonly>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">사용 시간</label>
                <div class="col-sm-2 col-form-label" id="diffResult">0일(0시간)</div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">사유</label>
                <div class="col-sm-10">
                    <textarea class="form-control" placeholder="사유를 입력해주세요." style="height: 100px" name="reason" readonly>${doc.offDoc.reason}</textarea>
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

    const calcTime = () => {
        const startDate = new Date($('input[name=startDate]').val());
        const endDate = new Date($('input[name=endDate]').val());

        const dayDiff = Math.abs((endDate-startDate)/(1000 * 60 * 60 * 24)) || 0;

        const startTime = new Date('2000-01-01T' + $('input[name=startTime]').val() + ':00').getTime();
        const endTime = new Date('2000-01-01T' + $('input[name=endTime]').val() + ":00").getTime();
        const startLunch = new Date(2000,0, 1, 0,0).getTime();
        const endLunch = new Date(1899,0,1,13,0,0).getTime();

        // Max(0, Min(endLunch, endTime) - Max(startLunch, startTime))
        const overlapTime = Math.max(0, Math.min(endLunch, endTime) - Math.max(startLunch, startTime));

        const timeDiff = (endTime - startTime - overlapTime)/(1000 * 60 * 60) || 0;
        const totalDiff = dayDiff * 8 + timeDiff;
        const result = totalDiff/8 + '일(' + totalDiff + '시간)';
        $('#diffResult').text(result);
    }

    const checkUnit = (startTime, endTime) => {
        if(startTime == '09:00' && endTime == '18:00'){
            $('#dateRadio').prop('checked', true);
            $('.timepicker').hide();
        }
    }


    $(document).ready(function () {
        const start = "${doc.offDoc.startTime}";
        const end = "${doc.offDoc.endTime}";

        const startDate = start.substring(0,10);
        const startTime = start.substring(11).substring(0,5);

        const endDate = end.substring(0,10);
        const endTime = end.substring(11).substring(0,5);

        checkUnit(startTime, endTime);

        $('input[name=startDate]').val(startDate);
        $('input[name=startTime]').val(startTime);

        $('input[name=endDate]').val(endDate);
        $('input[name=endTime]').val(endTime);

        calcTime();
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