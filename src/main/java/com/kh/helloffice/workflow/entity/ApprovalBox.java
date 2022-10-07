package com.kh.helloffice.workflow.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class ApprovalBox {
    private Long docSeq;
    private Long formSeq;
    private String formName;
    private String title;
    private Date createTime;

    private Character isApprove;
    private Date approveTime;
    private String writerName;
    private String writerDep;
    private Character activate;

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
        this.formName = Form.getBySeq(formSeq).formName();
    }
}
