package com.kh.helloffice.workflow.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class DocVo {

    private Long docSeq;
    private Long formSeq;
    private String formName;
    private String title;
    private Integer maxApproval;
    private Integer approvalCnt;
    private Date lastApprovalTime;
    private String lastApprovalEmp;
    private Date createTime;
    private Character isComplete;
    private String writerName;
    private String writerDep;
    private Character activate;

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
        this.formName = Form.getBySeq(formSeq).formName();
    }
}
