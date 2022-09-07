<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../common/head.jsp" %>
<body>
	<%@ include file="../common/header.jsp" %>

	<main id="main" class="main">
		<form action="" method="POST">
		<input type="hidden" name="_method" value="DELETE">
		<div class="row mb-3">
			<h1>${post.depName}</h1>
		</div>
         <div class="row mb-3">
           <div class="col-md-6">
           	  <div class="row">
           	  <div class="col-sm-2 col-form-label text-end">
           	 	 <h5><span class="badge border-dark border-1 text-dark">${post.category}</span></h5>
           	  </div>
	           <label class="col-sm-2 col-form-label"><b>제목</b></label>
	             <div class="col-sm-8 col-form-label">
                    ${post.title}
                  </div>
	           </div>
           </div>
           <div class="col-md-3">
             <div class="row">
	           <label class="col-sm-3 col-form-label"><b>작성자</b></label>
	             <div class="col-sm-9 col-form-label">
                    ${post.empName}
                  </div>
	           </div>
           </div>
             <div class="col-md-3">
                 <div class="row">
                     <label class="col-sm-3 col-form-label"><b>조회수</b></label>
                     <div class="col-sm-9 col-form-label">
                         ${post.viewCnt}
                     </div>
                 </div>
             </div>
         </div>
         <div class="row mb-3">
           <div class="col-sm-1"></div>
           <label class="col-sm-1 col-form-label"><b>내용</b></label>
           <div class="col-sm-8 col-form-label overflow-auto border border-1" style="height: 500px">
           		${post.content}
           </div>
           <div class="col-sm-2"></div>
         </div>
         <div class="row mb-3">
           <div class="col-sm-1"></div>
           <label class="col-sm-1 col-form-label"><b>첨부파일</b></label>
             <div class="col-sm-8 col-form-label ">
                 <ul class="file-list" style="list-style-type: none">
                      <c:forEach items="${fileList}" var="file">
                          <li style="float: left">
                              <div class="file-info col-sm-2">
                                  <a onclick="downloadFile(${file.seq})" ><i class="bx bxs-cloud-download"></i>${file.originName}</a>
                              </div>
                          </li>
                      </c:forEach>
                 </ul>
             </div>
           <div class="col-sm-2"></div>
         </div>
         <div class="text-center">
           <c:if test="${post.empNo eq loginEmp.empNo}">
	           <a href="post/${post.postNo}"><button type="button" class="btn btn-outline-primary">수정하기</button></a>
           </c:if>
           <a href="${root}/board/${boardNo}"><button type="button" class="btn btn-outline-secondary">목록으로</button></a>
           <c:if test="${post.empNo eq loginEmp.empNo}">
	           <button type="submit" class="btn btn-outline-danger">삭제하기</button>
           </c:if>
         </div>
         <div class="row mb-3">
             <div class="card-body pb-0">
                 <h5 class="card-title" id="replyList"> 댓글 </h5>
             </div>
         </div>
         </form>
            <div id="replyDiv">
                <input type="text" class="border border-1" id="replyContent">
                <button type="button" class="btn btn-outline-primary" id="sendReply">댓글쓰기</button>
            </div>
	</main>
	<%@ include file="../common/footer.jsp" %>
</body>
<script>
    let currentUrl = document.location.pathname;
    let boardNo = ${post.boardNo};
    let postNo = ${post.postNo};
    let empNo = ${loginEmp.empNo};

    $('#sendReply').click(function(){
        let content = $('#replyContent').val();
        const data = {
            "replyFor":null,
            "empNo":empNo,
            "content": content
        };

        $.ajax({
            type: 'POST',
            url: '/helloffice/board/' + boardNo + "/" + postNo + "/reply",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data){
            $('#replyContent').val('');
            alert('댓글이 등록되었습니다.')
            history.go(0);
        }).fail(function(){
            alert('댓글 등록 중 오류가 발생하였습니다.')
        });
    });

    const renderReplyList = (list) => {
        const reArr = [];
        for(let i=0;i<list.length;++i){
            let reEmpNo = list[i].empNo;
            if(list[i].replyFor != 0) {
                reArr.push(list[i]);
                continue;
            }
            let template =
            `<div id="reply${ '${list[i].replyNo}' }"><div class="list-group mb-4">
                <div class="d-flex w-70 justify-content-between">`;
            if(list[i].isDeleted == 'Y'){
                template += `<p class="mb-1">삭제된 댓글입니다.</p>`;
            }else{
                template += `<p class="mb-1">${ '${list[i].content}' }</p>`;
            }
            template += `<small>${ '${list[i].createdTime}' }</small>
            </div>
            <div class="d-flex w-70 justify-content-between">
                <small>${ '${list[i].empName}' }</small><small><a onclick="showInput('re',${ '${list[i].replyNo}' })">답글</a>`;
            if(empNo == reEmpNo){
                template += ` | <a onclick="showInput('edit',${ '${list[i].replyNo}' })">수정</a> | <a onclick="deleteReply(${ '${list[i].replyNo}' })">삭제</a>`
            };
            template +=
            `</small></div>
            <div id="editDiv${ '${list[i].replyNo}' }" style="display:none">
                <input type="text" class="border border-1" id="editContent">
                <button type="button" class="btn btn-outline-primary" data-no="${ '${list[i].replyNo}' }" id="editReply">수정하기</button>
                <button type="button" class="btn btn-outline-danger" onclick="cancelInput('edit',${ '${list[i].replyNo}' })">취소하기</button>
            </div>
            <div id="reDiv${ '${list[i].replyNo}' }" style="display:none">
                <input type="text" class="border border-1" id="reContent">
                <button type="button" class="btn btn-outline-primary" data-no="${ '${list[i].replyNo}' }" id="reReply">답글달기</button>
                <button type="button" class="btn btn-outline-danger" onclick="cancelInput('re', ${ '${list[i].replyNo}' })">취소하기</button>
            </div></div>`;
            $('#replyList').after(template);
        }
        renderReReplyList(reArr);
    }

    const renderReReplyList = (list) => {
        list.forEach(re => {
            let reEmpNo = re.empNo;
            let replyFor = re.replyFor;
            let template =
            `<div class="list-group mb-4">
                <div class="d-flex w-70 justify-content-between">
                <p class="mb-1"><i class="bx bxs-chevron-right"></i>${ '${re.content}' }</p>
                <small>${ '${re.createdTime}' }</small>
            </div>
            <div class="d-flex w-70 justify-content-between">
            <small>${ '${re.empName}' }</small>`;
            if(empNo == reEmpNo){
                template += `<small><a onclick="showInput('edit',${ '${re.replyNo}' })">수정</a> | <a onclick="deleteReply(${ '${re.replyNo}' })">삭제</a></small>`;
            }

            template += `</div>
            <div id="editDiv${ '${re.replyNo}' }" style="display:none">
                <input type="text" class="border border-1" id="editContent">
                <button type="button" class="btn btn-outline-primary" data-no="${ '${re.replyNo}' }" id="editReply">수정하기</button>
                <button type="button" class="btn btn-outline-danger" onclick="cancelInput('edit',${ '${re.replyNo}' })">취소하기</button>
            </div>`;
            $('#reply' + replyFor).after(template);
        })
    }

    $(document).ready(function () {
        $.ajax({
            type:'GET',
            url: currentUrl + '/reply',
            dataType: 'json'
        }).done(function(data){
            renderReplyList(data);
        }).fail(function(){
                alert('fail')
            })
    });

    const showInput = (type, no) => {
        if(type == 're'){
            $('#reDiv' + no).show();
        }else{
            $('#editDiv' + no).show();
        }
        $('#replyDiv').hide();
    };

    const cancelInput = (type, no) => {
        if(type == 're'){
            $('#reDiv' + no).hide();
        }else{
            $('#editDiv' + no).hide();
        }
        $('#replyDiv').show();
    };

    $(document).on('click', '#reReply',function(){
        const content = $(this).prev().val();
        const no = $(this).data('no');
        const data = {
            "replyFor":no,
            "content": content,
            "empNo":empNo
        };
        $.ajax({
            type: 'POST',
            url: currentUrl + '/reply',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            cancelInput('re',no);
            alert('답글이 등록 되었습니다.')
            history.go(0);
        }).fail(function (){
            alert('답글 등록중 오류가 발생했습니다.')
        });
    });

    $(document).on('click', '#editReply',function(){
        const content = $(this).prev().val();
        const no = $(this).data('no');
        const data = {
            "replyNo":no,
            "content": content
        };
        $.ajax({
            type: 'PATCH',
            url: currentUrl + '/reply/' + no,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            cancelInput('edit',no);
            alert('댓글이 수정되었습니다.')
            history.go(0);
        }).fail(function (){
            alert('댓글 수정중 오류가 발생했습니다.')
        });
    });

    const deleteReply = (no) => {
        if(confirm('댓글을 삭제하시겠습니까?')){
            $.ajax({
                type: 'DELETE',
                url: currentUrl + '/reply/' + no
            }).done(function () {
                alert('댓글이 삭제되었습니다.');
                history.go(0);
            }).fail(function () {
                alert('댓글 삭제 중 오류가 발생했습니다.')
            });
        }else return false;
    }

    const downloadFile = (fileNo)  => {
        if(confirm('파일을 다운로드하시겠습니까?')){
            location.href = currentUrl + "/file/" + fileNo;
        }else return false;
    }

</script>

</html>