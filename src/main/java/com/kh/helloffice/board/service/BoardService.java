package com.kh.helloffice.board.service;

import java.util.List;
import java.util.Map;

import com.kh.helloffice.board.entity.FileInfoDto;
import com.kh.helloffice.board.entity.PageVo;
import com.kh.helloffice.board.entity.PostDto;
import com.kh.helloffice.board.entity.ReplyDto;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

	List<PostDto> getList(PageVo pageVo, String category) throws Exception;

	int post(PostDto post,List<MultipartFile> fileList) throws Exception;

	PostDto getPost(long no) throws Exception;

	int editPost(PostDto post, List<MultipartFile> fileList) throws Exception;

	int deletePost(long no) throws Exception;

	int getTotalPostNum(long boardNo) throws Exception;

	List<PostDto> getRecentList() throws Exception;

    int addReply(ReplyDto reply) throws Exception;

    List<ReplyDto> getReplyList(long no) throws Exception;

	int editReply(ReplyDto reply) throws Exception;

	int deleteReply(long replyNo) throws Exception;

    List<FileInfoDto> getFiles(long no) throws Exception;

    FileInfoDto getFile(long fileNo) throws Exception;

	int increaseDownloadCnt(long fileNo) throws Exception;

    Map<Long, String> getFileMap(long no) throws Exception;

    int deleteFiles(List<Long> delFileNo) throws Exception;

    int postReply(PostDto post, List<MultipartFile> fileList) throws Exception;

    List<PostDto> getNoticeList() throws Exception;
}
