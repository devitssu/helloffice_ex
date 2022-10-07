package com.kh.helloffice.workflow.entity;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Form {
    OFF(1L, "FORM_1", "근태신청서"), SELF_EVAL(2L, "FORM_2", "수습사원 자기평가서");

    private final Long seq;
    private final String tableName;
    private final String formName;

    Form(Long seq, String tableName, String formName) {
        this.seq = seq;
        this.tableName = tableName;
        this.formName = formName;
    }

    private static final Map<Long, Form> BY_SEQ = Stream.of(values()).collect(Collectors.toMap(Form::seq, Function.identity()));
    public Long seq(){
        return this.seq;
    }
    public String formName(){return this.formName;}

    public static Form getBySeq(Long seq){
        return BY_SEQ.get(seq);
    }
}
