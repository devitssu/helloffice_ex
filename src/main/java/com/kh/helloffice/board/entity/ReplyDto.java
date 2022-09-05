package com.kh.helloffice.board.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyDto {

    private long replyNo;
    private long postNo;
    private long replyFor;
    private long empNo;
    private String empName;
    private String content;
    private Date createdTime;
    private char isDeleted;
}
