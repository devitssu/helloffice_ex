<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../../common/head.jsp" %>
<body>
	<%@ include file="../../common/header.jsp" %>

	<main id="main" class="main">
		<form action="" method="post" ENCTYPE="multipart/form-data" accept-charset="UTF-8">
         <div class="row mb-3">
           <div class="col-md-8">
           	  <div class="row">
           	  <div class="col-sm-2">
           	 	 <input class="form-control" name="category" type="text" value="공지" readonly>
           	  </div>
	           <label for="inputTitle" class="col-sm-1 col-form-label">제목</label>
	             <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTitle" name="title">
                  </div>
              </div>
           </div>
           <div class="col-md-4">
             <div class="row">
	           <label class="col-sm-2 col-form-label">작성자</label>
	             	<input type="hidden" class="form-control" name="empNo" value="${loginEmp.empNo}" readonly>
	             <div class="col-sm-10">
                   <input type="text" class="form-control" name="empName" value="${loginEmp.empName}" readonly>
                  </div>
	           </div>
           </div>
         </div>
         <div class="row m-3">
             <div class="col-md-6">
                 <label for="startTime" class="form-label">시작 시간</label>
                 <input type="datetime-local" class="form-control" id="startTime" name="startTime">
             </div>
             <div class="col-md-6">
                 <label for="endTime" class="form-label">종료 시간</label>
                 <input type="datetime-local" class="form-control" id="endTime" name="endTime">
             </div>
         </div>
         <div class="row mb-3">
           <label class="col-sm-2 col-form-label">내용</label>
            <textarea class="tinymce-editor" name="content"></textarea>
         </div>
         <div class="row mb-3">
           <label class="col-sm-2 col-form-label">파일 첨부</label>
             <input type="file" name="fileList" id="uploadFile" multiple="multiple">
         </div>
         <div class="row mb-3">
             <ul id="fileListPreview"></ul>
         </div>
         <div class="text-center">
           <button type="submit" class="btn btn-primary">Submit</button>
           <button type="reset" class="btn btn-secondary">Reset</button>
         </div>
       </form><!-- End Horizontal Form -->
	</main>
	<%@ include file="../../common/footer.jsp" %>
</body>
<script>

    $('#uploadFile').change(function(){
        renderFileList();
    });

    $(document).on('click', "#fileListPreview i", (el) => {
        cancelFile(el);
        renderFileList();
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

</script>
</html>