package com.kh.helloffice.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {
    private long seq;
    private long postNo;
    private String savePath;
    private String originName;
    private Timestamp regDate;
    private long fileSize;
    private char flagDel;
    private String fileExt;
    private long downloadCnt;
}
