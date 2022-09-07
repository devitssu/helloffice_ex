package com.kh.helloffice.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.helloffice.board.dao.BoardDao;
import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao dao;

	private static final String FILE_DIR = "C:/test/upload/";

	@Override
	public List<PostDto> getList(PageVo pageVo, String category) throws Exception {
		
		List<PostDto> postList = dao.getList(pageVo);
		postList = formatPost(postList);
		
		return postList;
	}

	@Override
	public int post(PostDto post, List<MultipartFile> fileList) throws Exception {
		if(fileList == null) return dao.post(post);
		int postResult = dao.post(post);
        int uploadResult = 0;

		if(postResult > 0){
		    long postNo = post.getPostNo();
            for (MultipartFile file: fileList) {
                String savePath = FILE_DIR + UUID.randomUUID().toString();
                FileInfoDto fileInfo = FileInfoDto.builder()
                        .originName(file.getOriginalFilename())
                        .fileSize(file.getSize())
                        .fileExt(file.getContentType())
                        .postNo(postNo)
                        .savePath(savePath)
                        .build();
                file.transferTo(new File(savePath));
                uploadResult += dao.uploadFiles(fileInfo);
            }
        }else return 0;
		return uploadResult;
	}

	@Override
	public PostDto getPost(long no) throws Exception {
	    int result = dao.increaseViewCnt(no);
	    if(result > 0) return dao.getPost(no);
	    else return null;
	}

	@Override
	public int editPost(PostDto post) throws Exception {
		return dao.editPost(post);
	}

	@Override
	public int deletePost(long no) throws Exception {
		return dao.deletePost(no);
	}

	@Override
	public int getTotalPostNum(long boardNo) throws Exception {
		return dao.getTotalPostNum(boardNo);
	}

	@Override
	public List<PostDto> getRecentList() throws Exception {
		
		List<PostDto> postList = dao.getRecentList();
		postList = formatPost(postList);
		
		return postList;
	}

	@Override
	public int addReply(ReplyDto reply) throws Exception {
		return dao.addReply(reply);
	}

    @Override
    public List<ReplyDto> getReplyList(long no) throws Exception {
        return dao.getReplyList(no);
    }

	@Override
	public int editReply(ReplyDto reply) throws Exception {
		return dao.editReply(reply);
	}

    @Override
    public int deleteReply(long replyNo) throws Exception {
        return dao.deleteReply(replyNo);
    }

	@Override
	public List<FileInfoDto> getFiles(long no) throws Exception {
		return dao.getFiles(no);
	}

    @Override
    public FileInfoDto getFile(long fileNo) throws Exception {
        return dao.getFile(fileNo);
    }

    @Override
    public int increaseDownloadCnt(long fileNo) throws Exception {
        return dao.increaseDownloadCnt(fileNo);
    }

    private List<PostDto> formatPost(List<PostDto> postList){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		for (PostDto post : postList) {
			Date createdTime = post.getCreatedTime();
			post.setDateString(dateFormat.format(createdTime));
			post.setTimeString(timeFormat.format(createdTime));
		}
		
		return postList;
	}

}
