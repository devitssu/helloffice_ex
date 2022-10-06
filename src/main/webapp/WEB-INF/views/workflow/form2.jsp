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
                    <input type="text" class="form-control" id="inputTitle" name="title">
                  </div>
              </div>
           </div>
         </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label">결재선</label>
            <div class="col-sm-2"><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#approvalSetModal">결재선 설정</button></div>
        </div>
        <div class="row mb-3 approval-list">
            <div class="card border-secondary mb-3 me-3" style="max-width: 12rem;">
                <div class="card-header text-center">기안</div>
                <div class="card-body text-secondary">
                    <p class="card-text text-center">
                        ${loginEmp.depName}<br>
                        <b>${loginEmp.empName}</b><br>
                        ${loginEmp.empRank}
                    </p>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label">참조</label>
            <div class="col-sm-9" id="refList"></div>
        </div>
            <div class="row mb-3">
                <label class="col-sm-1 col-form-label">작성일</label>
                <div class="col-sm-2">
                    <input type="date" class="form-control" name="createDate">
                </div>
                <label class="col-sm-1 col-form-label">부서명</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" value="${loginEmp.depName}" readonly>
                </div>
                <label class="col-sm-1 col-form-label">작성자</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" value="${loginEmp.empName}" readonly>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-1 col-form-label">수습기간</label>
                <div class="col-sm-2">
                    <input type="date" class="form-control" name="startDate">
                </div>
                ~
                <div class="col-sm-2">
                    <input type="date" class="form-control" name="endDate">
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">담당 업무 수행에 대한 소감 (필수)</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px"  name="content1"></textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">회사에 대한 소감 (필수)</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px" name="content2"></textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">담당 업무에 대한 본인의 적성 여부</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px" name="content3"></textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label class="row mb-3">기타 건의 사항</label>
                <div class="row mb-3">
                    <textarea class="form-control" placeholder="내용을 입력해주세요." style="height: 100px" name="content4"></textarea>
                </div>
            </div>
         <div class="text-center">
           <button type="button" id="submitDoc" class="btn btn-primary">기안하기</button>
           <button type="button" id="cancelDoc" class="btn btn-secondary">취소하기</button>
         </div>
       </form><!-- End Horizontal Form -->

        <div class="modal fade" id="approvalSetModal" tabindex="-1" data-bs-backdrop="false">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">결재선 설정</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col">결재유형</th>
                                <th scope="col">결재자</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody id="approvalBody">
                            <tr>
                                <td>기안</td>
                                <td>${loginEmp.empName}[${loginEmp.depName}]</td>
                                <td>
                                    <button type="button" class="btn btn-light" onclick="addRow(this)">+</button>
                                    <button type="button" class="btn btn-light" disabled>-</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="row mb-3">
                            <label class="col-sm-1 col-form-label">참조</label>
                            <div class="col-sm-9"><input type="text" class="form-control search" placeholder="이름, 조직명 입력"></div>
                            <div class="ref-list"></div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" id="closeModal" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                        <button type="button" id="applyModal" class="btn btn-primary">적용</button>
                    </div>
                </div>
            </div>
        </div><!-- End Large Modal-->
	</main>
	<%@ include file="../common/footer.jsp" %>
</body>
<script>
    const today = new Date();
    const currentUrl = document.location.pathname;

    function formatDay(date){
        let year = date.getFullYear();
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let day = ('0' + date.getDate()).slice(-2);
        let dayFormat = year + '-' + month  + '-' + day;
        return dayFormat;
    };

    $(document).ready(function () {
       $('input[name=createDate]').val(formatDay(today));
    });

    const addRow = (el) => {
        const num = checkNum();
        if(num > 5){
            alert('결재선은 최대 5명까지 가능합니다.')
            return false;
        }

        const target = $(el).parent().parent();
        const row = `
        <tr class="added-row">
            <td>결재</td>
            <td><input type="text" class="form-control search" placeholder="이름, 조직명 입력"></td>
            <td>
                <button type="button" class="btn btn-light" onclick="addRow(this)">+</button>
                <button type="button" class="btn btn-light" onclick="delRow(this)">-</button>
            </td>
        </tr>
        `;

        target.after(row);
    }

    const delRow = (el) => {
        const target = $(el).parent().parent();
        target.remove();
    }
    const delRef = (el) => {
        const target = $(el).parent();
        target.remove();
    }

    const checkNum = () => {
        return $('#approvalBody').children().length;
    }

    $(document).on('click', '.search', function(){
        const resultUl = `<ul class="member-list list-group"></ul>`;
        $(this).after(resultUl);
    })

    $(document).on('keyup', '.search', function (key) {
        let keyword = $(this).val();
        if(!keyword) return false;
        searchMember(keyword);
        if(key.keyCode === 13){
            $(this).val("");
        }
    });

    const searchMember = (keyword) => {
        $.ajax({
            type: 'GET',
            url: '/helloffice/hr/hr/teamList?keyword=' + keyword,
            dataType: 'json'
        }).done(function(data){
            $('.member-list').empty();
            if(data.length != 0){
                renderMemberList(data);
            }
        });
    }

    const renderMemberList = (data) => {

        data.forEach((member) => {
            const name = member.empName
            const rank = member.empRank;
            const no = member.empNo;
            const dept = member.depName;

            let template =
                `<li class="list-group-item member"><div class="member-data" data-no="${ '${no}' }" data-rank="${ '${rank}' }" data-name="${ '${name}' }" data-dept="${ '${dept}' }">
                ${ '${name}' } [${ '${dept}' }]</div></li>`;
            $('.member-list').append(template);
        })
    }

    $(document).on('click', function (e){
        if(!$(e.target).hasClass('search')){
            $('.member-list').remove();
        }
    })

    $(document).on('click', '.member', function (){
        const target = $(this).parent().parent();
        if(target.prop('tagName') == 'TD'){
            target.html($(this).html());
        }else{
            $('.search').val("");
            const element = `<div class="d-inline-flex ref-item m-2">${'${$(this).html()}'} <span onclick="delRef(this)">X<span><div>`;
            $('.ref-list').append(element);
        }
    });

    const clearModal = () => {
        $('.added-row').remove();
        $('.ref-list').empty();
    }

    $('#closeModal').on('click', clearModal);

    $('#applyModal').on('click', function(){
        const approvalList = getApprovalList();
        $('.addedApproval').remove();
        approvalList.forEach((item, index) => renderApproval(item, index));

        const refList = getRefList();
        $('#refList').empty();
        refList.forEach(item => renderRef(item));

        $('#approvalSetModal').modal('hide');
    })

    const getApprovalList = () => {
        const result = [];
        const target = $('tr .member-data')
        target.each(function (index){
            const empNo = $(this).data('no');
            const rank = $(this).data('rank');
            const name = $(this).data('name');
            const dept = $(this).data('dept');

            const approval = {
                "empNo" : empNo,
                "rank" : rank,
                "name" : name,
                "dept" : dept
            };
            result.push(approval);
        });
        return result;
    }

    const renderApproval = (item, index) => {
        const template =`
            <div class="card border-success mb-3 me-3 addedApproval" style="max-width: 12rem;">
                <div class="card-header text-success text-center">결재</div>
                <div class="card-body text-success approval-data" data-step="${ '${index + 1}' }" data-no="${ '${item.empNo}' }">
                    <p class="card-text text-center">
                        ${ '${item.dept}' }<br>
                        <b>${ '${item.name}' }</b><br>
                        ${ '${item.rank}' }
                    </p>
                </div>
            </div>
        `;
        $('.approval-list').append(template);
    }

    const getRefList = () => {
        const result = [];
        const target = $('.ref-list .member-data');
        target.each(function (){
            const empNo = $(this).data('no');
            const name = $(this).data('name');
            const dept = $(this).data('dept');

            const ref = {
                "empNo" : empNo,
                "name" : name,
                "dept" : dept
            };
            result.push(ref);
        });
        return result;
    };

    const renderRef = (item) => {
        const template = `<span class="float-start ref-item ref-result me-1 pe-2" data-no="${ '${item.empNo}' }">${ '${item.name}' } [${ '${item.dept}' }]</span>`;
        $('#refList').append(template);
    }

    $('#submitDoc').on('click', function (){
        if(!checkRequired()){
            alert('필수 조건을 작성해주세요.')
            return false;
        }
        const data = makeData();
        $.ajax({
            type: 'POST',
            url: currentUrl,
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8'
        }).done(function(data){
            alert('제출이 완료되었습니다.')
            history.back();
        }).fail(function(data){
            alert('제출 중 문제가 발생했습니다.')
        });
    });

    const makeData = () => {
        const selfEvalDoc = {
            "empNo" : ${loginEmp.empNo},
            "title" : $('input[name=title]').val(),
            "createDate" : $('input[name=createDate]').val(),
            "startDate" : $('input[name=startDate]').val(),
            "endDate" : $('input[name=endDate]').val(),
            "content1" : $('textarea[name=content1]').val(),
            "content2" : $('textarea[name=content2]').val(),
            "content3" : $('textarea[name=content3]').val(),
            "content4" : $('textarea[name=content4]').val(),
        };

        //Approvals
        const approvals = [];
        $('.approval-data').each(function() {
            const empNo = $(this).data("no");
            const step = $(this).data("step");
            const approval = {
                "empNo": empNo,
                "step": step
            };
            approvals.push(approval);
        });

        //References
        const references = [];
        $('.ref-result').each(function (){
            const empNo = $(this).data("no");
            const reference = {
                "empNo" : empNo
            };
            references.push(reference);
        });

        return {
            "selfEvalDoc" : selfEvalDoc,
            "approvals" : approvals,
            "references" : references
        };
    }

    const checkRequired = () => {
        if(!$('input[name=startDate]').val()) return false;
        if(!$('input[name=endDate]').val()) return false;
        if(!$('textarea[name=content1]').val().trim()) return false;
        if(!$('textarea[name=content2]').val().trim()) return false;
        return true;
    }

    $('#cancelDoc').on('click', function () {
        if(confirm('작성 내용이 저장되지 않습니다. 정말 취소하시겠습니까?')){
            history.back();
        }
    });

</script>
</html>