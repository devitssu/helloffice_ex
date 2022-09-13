package com.kh.helloffice.board.service;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

        Charset[] charset = {StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8, StandardCharsets.US_ASCII, Charset.forName("EUC-KR")};

		if(postResult > 0){
		    long postNo = post.getPostNo();
            for (MultipartFile file: fileList) {
                String savePath = FILE_DIR + UUID.randomUUID().toString();

                //String fileName = new String(file.getOriginalFilename().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

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
                log.info("file={}",fileInfo);
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

	@Override
	public Map<Long, String> getFileMap(long no) throws Exception{
		List<FileInfoDto> fileList = getFiles(no);
		Map<Long, String> fileMap= new HashMap<>();
		for (FileInfoDto file: fileList) {
			fileMap.put(file.getSeq(), file.getOriginName());
		}
		return fileMap;
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
