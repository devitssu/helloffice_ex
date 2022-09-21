package com.kh.helloffice.admin.entity;

import lombok.Data;
@Data
public class AlarmDto {

    private long seq;
    private String message;
    private String startTime;
    private String endTime;
}
