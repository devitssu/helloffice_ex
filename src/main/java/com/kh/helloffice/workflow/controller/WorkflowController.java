package com.kh.helloffice.workflow.controller;

import com.kh.helloffice.InvalidDocException;
import com.kh.helloffice.member.entity.DeptEmp;
import com.kh.helloffice.workflow.entity.*;
import com.kh.helloffice.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/workflow")
public class WorkflowController {

    private final WorkflowService service;

    @GetMapping("form")
    public String formList() throws Exception {
        return "workflow/form-list";
    }

    @GetMapping("/form/{formNo}")
    public String form(@PathVariable Long formNo) throws Exception {
        return "workflow/form" + formNo;
    }

    @PostMapping("/form/{formNo}")
    @ResponseBody
    public ResponseEntity<Object> submitDoc(@PathVariable Long formNo,
                                            @RequestBody Document data) {
        try{
            if(formNo.equals(1L)){
                data.setFormType(Form.OFF);
                service.submitOffDoc(data);
            }else if(formNo.equals(2L)){
                data.setFormType(Form.SELF_EVAL);
                service.submitSelfEval(data);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/doc")
    public String docList(HttpSession session, Model model) throws Exception {
        Long empNo = getEmpNoFromSession(session);
        List<DocVo> docList = service.getDocList(empNo);

        List<DocVo> progressList = docList.stream()
                                    .filter(doc -> !doc.getApprovalCnt().equals(doc.getMaxApproval()))
                                    .collect(Collectors.toList());
        List<DocVo> completeList = docList.stream()
                                    .filter(doc -> doc.getApprovalCnt().equals(doc.getMaxApproval()))
                                    .collect(Collectors.toList());

        model.addAttribute("progressList", progressList);
        model.addAttribute("completeList", completeList);
        return "workflow/doc-list";
    }

    @GetMapping("/approval/doc")
    public String approvalDocList(HttpSession session, Model model) throws Exception {
        Long empNo = getEmpNoFromSession(session);
        List<ApprovalBox> list = service.getDocToApproveList(empNo);

        List<ApprovalBox> readyList = list.stream()
                                        .filter(doc -> doc.getActivate().equals('Y'))
                                        .collect(Collectors.toList());

        List<ApprovalBox> approvedList = list.stream()
                                            .filter(doc -> doc.getIsApprove().equals('Y'))
                                            .collect(Collectors.toList());

        model.addAttribute("readyList", readyList);
        model.addAttribute("approvedList", approvedList);
        return "workflow/approval-list";
    }

    @GetMapping("/reference/doc")
    public String referenceDocList(HttpSession session, Model model) throws Exception {
        Long empNo = getEmpNoFromSession(session);
        List<DocVo> list = service.getRefDocList(empNo);
        model.addAttribute("list", list);
        return "workflow/reference-list";
    }

    @GetMapping("/form/{formNo}/doc/{docNo}")
    public String docDetail(@PathVariable Long formNo,
                            @PathVariable Long docNo,
                            Model model) throws Exception {
        sendDocDetail(formNo, docNo, model);
        model.addAttribute("type", "doc");
        return "workflow/doc-detail" + formNo;
    }

    @DeleteMapping("/form/{formNo}/doc/{docNo}")
    public ResponseEntity<Object> deleteDoc(@PathVariable Long formNo,
                                            @PathVariable Long docNo) {
        DocVo vo = new DocVo();
        vo.setFormSeq(formNo);
        vo.setDocSeq(docNo);

        try {
            service.deleteDoc(vo);
        } catch (InvalidDocException e1) {
            ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .body(e1.getMessage());
            return response;
        } catch (Exception e2){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/approval/form/{formNo}/doc/{docNo}")
    public String approvalDocDetail(@PathVariable Long formNo,
                                    @PathVariable Long docNo,
                                    Model model) throws Exception {
        sendDocDetail(formNo, docNo, model);
        model.addAttribute("type", "approval");
        return "workflow/doc-detail" + formNo;
    }

    @GetMapping("/reference/form/{formNo}/doc/{docNo}")
    public String refDocDetail(@PathVariable Long formNo,
                               @PathVariable Long docNo,
                               Model model) throws Exception {
        sendDocDetail(formNo, docNo, model);
        model.addAttribute("type", "ref");
        return "workflow/doc-detail" + formNo;
    }

    @ResponseBody
    @PatchMapping("/approval/form/{formNo}/doc/{docNo}")
    public ResponseEntity<Object> approve(@PathVariable Long formNo,
                                          @PathVariable Long docNo,
                                          HttpSession session) {
        Long empNo = getEmpNoFromSession(session);

        Approval vo = new Approval();
        vo.setDocSeq(docNo);
        vo.setFormSeq(formNo);
        vo.setEmpNo(empNo);

        try {
            service.approve(vo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private void sendDocDetail(Long formNo, Long docNo, Model model) throws Exception {
        DocVo vo = new DocVo();
        vo.setDocSeq(docNo);
        vo.setFormSeq(formNo);

        Document doc = service.getDoc(vo);
        model.addAttribute("doc", doc);
    }


    private static Long getEmpNoFromSession(HttpSession session) {
        DeptEmp loginEmp = (DeptEmp) session.getAttribute("loginEmp");
        return loginEmp.getEmpNo();
    }
}
