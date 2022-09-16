package com.kh.helloffice.admin.dao;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.hr.entity.DeptDto;

import java.util.List;

public interface AdminBoardDao {
    List<DeptDto> getBoardList() throws Exception;

    int editPost(PostDto post) throws Exception;

    int addNotice(PostDto post) throws Exception;

    int uploadFiles(FileInfoDto fileInfo) throws Exception;
}
