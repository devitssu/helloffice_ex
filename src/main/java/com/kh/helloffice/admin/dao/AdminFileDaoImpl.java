package com.kh.helloffice.admin.dao;

import com.kh.helloffice.board.entity.FileInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminFileDaoImpl implements AdminFileDao{

   private final SqlSession session;

    @Override
    public List<FileInfoDto> getFileList() throws Exception {
        return session.selectList("admin.getFileList");
    }

    @Override
    public int deleteFile(long fileNo) throws Exception {
        return session.update("admin.deleteFile", fileNo);
    }
}
