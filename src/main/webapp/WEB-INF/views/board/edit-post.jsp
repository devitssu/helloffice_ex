<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../common/head.jsp" %>
<style>
    input[type="file"] {
        position: absolute;
        width: 0;
        height: 0;
        padding: 0;
        overflow: hidden;
        border: 0;
    }

    .file-label{
        display: inline-block;
        padding: 10px 20px;
        color: #fff;
        vertical-align: middle;
        background-color: #999999;
        cursor: pointer;
        height: 40px;
        margin-left: 10px;
    }
</style>
<body>
	<%@ include file="../common/header.jsp" %>

	<main id="main" class="main">
		<form action="${post.postNo}" method="POST" ENCTYPE="multipart/form-data" accept-charset="UTF-8">
<%--		<input type="hidden" name="_method" value="PATCH">--%>
		<input type="hidden" name="postNo" value="${post.postNo}">
         <div class="row mb-3">
           <div class="col-md-8">
           	  <div class="row">
           	  <div class="col-sm-2">
           	 	 <select id="inputState" class="form-select" name="category">
                    <option >카테고리</option>
                    <option value="일반" ${"일반" eq post.category ? "selected" : ""}>일반</option>
                    <option value="인사" ${"인사" eq post.category ? "selected" : ""}>인사</option>
                  </select>
           	  </div>
	           <label for="inputTitle" class="col-sm-1 col-form-label">제목</label>
	             <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTitle" name="title" value="${post.title}">
                  </div>
	           </div>
           </div>
           <div class="col-md-4">
             <div class="row">
	           <label for="inputWriter" class="col-sm-2 col-form-label">작성자</label>
	             	<input type="hidden" class="form-control" name="empNo" value="1" readonly>
	             <div class="col-sm-10">
                   <input type="text" class="form-control" name="empName" value="${post.empName}" readonly>
                  </div>
	           </div>
           </div>
         </div>
         <div class="row mb-3">
           <label for="inputEmail3" class="col-sm-2 col-form-label">내용</label>
            <textarea class="tinymce-editor" name="content">
                ${post.content}
              </textarea><!-- End TinyMCE Editor -->
         </div>
        <div class="row mb-3">
            <label class="col-sm-1 col-form-label file-label" for="uploadFile">파일 추가</label>
            <div class="col-sm-10"></div>
            <input type="file" name="fileList" id="uploadFile" multiple="multiple">
            <label class="col-sm-2 col-form-label">첨부 목록</label>
            <ul id="uploadedFileList">
                <c:forEach items="${fileMap}" var="f">
                    <li data-no="${f.key}">${f.value}<i class="bx bx-x"></i></li>
                </c:forEach>
            </ul>
            <ul id="fileListPreview"></ul>
        </div>
        <input type="hidden" id="delFileNo" name="delFileList">
         <div class="text-center">
           <button type="submit" class="btn btn-primary">수정하기</button>
           <a href="${root}/board/${post.boardNo}/${post.postNo}"><button type="button" class="btn btn-secondary">취소하기</button></a>
         </div>
       </form><!-- End Horizontal Form -->
	</main>
	<%@ include file="../common/footer.jsp" %>
</body>
<script>

    const delFileArr = [];

    $('#uploadFile').change(function(){
        renderFileList();
    });

    $(document).on('click', "#fileListPreview i", (el) => {
        cancelFile(el);
        renderFileList();
    })

    $(document).on('click',"#uploadedFileList i", (el)=> {
        delFile(el);
    })

    const renderFileList = () => {
        let fileList = $('#uploadFile')[0].files;
        let template = '';
        $('#fileListPreview').empty();
        Array.from(fileList).forEach(file => {
            template += `<li>${ '${file.name}' }<i class="bx bx-x"></i></li>`;
        });
        $('#fileListPreview').append(template);
    }

    const cancelFile = (e) => {
        const dataTransfer = new DataTransfer();
        const targetLi = $(e.target).parent();
        const index = targetLi.index();
        const fileList = Array.from($('#uploadFile')[0].files);
        fileList.filter((file,idx) => idx != index)
            .forEach(file => {
                dataTransfer.items.add(file);
            });
        $('#uploadFile')[0].files = dataTransfer.files;
        targetLi.remove();
    }

    const delFile = (e) =>  {
        const targetLi = $(e.target).parent();
        const fileNo = targetLi.data('no');
        addDelFileNo(fileNo);
        targetLi.remove();
    }

    const addDelFileNo = (no) =>  {
        delFileArr.push(no);
        $('#delFileNo').val(JSON.stringify({ ...delFileArr}));
    }
</script>
</html>