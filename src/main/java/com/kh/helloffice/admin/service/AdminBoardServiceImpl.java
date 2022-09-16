package com.kh.helloffice.admin.service;

import com.kh.helloffice.admin.dao.AdminBoardDao;
import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.hr.entity.DeptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService{

    private final AdminBoardDao dao;
    private final String FILE_DIR = "C:/test/upload/";
    @Override
    public List<DeptDto> getBoardList() throws Exception {
        return dao.getBoardList();
    }

    @Override
    public int editPost(PostDto post) throws Exception {
        return dao.editPost(post);
    }

    @Override
    public int addNotice(PostDto post, List<MultipartFile> fileList) throws Exception {
        if(fileList.get(0).getSize() == 0) return dao.addNotice(post);
        int postResult = dao.addNotice(post);
        int uploadResult = 0;

        if(postResult > 0){
            uploadResult = uploadFiles(post.getPostNo(), fileList);
        }
        return uploadResult;
    }
    private int uploadFiles(long postNo, List<MultipartFile> fileList) throws Exception {
        int result = 0;
        for (MultipartFile file: fileList) {
            String savePath = FILE_DIR + UUID.randomUUID().toString();

            String fileName = file.getOriginalFilename();
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            FileInfoDto fileInfo = FileInfoDto.builder()
                    .originName(fileName)
                    .fileSize(file.getSize())
                    .fileExt(fileExt)
                    .postNo(postNo)
                    .savePath(savePath)
                    .build();
            file.transferTo(new File(savePath));
            result += dao.uploadFiles(fileInfo);
        }
        return result;
    }
}
