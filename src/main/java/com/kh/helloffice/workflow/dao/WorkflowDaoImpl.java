package com.kh.helloffice.workflow.dao;

import com.kh.helloffice.workflow.entity.Approval;
import com.kh.helloffice.workflow.entity.OffDoc;
import com.kh.helloffice.workflow.entity.Reference;
import com.kh.helloffice.workflow.entity.SelfEvalDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class WorkflowDaoImpl implements WorkflowDao{

    private final SqlSession session;

    @Override
    public int submitOffDoc(OffDoc offDoc) throws Exception {
        return session.insert("workflow.insertOffDoc", offDoc);
    }

    @Override
    public void setApprovals(List<Approval> approvals) throws Exception {
        for (Approval a: approvals) {
            session.insert("workflow.insertApproval", a);
        }
    }

    @Override
    public void setReferences(List<Reference> references) throws Exception {
        for (Reference r: references) {
            session.insert("workflow.insertReference", r);
        }
    }

    @Override
    public int submitSelfEvalDoc(SelfEvalDoc selfEvalDoc) throws Exception {
        return session.insert("workflow.insertSelfEvalDoc", selfEvalDoc);
    }
}
