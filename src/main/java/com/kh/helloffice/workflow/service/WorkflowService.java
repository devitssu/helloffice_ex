package com.kh.helloffice.workflow.service;

import com.kh.helloffice.workflow.entity.RequestDto;

public interface WorkflowService {
    void submitOffDoc(RequestDto data) throws Exception;

    void submitSelfEval(RequestDto data) throws Exception;
}
