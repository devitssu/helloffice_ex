package com.kh.helloffice.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.kh.helloffice.board.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.helloffice.board.dao.BoardDao;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao dao;

	private final String FILE_DIR = "C:/test/upload/";

	@Override
	public List<PostDto> getList(PageVo pageVo, String category) throws Exception {
		
		List<PostDto> postList = dao.getList(pageVo);
		postList = formatPost(postList);
		
		return postList;
	}

	@Override
	public int post(PostDto post, List<MultipartFile> fileList) throws Exception {
		if(fileList.get(0).getSize() == 0) return dao.post(post);
		int postResult = dao.post(post);
        int uploadResult = 0;

		if(postResult > 0){
			uploadResult = uploadFiles(post.getPostNo(), fileList);
        }
		return uploadResult;
	}

	@Override
	public int postReply(PostDto post, List<MultipartFile> fileList) throws Exception {
		if(fileList.get(0).getSize() == 0) return dao.postReply(post);
		int postResult = dao.postReply(post);
		int uploadResult = 0;

		if(postResult > 0){
			uploadResult = uploadFiles(post.getPostNo(), fileList);
		}
		return uploadResult;
	}

	@Override
	public List<PostDto> getNoticeList() throws Exception {
		List<PostDto> noticeList = dao.getNoticeList();
		return formatPost(noticeList);
	}

	@Override
	public List<BoardDto> getCategoryList(long boardNo, long empNo) throws Exception {
		long depNo = dao.getDepNo(boardNo);
		Map<String, Long> map = new HashMap<>();
		map.put("depNo", depNo);
		map.put("empNo", empNo);
		return dao.getCategoryListForUser(map);
	}

	@Override
	public List<BoardDto> getCategoryList(long boardNo) throws Exception {
		long depNo = dao.getDepNo(boardNo);
		return dao.getCategoryList(depNo);
	}

	@Override
	public PostDto getPost(long no) throws Exception {
	    int result = dao.increaseViewCnt(no);
	    if(result > 0) return dao.getPost(no);
	    else return null;
	}

	@Override
	public int editPost(PostDto post, List<MultipartFile> fileList) throws Exception {
		if(fileList.get(0).getSize() == 0) return dao.editPost(post);
		int editResult = dao.editPost(post);
		int uploadResult = 0;

		if(editResult != 0){
			uploadResult = uploadFiles(post.getPostNo(), fileList);
		}
		return uploadResult;
	}

	@Override
	public int deletePost(long no) throws Exception {
		List<FileInfoDto> files = getFiles(no);
		if(!files.isEmpty()){
			List<Long> seqList = new ArrayList<>();
			for (FileInfoDto file: files) {
				seqList.add(file.getSeq());
			}
			int delResult = deleteFiles(seqList);
			if(seqList.size() == delResult) return dao.deletePost(no);
			else return 0;
		}
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

	@Override
	public int deleteFiles(List<Long> delFileNo) throws Exception {
		int result = 0;
		for (Long fileNo: delFileNo) {
			result += dao.deleteFile(fileNo);
		}
		return result;
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
