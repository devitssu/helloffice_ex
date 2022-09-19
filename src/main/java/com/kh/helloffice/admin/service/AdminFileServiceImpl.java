package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminFileDao;
import com.kh.helloffice.board.entity.FileInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminFileServiceImpl implements AdminFileService{

    private final AdminFileDao dao;

    @Override
    public List<FileInfoDto> getFileList() throws Exception {
        return dao.getFileList();
    }

    @Override
    public int deleteFiles(List<String> fileNo) throws Exception {
        int result = 0;
        for(String no: fileNo){
            long delNo = Long.parseLong(no);
            result += dao.deleteFile(delNo);
        }
        return result;
    }
}
