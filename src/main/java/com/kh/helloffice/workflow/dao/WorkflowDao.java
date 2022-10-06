package com.kh.helloffice.workflow.dao;

import com.kh.helloffice.workflow.entity.Approval;
import com.kh.helloffice.workflow.entity.OffDoc;
import com.kh.helloffice.workflow.entity.Reference;
import com.kh.helloffice.workflow.entity.SelfEvalDoc;

import java.util.List;

public interface WorkflowDao {
    int submitOffDoc(OffDoc offDoc) throws Exception;

    void setApprovals(List<Approval> approvals) throws Exception;

    void setReferences(List<Reference> references)throws Exception;

    int submitSelfEvalDoc(SelfEvalDoc selfEvalDoc) throws Exception;
}
