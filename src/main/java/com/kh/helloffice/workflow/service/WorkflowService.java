package com.kh.helloffice.workflow.service;

import com.kh.helloffice.workflow.entity.ApprovalBox;
import com.kh.helloffice.workflow.entity.DocVo;
import com.kh.helloffice.workflow.entity.Document;

import java.util.List;

public interface WorkflowService {
    void submitOffDoc(Document data) throws Exception;

    void submitSelfEval(Document data) throws Exception;

    List<DocVo> getDocList(Long empNo) throws Exception;

    Document getDoc(DocVo vo) throws Exception;

    List<ApprovalBox> getDocToApproveList(Long empNo) throws Exception;
}
