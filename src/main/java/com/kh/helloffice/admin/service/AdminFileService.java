package com.kh.helloffice.admin.service;

import com.kh.helloffice.board.entity.FileInfoDto;

import java.util.List;

public interface AdminFileService {
    List<FileInfoDto> getFileList() throws Exception;

    int deleteFiles(List<String> fileNo) throws Exception;
}
