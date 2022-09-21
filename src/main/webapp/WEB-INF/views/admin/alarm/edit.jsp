<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../../common/head.jsp" %>
<body>
	<%@ include file="../../common/header.jsp" %>

	<main id="main" class="main">
		<form action="" method="post">
        <input type="hidden" name="_method" value="PATCH">
         <div class="row mb-3">
           <div class="col-md-8">
           	  <div class="row">
	           <label for="inputTitle" class="col-sm-1 col-form-label">내용</label>
	             <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTitle" name="message" value="${alarm.message}">
                  </div>
              </div>
           </div>
         </div>
         <div class="row m-3">
             <div class="col-md-6">
                 <label for="startTime" class="form-label">시작일</label>
                 <input type="date" class="form-control" id="startTime" name="startTime" value="${alarm.startTime}">
             </div>
             <div class="col-md-6">
                 <label for="endTime" class="form-label">종료일</label>
                 <input type="date" class="form-control" id="endTime" name="endTime" value="${alarm.endTime}">
             </div>
         </div>
         <div class="text-center">
           <button type="submit" class="btn btn-primary">등록</button>
           <button type="reset" class="btn btn-secondary">취소</button>
         </div>
       </form><!-- End Horizontal Form -->
	</main>
	<%@ include file="../../common/footer.jsp" %>
</body>
<script>
const dateFormat = (s) => {
    const [year, month, day] = s.split('-');
}
console.log(${alarm.startTime})
</script>
</html>