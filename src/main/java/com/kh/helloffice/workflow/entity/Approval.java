package com.kh.helloffice.workflow.entity;

import lombok.Data;

@Data
public class Approval {

    private Long seq;
    private Long formSeq;
    private Long docSeq;
    private Long empNo;
    private Integer step;
    private char isApprove;
}
