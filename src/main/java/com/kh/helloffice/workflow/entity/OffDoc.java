package com.kh.helloffice.workflow.entity;

import lombok.Data;

@Data
public class OffDoc {

    private Long seq;
    private Long empNo;
    private String title;
    private OffType offType;
    private String startTime;
    private String endTime;
    private String reason;

    private String writer;
    private String writerDep;
    private String writerRank;
}
