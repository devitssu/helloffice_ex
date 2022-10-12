package com.kh.helloffice.workflow.dao;

import com.kh.helloffice.workflow.entity.*;

import java.util.List;

public interface WorkflowDao {
    int submitOffDoc(OffDoc offDoc) throws Exception;

    void setApprovals(List<Approval> approvals) throws Exception;

    void setReferences(List<Reference> references)throws Exception;

    int submitSelfEvalDoc(SelfEvalDoc selfEvalDoc) throws Exception;

    List<DocVo> getDocList(Long empNo) throws Exception;

    OffDoc getOffDoc(DocVo vo) throws Exception;

    List<Approval> getApprovals(DocVo vo) throws Exception;

    List<Reference> getReferences(DocVo vo) throws Exception;

    SelfEvalDoc getSelfEvalDoc(DocVo vo) throws Exception;

    List<ApprovalBox> getDocToApproveList(Long empNo) throws Exception;

    int approve(Approval vo) throws Exception;

    int updateFormApprovalData(Approval vo) throws Exception;

    void updateActivate(Approval vo) throws Exception;

    List<DocVo> getRefDocList(Long empNo) throws Exception;

    int checkDeletable(DocVo vo) throws Exception;

    void deleteDoc(DocVo vo) throws Exception;

    void addPushes(List<PushData> pushes) throws Exception;

    List<PushData> getPushes(Long empNo) throws Exception;

    void deletePush(Long seq) throws Exception;

    Long getActivate(Approval vo) throws Exception;

    DocVo getWriterNameAndDep(Approval vo) throws Exception;
}
