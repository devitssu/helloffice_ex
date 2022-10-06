package com.kh.helloffice.workflow.service;

import com.kh.helloffice.workflow.dao.WorkflowDao;
import com.kh.helloffice.workflow.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class WorkflowServiceImpl implements WorkflowService{

    private final WorkflowDao dao;
    @Override
    public void submitOffDoc(RequestDto data) throws Exception{
        OffDoc offDoc = data.getOffDoc();
        List<Approval> approvals = data.getApprovals();
        List<Reference> references = data.getReferences();

        dao.submitOffDoc(offDoc);
        Long docSeq = offDoc.getSeq();
        Long formSeq = data.getFormType().getSeq();

        dao.setApprovals(setSeqForApprovals(approvals, docSeq, formSeq));
        dao.setReferences(setSeqForReferences(references, docSeq, formSeq));
    }

    @Override
    public void submitSelfEval(RequestDto data) throws Exception {
        SelfEvalDoc selfEvalDoc = data.getSelfEvalDoc();
        List<Approval> approvals = data.getApprovals();
        List<Reference> references = data.getReferences();

        dao.submitSelfEvalDoc(selfEvalDoc);
        Long docSeq = selfEvalDoc.getSeq();
        Long formSeq = data.getFormType().getSeq();

        dao.setApprovals(setSeqForApprovals(approvals, docSeq, formSeq));
        dao.setReferences(setSeqForReferences(references, docSeq, formSeq));

    }

    private List<Approval> setSeqForApprovals(List<Approval> list, Long docSeq, Long formSeq){
        for (Approval a: list) {
            a.setDocSeq(docSeq);
            a.setFormSeq(formSeq);
        }
        return list;
    }

    private List<Reference> setSeqForReferences(List<Reference> list, Long docSeq, Long formSeq){
        for (Reference r: list) {
            r.setDocSeq(docSeq);
            r.setFormSeq(formSeq);
        }
        return list;
    }


}
