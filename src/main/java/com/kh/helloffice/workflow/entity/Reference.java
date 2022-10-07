package com.kh.helloffice.workflow.entity;

import lombok.Data;

@Data
public class Reference {

    private Long seq;
    private Long formSeq;
    private Long docSeq;
    private Long empNo;

    private String empName;
    private String depName;
}
