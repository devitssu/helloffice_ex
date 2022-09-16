package com.kh.helloffice.admin.dao;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.hr.entity.DeptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AdminBoardDaoImpl implements AdminBoardDao{

    private final SqlSession session;
    @Override
    public List<DeptDto> getBoardList() throws Exception {
        return session.selectList("admin.getDeptList");
    }

    @Override
    public int editPost(PostDto post) throws Exception {
        return session.update("admin.editPost", post);
    }

    @Override
    public int addNotice(PostDto post) throws Exception {
        return session.insert("admin.insertNotice", post);
    }

    @Override
    public int uploadFiles(FileInfoDto fileInfo) throws Exception {
        return session.insert("admin.insertFile", fileInfo);
    }
}
