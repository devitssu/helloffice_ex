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
                        개발팀<br>
                        <b>이수진</b><br>
                        사원
                    </p>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label">참조</label>
            <div class="col-sm-9" id="refList"></div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label">근태명</label>
            <div class="col-sm-2">
                <select class="form-select" name="offType">
                    <option value="ANNUAL" selected>연차</option>
                    <option value="HALF_AM">오전반차</option>
                    <option value="HALF_PM">오후반차</option>
                    <option value="QUARTER">반반차</option>
                    <option value="HEALTH">보건휴가</option>
                    <option value="OFFICIAL">공가</option>
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
                    <input type="date" class="form-control" name="startDate">
                </div>
                <div class="col-sm-3">
                    <input class="form-control timepicker" name="startTime">
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">종료일/종료시간</label>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="endDate" disabled>
                </div>
                <div class="col-sm-3">
                    <input class="form-control timepicker" name="endTime">
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">사용 시간</label>
                <div class="col-sm-2 col-form-label" id="diffResult">0일(0시간)</div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">사유</label>
                <div class="col-sm-10">
                    <textarea class="form-control" placeholder="사유를 입력해주세요." style="height: 100px" name="reason"></textarea>
                </div>
            </div>
         <div class="text-center">
           <button type="submit" class="btn btn-primary">기안하기</button>
           <button type="reset" class="btn btn-secondary">취소하기</button>
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
                                    <td>이수진</td>
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

    function formatDay(date){
        let year = date.getFullYear();
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let day = ('0' + date.getDate()).slice(-2);
        let dayFormat = year + '-' + month  + '-' + day;
        return dayFormat;
    };

    $('input[name=startDate]').val(formatDay(today));
    $('input[name=endDate]').val(formatDay(today));

    const calcTime = () => {
        const startDate = new Date($('input[name=startDate]').val());
        const endDate = new Date($('input[name=endDate]').val());

        const dayDiff = Math.abs((endDate-startDate)/(1000 * 60 * 60 * 24)) || 0;

        const startTime = $('input[name=startTime]').timepicker('getTime');
        const endTime = $('input[name=endTime]').timepicker('getTime');
        const startLunch = new Date(1899,11,31,12,0,0).getTime();
        const endLunch = new Date(1899,11,31,13,0,0).getTime();

        // Max(0, Min(endLunch, endTime) - Max(startLunch, startTime))
        const overlapTime = Math.max(0, Math.min(endLunch, endTime) - Math.max(startLunch, startTime));

        const timeDiff = (endTime - startTime - overlapTime)/(1000 * 60 * 60) || 0;
        const totalDiff = dayDiff * 8 + timeDiff;
        const result = totalDiff/8 + '일(' + totalDiff + '시간)';
        $('#diffResult').text(result);
    }

    $('.timepicker').timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '9:00',
        maxTime: '18:00',
        startTime: '09:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true,
        change: calcTime
    });

    $('input[type=radio]').on('click', function () {
        if($(this).val() == 'date'){
            $('input[name=endDate]').attr("disabled", false);
            $('input[name=startTime]').val('09:00');
            $('input[name=endTime]').val('18:00');
            $('.timepicker').hide();
            calcTime();
        }else{
            $('.timepicker').show();
            $('input[name=endDate]').attr("disabled", true);
            $('input[name=endDate]').val($('input[name=startDate]').val());
            calcTime();
        }
    });


    $('input[name=endDate]').on('change', calcTime);
    $('input[name=startDate]').on('change', function () {
        if($('input[type=radio]:checked').val() == 'time'){
            $('input[name=endDate]').val($('input[name=startDate]').val());
            calcTime();
        }else{
            calcTime()
        }
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
            console.log($(this).html());
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
                <div class="card-body text-success approvalData" data-step="${ '${index + 1}' }">
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
        const template = `<span class="float-start ref-item me-1 pe-2" data-no="${ '${item.empNo}' }">${ '${item.name}' } [${ '${item.dept}' }]</span>`;
        $('#refList').append(template);
    }
    //TODO Modal 부분 따로 빼기
    //TODO json으로 값보내기..?

</script>
</html>