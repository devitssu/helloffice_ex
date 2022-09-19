package com.kh.helloffice.admin.dao;

import com.kh.helloffice.board.entity.FileInfoDto;

import java.util.List;

public interface AdminFileDao {
    List<FileInfoDto> getFileList() throws Exception;

    int deleteFile(long fileNo) throws Exception;
}
