package com.kh.helloffice.workflow.entity;

import lombok.Data;

@Data
public class SelfEvalDoc {
    private Long seq;
    private Long empNo;
    private String title;
    private String createDate;
    private String startDate;
    private String endDate;
    private String content1;
    private String content2;
    private String content3;
    private String content4;
}
