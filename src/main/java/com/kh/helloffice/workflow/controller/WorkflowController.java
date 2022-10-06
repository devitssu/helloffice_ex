package com.kh.helloffice.workflow.controller;

import com.kh.helloffice.workflow.entity.Form;
import com.kh.helloffice.workflow.entity.RequestDto;
import com.kh.helloffice.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/workflow")
public class WorkflowController {

    private final WorkflowService service;

    @GetMapping
    public String main() throws Exception {
        return "workflow/main";
    }

    @GetMapping("/form/{formNo}")
    public String form(@PathVariable Long formNo) throws Exception {
        return "workflow/form" + formNo;
    }

    @PostMapping("/form/1")
    @ResponseBody
    public ResponseEntity<Object> submitOffDoc(@RequestBody RequestDto data) {
        data.setFormType(Form.OFF);
        try {
            service.submitOffDoc(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/form/2")
    @ResponseBody
    public ResponseEntity<Object> submitSelfEval(@RequestBody RequestDto data) {
        data.setFormType(Form.SELF_EVAL);
        try {
            service.submitSelfEval(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
