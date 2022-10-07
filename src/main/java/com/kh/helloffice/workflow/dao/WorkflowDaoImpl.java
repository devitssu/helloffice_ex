package com.kh.helloffice.workflow.dao;

import com.kh.helloffice.workflow.entity.*;
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

    @Override
    public List<DocVo> getDocList(Long empNo) throws Exception {
        return session.selectList("workflow.getDocList", empNo);
    }

    //TODO 쿼리짜기
    @Override
    public OffDoc getOffDoc(DocVo vo) throws Exception {
        return session.selectOne("workflow.getOffDoc", vo);
    }

    @Override
    public SelfEvalDoc getSelfEvalDoc(DocVo vo) throws Exception {
        return session.selectOne("workflow.getSelfEvalDoc", vo);
    }

    @Override
    public List<ApprovalBox> getDocToApproveList(Long empNo) throws Exception {
        return session.selectList("workflow.getDocToApproveList", empNo);
    }

    @Override
    public List<Approval> getApprovals(DocVo vo) throws Exception {
        return session.selectList("workflow.getApprovals", vo);
    }

    @Override
    public List<Reference> getReferences(DocVo vo) throws Exception {
        return session.selectList("workflow.getReferences", vo);
    }
}