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
	           <label for="inputTitle" class="col-sm-1 col-form-label"><b>제목</b></label>
	             <div class="col-sm-9 col-form-label">
                    ${post.title}
                  </div>
	           </div>
           </div>
           <div class="col-md-4">
             <div class="row">
	           <label for="inputWriter" class="col-sm-2 col-form-label"><b>작성자</b></label>
	             <div class="col-sm-10 col-form-label">
                    ${post.empName}
                  </div>
	           </div>
           </div>
           <div class="col-sm-2"></div>
         </div>
         <div class="row mb-3">
           <div class="col-sm-1"></div>
           <label class="col-sm-1 col-form-label"><b>내용</b></label>
           <div class="col-sm-8 col-form-label overflow-auto border border-1" style="height: 500px">
           		${post.content}
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
        for(let i=0;i<list.length;++i){
            let reEmpNo = list[i].empNo;
            if(list[i].replyFor != 0) continue;
            let template =
            `<div class="list-group mb-4">
                <div class="d-flex w-70 justify-content-between">
                <p class="mb-1">${ '${list[i].content}' }</p>
                <small>${ '${list[i].createdTime}' }</small>
            </div>
            <div class="d-flex w-70 justify-content-between">
                <small>${ '${list[i].empName}' }</small>`;
            if(empNo == reEmpNo){
                template = template + `<small><a onclick="editInput(${ '${list[i].replyNo}' })">수정</a> | <a onclick="deleteReply(${ '${list[i].replyNo}' })">삭제</a></small></div>`
            };
            template = template +
            `<div id="editDiv${ '${list[i].replyNo}' }" style="display:none">
                <input type="text" class="border border-1" id="editContent">
                <button type="button" class="btn btn-outline-primary" data-no="${ '${list[i].replyNo}' }" id="editReply">댓글쓰기</button>
            </div>`;
            $('#replyList').after(template);
        }
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

    const editInput = (no) => {
        $('#editDiv' + no).show();
        $('#replyDiv').hide();
    };

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
            $('#editDiv' + no).hide();
            $('#replyDiv').show();
            alert('댓글이 수정되었습니다.')
            history.go(0);
        }).fail(function (){
            alert('댓글 수정중 오류가 발생했습니다.')
        });
    });
</script>

</html>