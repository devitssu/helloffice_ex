package com.kh.helloffice.workflow.service;

import com.kh.helloffice.InvalidDocException;
import com.kh.helloffice.member.entity.DeptEmp;
import com.kh.helloffice.workflow.dao.WorkflowDao;
import com.kh.helloffice.workflow.entity.*;
import com.kh.helloffice.workflow.handler.PushHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class WorkflowServiceImpl implements WorkflowService{

    private final WorkflowDao dao;
    private final PushHandler pushHandler;

    @Override
    public Document submitOffDoc(Document data) throws Exception{
        OffDoc offDoc = data.getOffDoc();
        List<Approval> approvals = data.getApprovals();
        List<Reference> references = data.getReferences();

        dao.submitOffDoc(offDoc);
        Long docSeq = offDoc.getSeq();
        Long formSeq = data.getFormType().seq();

        dao.setApprovals(setSeqForApprovals(approvals, docSeq, formSeq));
        dao.setReferences(setSeqForReferences(references, docSeq, formSeq));
        return data;
    }

    @Override
    public Document submitSelfEval(Document data) throws Exception {
        SelfEvalDoc selfEvalDoc = data.getSelfEvalDoc();
        List<Approval> approvals = data.getApprovals();
        List<Reference> references = data.getReferences();

        dao.submitSelfEvalDoc(selfEvalDoc);
        Long docSeq = selfEvalDoc.getSeq();
        Long formSeq = data.getFormType().seq();

        dao.setApprovals(setSeqForApprovals(approvals, docSeq, formSeq));
        dao.setReferences(setSeqForReferences(references, docSeq, formSeq));
        return data;
    }

    @Override
    public List<DocVo> getDocList(Long empNo) throws Exception {
        return dao.getDocList(empNo);
    }

    @Override
    public Document getDoc(DocVo vo) throws Exception {
        Document doc = new Document();

        if(vo.getFormSeq().equals(1L)){
            doc.setOffDoc(dao.getOffDoc(vo));
        } else if (vo.getFormSeq().equals(2L)) {
            doc.setSelfEvalDoc(dao.getSelfEvalDoc(vo));
        }
        doc.setApprovals(dao.getApprovals(vo));
        doc.setReferences(dao.getReferences(vo));

        return doc;
    }

    @Override
    public List<ApprovalBox> getDocToApproveList(Long empNo) throws Exception {
        return dao.getDocToApproveList(empNo);
    }

    @Override
    public void approve(Approval vo) throws Exception {
        dao.approve(vo);
        dao.updateActivate(vo);
        dao.updateFormApprovalData(vo);
    }

    @Override
    public List<DocVo> getRefDocList(Long empNo) throws Exception {
        return dao.getRefDocList(empNo);
    }

    @Override
    @ResponseBody
    public void deleteDoc(DocVo vo) throws Exception {
        int isDeletable = dao.checkDeletable(vo);
        if(isDeletable != 0) throw new InvalidDocException("결재가 진행중인 문서는 삭제할 수 없습니다.");
        dao.deleteDoc(vo);
    }

    @Override
    public void sendNewDocPush(Document data, DeptEmp loginEmp) throws Exception {
        String senderName = loginEmp.getEmpName();
        String senderDep = loginEmp.getDepName();
        Long docSeq = null;
        String formName = data.getFormType().formName();
        Long nextApprover = data.getApprovals().get(0).getEmpNo();
        List<Long> refEmps = data.getReferences().stream().map(r -> r.getEmpNo()).collect(Collectors.toList());

        if(data.getFormType().equals(Form.OFF)){
            docSeq = data.getOffDoc().getSeq();
        } else if (data.getFormType().equals(Form.SELF_EVAL)) {
            docSeq = data.getSelfEvalDoc().getSeq();
        }
        List<PushData> pushes = new ArrayList<>();

        PushData push = PushData.builder()
                            .sender(loginEmp.getEmpNo())
                            .senderName(senderName)
                            .senderDep(senderDep)
                            .formSeq(data.getFormType().seq())
                            .docSeq(docSeq)
                            .formName(formName)
                            .receiver(nextApprover)
                            .pushType(PushType.REQUEST)
                            .build();

        pushes.add(push);

        for (Long ref: refEmps) {
            pushes.add(
                    PushData.builder()
                        .sender(loginEmp.getEmpNo())
                        .senderName(senderName)
                        .senderDep(senderDep)
                        .formSeq(data.getFormType().seq())
                        .docSeq(docSeq)
                        .formName(formName)
                        .receiver(ref)
                        .pushType(PushType.REFERENCE)
                        .build());
        }
        dao.addPushes(pushes);
        pushHandler.send(pushes);
    }

    @Override
    public List<PushData> getPushes(Long empNo) throws Exception {
        List<PushData> pushes = dao.getPushes(empNo);
        return pushes;
    }

    @Override
    public void deletePush(Long seq) throws Exception {
        dao.deletePush(seq);
    }

    @Override
    public void sendApprovePush(Approval vo, DeptEmp loginEmp) throws Exception {
        DocVo doc = dao.getWriterNameAndDep(vo);
        doc.setFormSeq(vo.getFormSeq());
        Long approver = dao.getActivate(vo);

        String writerName = doc.getWriterName();
        String writerDep = doc.getWriterDep();
        String formName = doc.getFormName();
        Long writerNo = doc.getEmpNo();

        List<PushData> pushes = new ArrayList<>();

        if(approver != null){
            pushes.add(
                    PushData.builder()
                        .formSeq(vo.getFormSeq())
                        .docSeq(vo.getDocSeq())
                        .sender(vo.getEmpNo())
                        .senderName(writerName)
                        .senderDep(writerDep)
                        .receiver(approver)
                        .pushType(PushType.REQUEST)
                        .formName(formName)
                        .build()
            );

            pushes.add(
                    PushData.builder()
                        .formSeq(vo.getFormSeq())
                        .docSeq(vo.getDocSeq())
                        .sender(loginEmp.getEmpNo())
                        .senderName(loginEmp.getEmpName())
                        .senderDep(loginEmp.getDepName())
                        .receiver(approver)
                        .pushType(PushType.APPROVED)
                        .formName(formName)
                        .build()
            );
        }else{
            pushes.add(
                    PushData.builder()
                        .formSeq(vo.getFormSeq())
                        .docSeq(vo.getDocSeq())
                        .sender(loginEmp.getEmpNo())
                        .senderName(loginEmp.getEmpName())
                        .senderDep(loginEmp.getDepName())
                        .receiver(writerNo)
                        .pushType(PushType.COMPLETE)
                        .formName(formName)
                        .build()
            );
        }

        dao.addPushes(pushes);
        pushHandler.send(pushes);
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
