package com.kh.helloffice.workflow.service;

import com.kh.helloffice.member.entity.DeptEmp;
import com.kh.helloffice.workflow.entity.*;

import java.util.List;

public interface WorkflowService {
    Document submitOffDoc(Document data) throws Exception;

    Document submitSelfEval(Document data) throws Exception;

    List<DocVo> getDocList(Long empNo) throws Exception;

    Document getDoc(DocVo vo) throws Exception;

    List<ApprovalBox> getDocToApproveList(Long empNo) throws Exception;

    void approve(Approval vo) throws Exception;

    List<DocVo> getRefDocList(Long empNo) throws Exception;

    void deleteDoc(DocVo vo) throws Exception;

    void sendNewDocPush(Document data, DeptEmp loginEmp) throws Exception;

    List<PushData> getPushes(Long empNo) throws Exception;

    void deletePush(Long seq) throws Exception;

    void sendApprovePush(Approval vo, DeptEmp loginEmp) throws Exception;
}
