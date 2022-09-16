package com.kh.helloffice.admin.service;

import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.hr.entity.DeptDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminBoardService {
    List<DeptDto> getBoardList() throws Exception;

    int editPost(PostDto post) throws Exception;

    int addNotice(PostDto post, List<MultipartFile> fileList) throws Exception;
}
