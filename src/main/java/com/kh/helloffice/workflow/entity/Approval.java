package com.kh.helloffice.workflow.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Approval {

    private Long seq;
    private Long formSeq;
    private Long docSeq;
    private Long empNo;
    private Integer step;
    private Character isApprove;
    private Date approveTime;
    private Character activate;

    private String empName;
    private String depName;
    private String empRank;
}
