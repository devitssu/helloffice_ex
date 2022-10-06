package com.kh.helloffice.workflow.entity;

public enum Form {
    OFF(1L, "FORM_1"), SELF_EVAL(2L, "FORM_2");

    private final Long seq;
    private final String tableName;

    Form(Long seq, String tableName) {
        this.seq = seq;
        this.tableName = tableName;
    }

    public Long getSeq(){
        return this.seq;
    }
}
