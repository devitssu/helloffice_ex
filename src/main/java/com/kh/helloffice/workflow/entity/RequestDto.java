package com.kh.helloffice.workflow.entity;

import lombok.Data;

import java.util.List;

@Data
public class RequestDto {

    private OffDoc offDoc;
    private SelfEvalDoc selfEvalDoc;
    private List<Approval> approvals;
    private List<Reference> references;
    private Form formType;
}
