<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- ======= Footer ======= -->
  <footer id="footer" class="footer">
    <div class="copyright">
      &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
    </div>
    <div class="credits">
      <!-- All the links in the footer should remain intact. -->
      <!-- You can delete the links only if you purchased the pro version. -->
      <!-- Licensing information: https://bootstrapmade.com/license/ -->
      <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
      Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
    </div>
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->

  <script src="${root}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${root}/resources/assets/vendor/quill/quill.min.js"></script>
  <script src="${root}/resources/assets/vendor/simple-datatables/simple-datatables.js"></script>
  <script src="${root}/resources/assets/vendor/tinymce/tinymce.min.js"></script>

  <!-- Template Main JS File -->
  <script src="${root}/resources/assets/js/main.js"></script>

  <!-- WebSocket -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
  <script>
    let socket = null;
    let pushCount = 0;

    toastr.options = {
      "closeButton": true,
      "debug": false,
      "progressBar": false,
      "positionClass": "toast-top-right",
      "preventDuplicates": true,
      "newestOnTop": true,
    }

    $(document).ready(function () {
      getPushes();
      connect();
    });

    const connect = () => {
      const ws = new SockJS('/helloffice/push');
      socket = ws;

      ws.onopen = function () {
        console.log('WS connected...');
      };

      ws.onmessage = function (event){
        const push = JSON.parse(event.data);
        makeToast(push);
        getPushes();
      }
    };

    const makeToast = (push) => {
      console.log(push);
      let title;
      let msg;
      switch (push.pushType) {
        case 'REQUEST':
          title = '?????? ??????';
          msg = `${'${push.senderName}'}(${'${push.senderDep}'})?????? ?????? ????????? ???????????????`;
          toastr.info(msg, title);
          break;
        case 'APPROVED':
          title = '?????? ??????';
          msg = `${'${push.senderName}'}(${'${push.senderDep}'})?????? ?????? ??????????????????`;
          toastr.success(msg, title);
          break;
        case 'REFERENCE':
          title = '??????';
          msg = `${'${push.senderName}'}(${'${push.senderDep}'})?????? ????????? ?????????????????????.`;
          toastr.info(msg, title);
          break;
        case 'COMPLETE':
          title = '?????? ??????';
          msg = `${'${push.senderName}'}(${'${push.senderDep}'})?????? ?????? ?????? ???????????? ????????? ?????????????????????.`;
          toastr.success(msg, title);
          break;
      }

    };

    const renderPushes = (pushes) => {
      $('#pushList').empty();
      pushes.forEach(push => {
        $('#pushCount').text(++pushCount);
        const type = push.pushType;
        let template = ``;
        switch (type) {
          case 'REQUEST':
            template = `
              <div class="alert alert-primary alert-dismissible fade show" role="alert">
                <span onclick="moveToDoc(${'${push.seq}'},${'${push.formSeq}'}, ${'${push.docSeq}'}, '${'${type}'}')">
                  <b>${'${push.senderName}'}(${'${push.senderDep}'})</b>?????? <b>${'${push.formName}'}</b>??? ?????? <b>?????? ??????</b>??? ???????????????.
                </span>
                  <button type="button" class="btn-close" aria-label="Close" onclick="delPush(${'${push.seq}'})"></button>
              </div>
            `;
            break;

          case  'APPROVED':
            template = `
              <div class="alert alert-success alert-dismissible fade show" role="alert">
                <span onclick="moveToDoc(${'${push.seq}'},${'${push.formSeq}'}, ${'${push.docSeq}'}, '${'${type}'}')">
                  <b>${'${push.senderName}'}(${'${push.senderDep}'})</b>?????? <b>${'${push.formName}'}</b> <b>?????? ??????</b>????????????.
                </span>
                  <button type="button" class="btn-close" aria-label="Close" onclick="delPush(${'${push.seq}'})"></button>
              </div>
            `;
            break;

          case 'REFERENCE':
            template = `
              <div class="alert alert-secondary alert-dismissible fade show" role="alert">
                <span onclick="moveToDoc(${'${push.seq}'},${'${push.formSeq}'}, ${'${push.docSeq}'}, '${'${type}'}')">
                  <b>${'${push.senderName}'}(${'${push.senderDep}'})</b>?????? <b>${'${push.formName}'}</b>??? ?????? <b>??????</b>?????? ??????????????????.
                </span>
                  <button type="button" class="btn-close" aria-label="Close" onclick="delPush(${'${push.seq}'})"></button>
              </div>
            `;
            break;

          case  'COMPLETE':
            template = `
              <div class="alert alert-success alert-dismissible fade show" role="alert">
                <span onclick="moveToDoc(${'${push.seq}'},${'${push.formSeq}'}, ${'${push.docSeq}'}, '${'${type}'}')">
                  <b>${'${push.senderName}'}(${'${push.senderDep}'})</b>?????? <b>${'${push.formName}'}</b>??? <b>?????? ??????</b>?????? ????????? ?????????????????????.
                </span>
                  <button type="button" class="btn-close" aria-label="Close" onclick="delPush(${'${push.seq}'})"></button>
              </div>
            `;
            break;
        }

        $('#pushList').append(template);
      });
    }

    const getPushes = () => {
      $.ajax({
        type: 'GET',
        url: '/helloffice/workflow/push',
        dataType: 'json'
      }).done(function(data){
        if(data.length != 0){
          renderPushes(data);
        }
      });
    };

    const moveToDoc = (seq, formNo, docNo, type) => {
      delPush(seq);
      switch (type){
        case 'REQUEST':
          location.href = '/helloffice/workflow/approval/form/' + formNo + '/doc/' + docNo;
          break;
        case 'APPROVED':
        case 'COMPLETE':
          location.href = '/helloffice/workflow/form/' + formNo + '/doc/' + docNo;
          break;
        case 'REFERENCE':
          location.href = '/helloffice/workflow/reference/form/' + formNo + '/doc/' + docNo;
          break;
      }
    };

    const delPush = (seq) => {
      $.ajax({
        type: 'DELETE',
        url: "/helloffice/workflow/push/" + seq
      }).done(function(data){
        $('#pushCount').text(--pushCount);
      }).fail(function (data) {
        alert("????????? ??????????????????.")
      });
    }
  </script>
